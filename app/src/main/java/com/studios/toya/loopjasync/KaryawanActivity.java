package com.studios.toya.loopjasync;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class KaryawanActivity extends ActionBarActivity {
    private ProgressDialog progressDialog;
    private ListView karyawanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan);
        // inisialisasi listview
        karyawanList = (ListView)findViewById(R.id.karyawan_list);
        // panggil method untuk mendapatkan data
        dapatkanDaftarKaryawan();
    }

    // method untuk mendapatkan data dari server
    protected void dapatkanDaftarKaryawan(){

        String alamatServer = "http://json.toya-studios.com/karyawan.php";

        // proses mendapatkan data dengan loopj AsyncTaskHttpClient
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(alamatServer, new JsonHttpResponseHandler(){
            // aksi sebelum berkomunikasi dengan server
            @Override
            public void onStart() {
                super.onStart();
                progressDialog = new ProgressDialog(KaryawanActivity.this);
                progressDialog.setMessage("Sedang ambil data karyawan, sabar . . . ");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
            // aksi jika komunikasi dengan server berhasil
            // karena data yang dikirim dari server diapit dengan kurung kurawal maka menggunakan JSONObject
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    // mendapatkan status dari server
                    String status = response.getString("status");

                    if(status.equals("full")){

                        // aksi jika status dari server = full atau ada data
                        // buat arraylist untuk menampung data karyawan
                        ArrayList<Karyawan> karyawanArrayList = new ArrayList<Karyawan>();

                        // masukkan data karyawan ke dalam JSONArray kemudian looping
                        // untuk mendapatkan setiap item data karyawan
                        JSONArray karyawanJSONArray = response.getJSONArray("karyawan");
                        for(int i = 0; i < karyawanJSONArray.length(); i++){
                            // ubah setiap data dari JSONArray menjadi JSONObject
                            JSONObject karyawanJSONObject = karyawanJSONArray.getJSONObject(i);

                            // simpan setiap data dari JSONObject kedalam variabel sementara
                            String nama = karyawanJSONObject.getString("nama");
                            String alamat = karyawanJSONObject.getString("alamat");
                            String email = karyawanJSONObject.getString("email");
                            String foto = karyawanJSONObject.getString("foto");

                            // gabungkan data sementara diatas agar menjadi satu object Karyawan dengan menggunakan constructor
                            karyawanArrayList.add(new Karyawan(nama, alamat, email, foto));
                        }

                        // buat arrayadapter (KarywanAdapter) untuk menampilkan data karyawan ke dalam listview
                        KaryawanAdapter karyawanAdapter = new KaryawanAdapter(KaryawanActivity.this, karyawanArrayList);
                        karyawanList.setAdapter(karyawanAdapter);

                    }else{
                        // aksi dari server jika data tidak ada
                        // listview akan menampilkan data berupa satu buah pesan dari server
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(response.getString("pesan"));
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                        karyawanList.setAdapter(adapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                // sembunyikan progressdialog jika sudah selesai
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }
        });


    }

}
