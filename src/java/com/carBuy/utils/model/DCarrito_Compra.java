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
public class DCarrito_Compra {
    private int id_dcc;
    private int id_dprod;
    private int Cantidad_dcc;
    private double Sub_total_dcc;
    private double Iva_dcc;
    private double Total_dcc;

    public int getId_dcc() {
        return id_dcc;
    }

    public void setId_dcc(int id_dcc) {
        this.id_dcc = id_dcc;
    }

    public int getId_dprod() {
        return id_dprod;
    }

    public void setId_dprod(int id_dprod) {
        this.id_dprod = id_dprod;
    }

    public int getCantidad_dcc() {
        return Cantidad_dcc;
    }

    public void setCantidad_dcc(int Cantidad_dcc) {
        this.Cantidad_dcc = Cantidad_dcc;
    }

    public double getSub_total_dcc() {
        return Sub_total_dcc;
    }

    public void setSub_total_dcc(double Sub_total_dcc) {
        this.Sub_total_dcc = Sub_total_dcc;
    }

    public double getIva_dcc() {
        return Iva_dcc;
    }

    public void setIva_dcc(double Iva_dcc) {
        this.Iva_dcc = Iva_dcc;
    }

    public double getTotal_dcc() {
        return Total_dcc;
    }

    public void setTotal_dcc(double Total_dcc) {
        this.Total_dcc = Total_dcc;
    }
}
