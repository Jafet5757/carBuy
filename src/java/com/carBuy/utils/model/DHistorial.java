/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.model;

import java.time.LocalDate;

/**
 *
 * @author kcram
 */
public class DHistorial {

    private int id_dhis;
    private String id_cli;
    private double totalpag;
    private int totalart;
    private LocalDate fecha;
    private boolean comprado;

    public int getId_dhis() {
        return id_dhis;
    }

    public void setId_dhis(int id_dhis) {
        this.id_dhis = id_dhis;
    }

    public String getId_cli() {
        return id_cli;
    }

    public void setId_cli(String id_cli) {
        this.id_cli = id_cli;
    }

    public double getTotalpag() {
        return totalpag;
    }

    public void setTotalpag(double totalpag) {
        this.totalpag = totalpag;
    }

    public int getTotalart() {
        return totalart;
    }

    public void setTotalart(int totalart) {
        this.totalart = totalart;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }
}
