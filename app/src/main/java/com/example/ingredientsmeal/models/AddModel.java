package com.example.ingredientsmeal.models;

public class AddModel {

    public String czasTrwania, energiaWporcji, poziom, porcja, zdjecie;

    public AddModel(String duration, String portion, String level, String energy, String foto) {
        this.czasTrwania = duration;
        this.energiaWporcji = portion;
        this.poziom = level;
        this.porcja = energy;
        this.zdjecie = foto;
    }

    public String getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(String czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public String getEnergiaWporcji() {
        return energiaWporcji;
    }

    public void setEnergiaWporcji(String energiaWporcji) {
        this.energiaWporcji = energiaWporcji;
    }

    public String getPoziom() {
        return poziom;
    }

    public void setPoziom(String poziom) {
        this.poziom = poziom;
    }

    public String getPorcja() {
        return porcja;
    }

    public void setPorcja(String porcja) {
        this.porcja = porcja;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }
}