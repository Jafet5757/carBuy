/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.model;

import java.time.LocalDateTime;

/**
 *
 * @author kcram
 */
public class CCarrito_Compras {
    private int id_ccc;
    private int id_dcc;
    private String id_cli;
    private LocalDateTime fecha_ccc;

    public int getId_ccc() {
        return id_ccc;
    }

    public void setId_ccc(int id_ccc) {
        this.id_ccc = id_ccc;
    }

    public int getId_dcc() {
        return id_dcc;
    }

    public void setId_dcc(int id_dcc) {
        this.id_dcc = id_dcc;
    }

    public String getId_cli() {
        return id_cli;
    }

    public void setId_cli(String id_cli) {
        this.id_cli = id_cli;
    }

    public LocalDateTime getFecha_ccc() {
        return fecha_ccc;
    }

    public void setFecha_ccc(LocalDateTime fecha_ccc) {
        this.fecha_ccc = fecha_ccc;
    }
}
