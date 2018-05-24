package com.example.mehmet.gymautomation.models;

public class Tarife {
    private  int id;
    private int tarife_id;
    private String tarife_adi;
    private int toplam_uye;
    //Getter and Setter Methods
    public int getToplam_uye() {
        return toplam_uye;
    }
    public void setToplam_uye(int toplam_uye) {
        this.toplam_uye = toplam_uye;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTarife_id() {
        return tarife_id;
    }
    public void setTarife_id(int tarife_id) {
        this.tarife_id = tarife_id;
    }
    public String getTarife_adi() {
        return tarife_adi;
    }
    public void setTarife_adi(String tarife_adi) {
        this.tarife_adi = tarife_adi;
    }


}
