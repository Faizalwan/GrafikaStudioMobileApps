package com.example.grafikastudio;

public class row_booking {
    String url_pic,nama_paket,kode_transaksi,harga,jml_orang;

    row_booking(){

    }

    public row_booking(String url_pic, String nama_paket, String kode_transaksi, String harga, String jml_orang) {
        this.url_pic = url_pic;
        this.nama_paket = nama_paket;
        this.kode_transaksi = kode_transaksi;
        this.harga = harga;
        this.jml_orang = jml_orang;
    }

    public String getUrl_pic() {
        return url_pic;
    }

    public void setUrl_pic(String url_pic) {
        this.url_pic = url_pic;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJml_orang() {
        return jml_orang;
    }

    public void setJml_orang(String jml_orang) {
        this.jml_orang = jml_orang;
    }
}
