package com.example.quanlydonhang;

public class HoaDon {
    int sohd;
    String ngaylap;
    String ngaygiao;
    int makh;

    public HoaDon(int sohd, String ngaylap, String ngaygiao, int makh) {
        this.sohd = sohd;
        this.ngaylap = ngaylap;
        this.ngaygiao = ngaygiao;
        this.makh = makh;
    }

    public int getSohd() {
        return sohd;
    }

    public void setSohd(int sohd) {
        this.sohd = sohd;
    }

    public String getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(String ngaylap) {
        this.ngaylap = ngaylap;
    }

    public String getNgaygiao() {
        return ngaygiao;
    }

    public void setNgaygiao(String ngaygiao) {
        this.ngaygiao = ngaygiao;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }
}
