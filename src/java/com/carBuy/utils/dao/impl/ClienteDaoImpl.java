/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.ClienteDaoAPI;
import com.carBuy.utils.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author kcram
 */
@WebServlet(name = "ClienteDaoImpl", urlPatterns = {"/ClienteDaoImpl"})
public class ClienteDaoImpl extends HttpServlet implements ClienteDaoAPI{
    
    @Resource(name="jdbc/dbPool")
    private DataSource datasource;

    @Override
    public Cliente save(Cliente cliente, Connection con) throws SQLException{
        try{
            PreparedStatement ps =con.prepareStatement("insert into cliente"
                    + "(id_cli,nom_cli,app_cli,apm_cli,fnac_cli,dir_cli,tel_cli,cel_cli,pass_cli) "
                    + "values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, cliente.getId_cli());
            ps.setString(2, cliente.getNom_cli());
            ps.setString(3, cliente.getApp_cli());
            ps.setString(4, cliente.getApm_cli());
            ps.setDate(5, Date.valueOf(cliente.getFnac_cli()));
            ps.setString(6, cliente.getDir_cli());
            ps.setString(7, cliente.getTel_cli());
            ps.setString(8, cliente.getCel_cli());
            ps.setString(9, cliente.getPass_cli());
            ps.executeUpdate();
            ps.close();
            return cliente;
        }catch(SQLException ex){
            System.err.println(ex);
            return null;
        }
    }

    @Override
    public boolean delete(String id, String pass, Connection con) throws SQLException{
        try{
            if(!checkExistence(id, pass, con)){
                return false;
            }else{
                PreparedStatement ps =con.prepareStatement("delete from cliente "
                    + "where id_cli=? "
                    + "and pass_cli=? "
                    + "limit 1");
                ps.setString(1, id);
                ps.setString(2, pass);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
    }

    @Override
    public Cliente modify(String id, String pass, Cliente cliente, Connection con) throws SQLException{
        try{
            if(!checkExistence(id, pass, con)){
                return null;
            }else{
                PreparedStatement ps =con.prepareStatement("update cliente set "
                    + "id_cli=?, nom_cli=?, app_cli=?, apm_cli=?, fnac_cli=?, "
                    + "dir_cli=?, tel_cli=?, cel_cli=?, pass_cli=? "
                    + "where id_cli=? "
                    + "and pass_cli=?");
                ps.setString(1, cliente.getId_cli());
                ps.setString(2, cliente.getNom_cli());
                ps.setString(3, cliente.getApp_cli());
                ps.setString(4, cliente.getApm_cli());
                ps.setDate(5, Date.valueOf(cliente.getFnac_cli()));
                ps.setString(6, cliente.getDir_cli());
                ps.setString(7, cliente.getTel_cli());
                ps.setString(8, cliente.getCel_cli());
                ps.setString(9, cliente.getPass_cli());
                ps.setString(10, id);
                ps.setString(11, pass);
                ps.executeUpdate();
                ps.close();
                return cliente;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public Cliente get(String id, String pass, Connection con) throws SQLException {
        try{
            if(!checkExistence(id, pass, con)){
                return null;
            }else{
                PreparedStatement ps =con.prepareStatement("select * from cliente "
                    + "where id_cli=? "
                    + "and password=?");
                ps.setString(1, id);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();
                Cliente cliente = new Cliente();
                cliente.setId_cli(rs.getString("id_cli"));
                cliente.setNom_cli(rs.getString("nom_cli"));
                cliente.setApp_cli(rs.getString("app_cli"));
                cliente.setApm_cli(rs.getString("apm_cli"));
                cliente.setFnac_cli((rs.getDate("fnac_cli")).toLocalDate());
                cliente.setDir_cli(rs.getString("dir_cli"));
                cliente.setTel_cli(rs.getString("tel_cli"));
                cliente.setCel_cli(rs.getString("cel_cli"));
                cliente.setPass_cli(rs.getString("pass_cli"));
                ps.close();
                return cliente;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    private boolean checkExistence(String id, String pass, Connection con) throws SQLException{
        try{
            PreparedStatement ps =con.prepareStatement("select * from cliente "
                    + "where id_cli=? "
                    + "and pass_cli=?");
            ps.setString(1, id);
            ps.setString(2, pass);
            ResultSet rs =ps.executeQuery();
            if(rs.wasNull()){
                ps.close();
                return false;
            }else{
                ps.close();
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
    }
    
}
