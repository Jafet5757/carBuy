/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.model;

/**
 *
 * @author kcram
 */
public class MProductos {
    private int id_mprod;
    private String nom_prod;
    private String img_prod;
    private String des_prod;

    public int getId_mprod() {
        return id_mprod;
    }

    public void setId_mprod(int id_mprod) {
        this.id_mprod = id_mprod;
    }

    public String getNom_prod() {
        return nom_prod;
    }

    public void setNom_prod(String nom_prod) {
        this.nom_prod = nom_prod;
    }

    public String getImg_prod() {
        return img_prod;
    }

    public void setImg_prod(String img_prod) {
        this.img_prod = img_prod;
    }

    public String getDes_prod() {
        return des_prod;
    }

    public void setDes_prod(String des_prod) {
        this.des_prod = des_prod;
    }
    
}
