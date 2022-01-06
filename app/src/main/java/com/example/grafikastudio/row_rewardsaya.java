package com.example.grafikastudio;

public class row_rewardsaya {
    String judul_reward,poin_reward,url_reward,kode_reward;
    row_rewardsaya(){}

    public row_rewardsaya(String judul_reward, String poin_reward, String url_reward, String kode_reward) {
        this.judul_reward = judul_reward;
        this.poin_reward = poin_reward;
        this.url_reward = url_reward;
        this.kode_reward = kode_reward;
    }

    public String getJudul_reward() {
        return judul_reward;
    }

    public void setJudul_reward(String judul_reward) {
        this.judul_reward = judul_reward;
    }

    public String getPoin_reward() {
        return poin_reward;
    }

    public void setPoin_reward(String poin_reward) {
        this.poin_reward = poin_reward;
    }

    public String getUrl_reward() {
        return url_reward;
    }

    public void setUrl_reward(String url_reward) {
        this.url_reward = url_reward;
    }

    public String getKode_reward() {
        return kode_reward;
    }

    public void setKode_reward(String kode_reward) {
        this.kode_reward = kode_reward;
    }
}
