/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.ClienteDaoImpl;
import com.carBuy.utils.model.Cliente;
import com.carBuy.utils.service.ClienteServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class ClienteServiceImpl implements ClienteServiceAPI{
    
    private ClienteDaoImpl clienteDaoImpl = new ClienteDaoImpl() {};
    
    private Pattern noNumbs = Pattern.compile("[^\\p{Punct}\\p{Digit}]+");
    private Pattern noLetts = Pattern.compile("[^\\p{Punct}\\p{Alpha}]+");
    private Pattern noStndr = Pattern.compile("[^();''*--]+");
    
    @Override
    public Cliente save(Cliente cliente, Connection con) throws SQLException {
        if(checkClient(cliente)){
            try{
                return clienteDaoImpl.save(cliente, con);
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
                return clienteDaoImpl.delete(id, pass, con);
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
    public Cliente modify(String id, String pass, Cliente cliente, Connection con) throws SQLException {
        if(checkClient(cliente) && checkKeys(id, pass)){
            try{
                return clienteDaoImpl.modify(id, pass, cliente, con);
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
    public Cliente get(String id, String pass, Connection con) throws SQLException {
        if(checkKeys(id, pass)){
            try{
                return clienteDaoImpl.get(id, pass, con);
            }catch(SQLException ex){
                return null;
            }finally{
                con.close();
            }
        }else{
            return null;
        }
    }
    
    private boolean checkClient(Cliente cliente){
        String id_cli = cliente.getId_cli();
        String nom_cli = cliente.getNom_cli();
        String app_cli = cliente.getApp_cli();
        String apm_cli = cliente.getApm_cli();
        String dir_cli = cliente.getDir_cli();
        String tel_cli = cliente.getTel_cli();
        String cel_cli = cliente.getCel_cli();
        String pass_cli = cliente.getPass_cli();
        if(id_cli.length()<31 && nom_cli.length()<21 && app_cli.length()<21 && apm_cli.length()<21
                && dir_cli.length()<101 && tel_cli.length()<13 && cel_cli.length()<13 && pass_cli.length()<51){
            Matcher mat1 = noStndr.matcher(id_cli);
            Matcher mat2 = noNumbs.matcher(nom_cli);
            Matcher mat3 = noNumbs.matcher(app_cli);
            Matcher mat4 = noNumbs.matcher(apm_cli);
            Matcher mat6 = noStndr.matcher(dir_cli);
            Matcher mat7 = noLetts.matcher(tel_cli);
            Matcher mat8 = noLetts.matcher(cel_cli);
            Matcher mat9 = noStndr.matcher(pass_cli);
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
    public Cliente getCookie(String id, Connection con) throws SQLException {
        if(checkID(id)){
            try{
                return clienteDaoImpl.getCookie(id, con);
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
