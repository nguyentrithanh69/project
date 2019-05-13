package com.example.quanlydonhang;

public class MatHang {
    int mahang;
    String tenhang;
    String dvt;
    int dongia;

    public MatHang(int mahang, String tenhang, String dvt, int dongia) {
        this.mahang = mahang;
        this.tenhang = tenhang;
        this.dvt = dvt;
        this.dongia = dongia;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }
}
