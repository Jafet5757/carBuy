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
public class MCarrito_Compra {
    private int id_mcc;
    private int id_dprod;
    private int id_dhis;
    private int Cantidad;
    private double Sub_total;
    private double Iva;
    private double Total;

    public int getId_mcc() {
        return id_mcc;
    }

    public void setId_mcc(int id_mcc) {
        this.id_mcc = id_mcc;
    }

    public int getId_dprod() {
        return id_dprod;
    }

    public void setId_dprod(int id_dprod) {
        this.id_dprod = id_dprod;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getSub_total() {
        return Sub_total;
    }

    public void setSub_total(double Sub_total) {
        this.Sub_total = Sub_total;
    }

    public double getIva() {
        return Iva;
    }

    public void setIva(double Iva) {
        this.Iva = Iva;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public int getId_dhis() {
        return id_dhis;
    }

    public void setId_dhis(int id_dhis) {
        this.id_dhis = id_dhis;
    }

}
