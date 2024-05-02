package com.e_learning.tuvung;

import java.io.Serializable;

public class TuVung implements Serializable {
    private int idtu;
    private int idbo;
    private String tu;
    private String dichnghia;
    private String loaitu;
    private String audio;
    private byte[] anh;

    private int id_chude;

    public TuVung(int idtu, int idbo, String tu, String dichnghia, String loaitu, String audio, byte[] anh, int id_chude) {
        this.idtu = idtu;
        this.idbo = idbo;
        this.tu = tu;
        this.dichnghia = dichnghia;
        this.loaitu = loaitu;
        this.audio = audio;
        this.anh = anh;
        this.id_chude= id_chude;
    }

    public int getIdtu() {
        return idtu;
    }

    public void setIdtu(int idtu) {
        this.idtu = idtu;
    }

    public int getIdbo() {
        return idbo;
    }

    public void setIdbo(int idbo) {
        this.idbo = idbo;
    }

    public String getTu() {
        return tu;
    }

    public void setTu(String tu) {
        this.tu = tu;
    }

    public String getDichnghia() {
        return dichnghia;
    }

    public void setDichnghia(String dichnghia) {
        this.dichnghia = dichnghia;
    }

    public String getLoaitu() {
        return loaitu;
    }

    public void setLoaitu(String loaitu) {
        this.loaitu = loaitu;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public int getId_chude() {
        return id_chude;
    }

    public void setId_chude(int id_chude) {
        this.id_chude = id_chude;
    }
}

