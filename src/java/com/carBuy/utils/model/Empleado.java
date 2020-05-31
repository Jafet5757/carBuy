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
public class Empleado {
    private String id_emp;
    private String nom_emp;
    private String app_emp;
    private String apm_emp;
    private LocalDate fnac_emp;
    private String dir_emp;
    private String tel_emp;
    private String cel_emp;
    private String pass_emp;
    private int id_cpe;

    public LocalDate getFnac_emp() {
        return fnac_emp;
    }

    public void setFnac_emp(LocalDate fnac_emp) {
        this.fnac_emp = fnac_emp;
    }

    public String getId_emp() {
        return id_emp;
    }

    public void setId_emp(String id_emp) {
        this.id_emp = id_emp;
    }

    public String getNom_emp() {
        return nom_emp;
    }

    public void setNom_emp(String nom_emp) {
        this.nom_emp = nom_emp;
    }

    public String getApp_emp() {
        return app_emp;
    }

    public void setApp_emp(String app_emp) {
        this.app_emp = app_emp;
    }

    public String getApm_emp() {
        return apm_emp;
    }

    public void setApm_emp(String apm_emp) {
        this.apm_emp = apm_emp;
    }

    public String getDir_emp() {
        return dir_emp;
    }

    public void setDir_emp(String dir_emp) {
        this.dir_emp = dir_emp;
    }

    public String getTel_emp() {
        return tel_emp;
    }

    public void setTel_emp(String tel_emp) {
        this.tel_emp = tel_emp;
    }

    public String getCel_emp() {
        return cel_emp;
    }

    public void setCel_emp(String cel_emp) {
        this.cel_emp = cel_emp;
    }

    public String getPass_emp() {
        return pass_emp;
    }

    public void setPass_emp(String pass_emp) {
        this.pass_emp = pass_emp;
    }

    public int getId_cpe() {
        return id_cpe;
    }

    public void setId_cpe(int id_cpe) {
        this.id_cpe = id_cpe;
    }
    
}
