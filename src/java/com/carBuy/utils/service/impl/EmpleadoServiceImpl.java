/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.EmpleadoDaoImpl;
import com.carBuy.utils.model.Empleado;
import com.carBuy.utils.service.EmpleadoServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class EmpleadoServiceImpl implements EmpleadoServiceAPI{
    
    private EmpleadoDaoImpl empleadoDaoImpl = new EmpleadoDaoImpl() {};
    
    private Pattern noNumbs = Pattern.compile("[^\\p{Punct}\\p{Digit}]+");
    private Pattern noLetts = Pattern.compile("[^\\p{Punct}\\p{Alpha}]+");
    private Pattern noStndr = Pattern.compile("[^();''*--]+");

    @Override
    public Empleado save(Empleado empleado, Connection con) throws SQLException {
        if(checkEmp(empleado)){
            try{
                return empleadoDaoImpl.save(empleado, con);
            }catch(SQLException ex){
                return null;
            }finally{
                con.close();
            }
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(String id, String pass, Connection con) throws SQLException {
        if(checkKeys(id, pass)){
            try{
                return empleadoDaoImpl.delete(id, pass, con);
            }catch(SQLException ex){
                return false;
            }finally{
                con.close();
            }
        }else{
            return false;
        }
    }

    @Override
    public Empleado modify(String id, String pass, Empleado empleado, Connection con) throws SQLException {
        if(checkEmp(empleado) && checkKeys(id, pass)){
            try{
                return empleadoDaoImpl.modify(id, pass, empleado, con);
            }catch(SQLException ex){
                return null;
            }finally{
                con.close();
            }
        }else{
            return null;
        }
    }

    @Override
    public Empleado get(String id, String pass, Connection con) throws SQLException {
        if(checkKeys(id, pass)){
            try{
                return empleadoDaoImpl.get(id, pass, con);
            }catch(SQLException ex){
                return null;
            }finally{
                con.close();
            }
        }else{
            return null;
        }
    }
    
    private boolean checkEmp(Empleado empleado){
        String id_emp = empleado.getId_emp();
        String nom_emp = empleado.getNom_emp();
        String app_emp = empleado.getApp_emp();
        String apm_emp = empleado.getApm_emp();
        String dir_emp = empleado.getDir_emp();
        String tel_emp = empleado.getTel_emp();
        String cel_emp = empleado.getCel_emp();
        String pass_emp = empleado.getPass_emp();
        if(id_emp.length()<31 && nom_emp.length()<21 && app_emp.length()<21 && apm_emp.length()<21
                && dir_emp.length()<101 && tel_emp.length()<13 && cel_emp.length()<13 && pass_emp.length()<51){
            Matcher mat1 = noStndr.matcher(id_emp);
            Matcher mat2 = noNumbs.matcher(nom_emp);
            Matcher mat3 = noNumbs.matcher(app_emp);
            Matcher mat4 = noNumbs.matcher(apm_emp);
            Matcher mat6 = noStndr.matcher(dir_emp);
            Matcher mat7 = noLetts.matcher(tel_emp);
            Matcher mat8 = noLetts.matcher(cel_emp);
            Matcher mat9 = noStndr.matcher(pass_emp);
            if(mat1.matches() && mat2.matches() && mat3.matches() && mat4.matches()
                    && mat6.matches() && mat7.matches() && mat8.matches() && mat9.matches()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
        
    }
    
    private boolean checkKeys(String id, String pass){
        Matcher mat1 = noStndr.matcher(id);
        Matcher mat2 = noStndr.matcher(pass);
        if(mat1.matches() && mat2.matches()){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean checkID(String id){
        Matcher mat1 = noStndr.matcher(id);
        if(mat1.matches()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Empleado> getAll(Connection con) throws SQLException {
        try{
            return empleadoDaoImpl.getAll( con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public Empleado getCookie(String id, Connection con) throws SQLException {
        if(checkID(id)){
            try{
                return empleadoDaoImpl.getCookie(id, con);
            }catch(SQLException ex){
                return null;
            }finally{
                con.close();
            }
        }else{
            return null;
        }
    }
    
}
