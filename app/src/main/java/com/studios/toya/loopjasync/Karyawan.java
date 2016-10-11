package com.studios.toya.loopjasync;

/**
 * Created by ngasturi on 21/02/16.
 */
public class Karyawan {
    private String nama;
    private String alamat;
    private String email;
    private String foto;

    // buat constructor untuk memudahkan input data
    public Karyawan(String nama, String alamat, String email, String foto){
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
