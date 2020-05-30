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
public class Cliente {
    
    private String id_cli;
    private String nom_cli;
    private String app_cli;
    private String apm_cli;
    private LocalDate fnac_cli;
    private String dir_cli;
    private String tel_cli;
    private String cel_cli;
    private String pass_cli; 

    public String getId_cli() {
        return id_cli;
    }

    public void setId_cli(String id_cli) {
        this.id_cli = id_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getApp_cli() {
        return app_cli;
    }

    public void setApp_cli(String app_cli) {
        this.app_cli = app_cli;
    }

    public String getApm_cli() {
        return apm_cli;
    }

    public void setApm_cli(String apm_cli) {
        this.apm_cli = apm_cli;
    }

    public LocalDate getFnac_cli() {
        return fnac_cli;
    }

    public void setFnac_cli(LocalDate fnac_cli) {
        this.fnac_cli = fnac_cli;
    }

    public String getDir_cli() {
        return dir_cli;
    }

    public void setDir_cli(String dir_cli) {
        this.dir_cli = dir_cli;
    }

    public String getTel_cli() {
        return tel_cli;
    }

    public void setTel_cli(String tel_cli) {
        this.tel_cli = tel_cli;
    }

    public String getCel_cli() {
        return cel_cli;
    }

    public void setCel_cli(String cel_cli) {
        this.cel_cli = cel_cli;
    }

    public String getPass_cli() {
        return pass_cli;
    }

    public void setPass_cli(String pass_cli) {
        this.pass_cli = pass_cli;
    }
}
