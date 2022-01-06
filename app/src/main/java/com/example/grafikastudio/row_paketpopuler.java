package com.example.grafikastudio;

public class row_paketpopuler {
    String url_pic,nama_katalog,kode_katalog;
    row_paketpopuler(){}

    public row_paketpopuler(String url_pic, String nama_katalog, String kode_katalog) {
        this.url_pic = url_pic;
        this.nama_katalog = nama_katalog;
        this.kode_katalog = kode_katalog;
    }

    public String getUrl_pic() {
        return url_pic;
    }

    public void setUrl_pic(String url_pic) {
        this.url_pic = url_pic;
    }

    public String getNama_Katalog() {
        return nama_katalog;
    }

    public void setNama_Katalog(String nama_katalog) {
        this.nama_katalog = nama_katalog;
    }
    public String getKode_Katalog() {
        return kode_katalog;
    }

    public void setKode_Katalog(String kode_katalog) {
        this.kode_katalog = kode_katalog;
    }
}
