package com.e_learning.tuvung;

public class ChuDeTuVung {
    private int idchude;
    private String tenchude;
    private byte[] hinhanh;

    public ChuDeTuVung(int idchude, String tenchude, byte[] hinhanh) {
        this.idchude = idchude;
        this.tenchude = tenchude;
        this.hinhanh = hinhanh;
    }

    public ChuDeTuVung(String tenchude, byte[] hinhanh) {
        this.tenchude = tenchude;
        this.hinhanh = hinhanh;
    }

    public int getIdchude() {
        return idchude;
    }

    public void setIdchude(int idchude) {
        this.idchude = idchude;
    }

    public String getTenchude() {
        return tenchude;
    }

    public void setTenchude(String tenchude) {
        this.tenchude = tenchude;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }
}
