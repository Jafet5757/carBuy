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
public class DProductos {
    private int id_dprod;
    private int id_prod;
    private int id_ccp;
    private int id_mprod;
    private double precio_prod;
    private int stock_prod;

    public int getId_dprod() {
        return id_dprod;
    }

    public void setId_dprod(int id_dprod) {
        this.id_dprod = id_dprod;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_ccp() {
        return id_ccp;
    }

    public void setId_ccp(int id_ccp) {
        this.id_ccp = id_ccp;
    }

    public int getId_mprod() {
        return id_mprod;
    }

    public void setId_mprod(int id_mprod) {
        this.id_mprod = id_mprod;
    }

    public double getPrecio_prod() {
        return precio_prod;
    }

    public void setPrecio_prod(double precio_prod) {
        this.precio_prod = precio_prod;
    }

    public int getStock_prod() {
        return stock_prod;
    }

    public void setStock_prod(int stock_prod) {
        this.stock_prod = stock_prod;
    }
}
