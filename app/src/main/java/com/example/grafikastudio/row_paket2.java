package com.example.grafikastudio;

public class row_paket2 {
    String url_pic,nama_paket,kode_paket,harga,jml_orang;

    row_paket2(){

    }

    public row_paket2(String url_pic, String nama_paket, String kode_paket, String harga, String jml_orang) {
        this.url_pic = url_pic;
        this.nama_paket = nama_paket;
        this.kode_paket = kode_paket;
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

    public String getKode_paket() {
        return kode_paket;
    }

    public void setKode_paket(String kode_paket) {
        this.kode_paket = kode_paket;
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
