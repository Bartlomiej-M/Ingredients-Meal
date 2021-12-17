package com.example.ingredientsmeal.menuFragments.menuModels;

public class DishModel {
    String Poziom, Zdjecie, CzasTrwania, EnergiaWporcji, Porcja;

    //a blank constructor//
    public DishModel() {

    }

    public DishModel(String Poziom, String Zdjecie, String CzasTrwania, String EnergiaWporcji, String Porcja) {
        this.Poziom = Poziom;
        this.Zdjecie = Zdjecie;
        this.CzasTrwania = CzasTrwania;
        this.EnergiaWporcji = EnergiaWporcji;
        this.Porcja = Porcja;
    }

    public String getCzasTrwania() {
        return CzasTrwania;
    }

    public void setCzasTrwania(String czasTrwania) {
        CzasTrwania = czasTrwania;
    }

    public String getEnergiaWporcji() {
        return EnergiaWporcji;
    }

    public void setEnergiaWporcji(String energiaWporcji) {
        EnergiaWporcji = energiaWporcji;
    }

    public String getPorcja() {
        return Porcja;
    }

    public void setPorcja(String Porcja) {
        Porcja = Porcja;
    }

    public String getPoziom() {
        return Poziom;
    }

    public void setPoziom(String poziom) {
        Poziom = poziom;
    }

    public String getZdjecie() {
        return Zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        Zdjecie = zdjecie;
    }
}