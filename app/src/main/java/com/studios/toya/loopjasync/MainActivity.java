package com.studios.toya.loopjasync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends ActionBarActivity {
    private ProgressDialog progressDialog;
    private EditText email;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inisialisasi variabel
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // alamat server
                String alamatServer = "http://json.toya-studios.com/login.php";

                // data dari edit text yang akan dikirim ke server untuk proses login
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                // memasukkan data yang diinput di EditText sebagai parameter
                RequestParams requestParams = new RequestParams();
                requestParams.put("email", emailText);
                requestParams.put("password", passwordText);

                // membuat object AsyncHttpClient dari loopj
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

                // proses login ke server dengan loopj AsyncHttpClient
                asyncHttpClient.post(alamatServer, requestParams, new JsonHttpResponseHandler(){

                    // aksi sebelum komunikasi dengan server
                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Proses login, sabar....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    /*
                        aksi jika komunikasi dengan server berhasil
                        kita bisa menentukan apakah respon dari server diubah jadi JSONObject atau JSONArray
                        pada tutorial ini saya menggunakan JSONObject
                        jika Anda ingin menggunakan JSONArray cukup ubah JSONObject dengan JSONArray pada method onSuccess ini
                        secara otomatis loopj AsyncTaskHttpClient akan mengubahnya sesuai keinginan Anda
                     */
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        /*
                            aksi menangaini respon dari server jika komunikasi berhasil
                            silakan sesuaikan dengan aplikasi Anda
                            pada tutorial ini kita akan cek status dari server
                            jika status = success arahkan ke KaryawanActivity
                            jika gagal / fail minta user untuk memasukkan email dan password lagi
                         */
                        String status = null;
                        try {
                            status = response.getString("status");
                            if(status.equals("success")){
                                Intent intent = new Intent(MainActivity.this, KaryawanActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), response.getString("pesan"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // sembunyikan progress dialog
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        // sembunyikan keyboard
                        InputMethodManager inputMethodManager = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    }
                });
            }
        });

    }


}
