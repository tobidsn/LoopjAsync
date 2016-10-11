package com.studios.toya.loopjasync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ngasturi on 21/02/16.
 */

public class KaryawanAdapter extends ArrayAdapter<Karyawan> {
    private Context  context;

    // wajib buat constructor
    // silakan lihat tutorial saya tentang bagaimana membuat custom arrayadapter
    public KaryawanAdapter(Context context, ArrayList<Karyawan> karyawanArrayList) {
        super(context, 0, karyawanArrayList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView= inflater.inflate(R.layout.karyawan_list_item, parent, false);

        Karyawan karyawan = getItem(position);

        ImageView fotoKaryawan = (ImageView)convertView.findViewById(R.id.foto_karyawan);
        TextView namaKaryawan = (TextView)convertView.findViewById(R.id.nama_karyawan);
        TextView emailKaryawan = (TextView)convertView.findViewById(R.id.email_karyawan);
        TextView alamatKaryawan = (TextView)convertView.findViewById(R.id.alamat_karyawan);

        /*
            menggunakan library picasso untuk menampilkan gambar dari url / server / web
            loopj pada tutorial ini digunakan hanya mendapatkan data karyawan dalam bentuk json
            walaupun loopj bisa digunakan untuk download file gambar,
            tetapi kalau hanya sekedar menampilkan gambar kedalam ImageView
            lebih mudah menggunakan picasso
         */
        Picasso.with(this.context).load(karyawan.getFoto()).into(fotoKaryawan);

        namaKaryawan.setText(karyawan.getNama().toString());
        emailKaryawan.setText(karyawan.getEmail().toString());
        alamatKaryawan.setText(karyawan.getAlamat().toString());

        return convertView;
    }
}
