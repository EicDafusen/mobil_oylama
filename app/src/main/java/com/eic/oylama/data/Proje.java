package com.eic.oylama.data;

public class Proje {

    private int projeId;
    private String projeAdi;
    private String projeSahaibi;
    private String projeOzet;


    private int oy1 ;
    private int oy2 ;
    private int oy3 ;
    private int oy4 ;

    public Proje(int projeId, String projeAdi, String projeSahaibi, String projeOzet) {
        this.projeId = projeId;
        this.projeAdi = projeAdi;
        this.projeSahaibi = projeSahaibi;
        this.projeOzet = projeOzet;
    }

    public  Proje(){

    }

    public void setProjeAdi(String projeAdi) {
        this.projeAdi = projeAdi;
    }

    public void setProjeSahaibi(String projeSahaibi) {
        this.projeSahaibi = projeSahaibi;
    }

    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public String getProjeOzet() {
        return projeOzet;
    }

    public void setProjeOzet(String projeOzet) {
        this.projeOzet = projeOzet;
    }

    public String getProjeAdi() {
        return projeAdi;
    }

    public String getProjeSahaibi() {
        return projeSahaibi;
    }

    public int getOy1() {
        return oy1;
    }

    public int getOy2() {
        return oy2;
    }

    public int getOy3() {
        return oy3;
    }

    public int getOy4() {
        return oy4;
    }
}
