/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CCarritoCDaoApi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JAFET
 */
public class CCarritoCDaoImpl implements CCarritoCDaoApi {

    @Override
    public boolean delete(String id_cli, Connection con) throws SQLException {
        try{
            if(checkExistence(id_cli, con)){
                PreparedStatement ps =con.prepareStatement("delete from ccarrito_compras "
                        + "where id_cli=? "
                        + "limit 1");
                ps.setString(1,id_cli);
                ps.executeUpdate();
                ps.close();
                return true;
            }else{
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
    }
    
    private boolean checkExistence(String id, Connection con) throws SQLException{
        try{
            PreparedStatement ps =con.prepareStatement("select * from ccarrito_compras "
                    + "where id_cli=?");
            ps.setString(1, id);
            ResultSet rs =ps.executeQuery();
            if(!rs.next()){
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
