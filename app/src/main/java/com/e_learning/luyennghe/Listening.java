package com.e_learning.luyennghe;

public class Listening {
    private int ID_Bai;
    private int ID_Bo;
    private String DapAn_A;
    private String DapAn_B;
    private String DapAn_C;
    private String DapAn_D;
    private String DapAn_True;
    private byte[] HinhAnh;
    private String Audio;

    public Listening() {
    }

    public Listening(int ID_Bai, int ID_Bo, String DapAn_A, String DapAn_B, String DapAn_C, String DapAn_D, String DapAn_True, byte[] HinhAnh, String Audio) {
        this.ID_Bai = ID_Bai;
        this.ID_Bo = ID_Bo;
        this.DapAn_A = DapAn_A;
        this.DapAn_B = DapAn_B;
        this.DapAn_C = DapAn_C;
        this.DapAn_D = DapAn_D;
        this.DapAn_True = DapAn_True;
        this.HinhAnh = HinhAnh;
        this.Audio = Audio;
    }

    public int getID_Bai() {
        return ID_Bai;
    }

    public void setID_Bai(int ID_Bai) {
        this.ID_Bai = ID_Bai;
    }

    public int getID_Bo() {
        return ID_Bo;
    }

    public void setID_Bo(int ID_Bo) {
        this.ID_Bo = ID_Bo;
    }

    public String getDapAn_A() {
        return DapAn_A;
    }

    public void setDapAn_A(String dapAn_A) {
        DapAn_A = dapAn_A;
    }

    public String getDapAn_B() {
        return DapAn_B;
    }

    public void setDapAn_B(String dapAn_B) {
        DapAn_B = dapAn_B;
    }

    public String getDapAn_C() {
        return DapAn_C;
    }

    public void setDapAn_C(String dapAn_C) {
        DapAn_C = dapAn_C;
    }

    public String getDapAn_D() {
        return DapAn_D;
    }

    public void setDapAn_D(String dapAn_D) {
        DapAn_D = dapAn_D;
    }

    public String getDapAn_True() {
        return DapAn_True;
    }

    public void setDapAn_True(String dapAn_True) {
        DapAn_True = dapAn_True;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        Audio = audio;
    }
}
