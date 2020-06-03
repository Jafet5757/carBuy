/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CatProductosDaoApi;
import com.carBuy.utils.model.CatProductos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CatProductosDaoImpl implements CatProductosDaoApi{

    @Override
    public CatProductos get(int id_prod, Connection con) throws SQLException {
        try{
            CatProductos catProductos = new CatProductos();
            PreparedStatement ps = con.prepareStatement("select * from catproductos "
                    + "where id_prod=?");
            ps.setInt(1, id_prod);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                catProductos.setId_prod(rs.getInt("id_prod"));
                catProductos.setTipo_prod(rs.getString("tipo_prod"));
                return catProductos;
            }
        }catch(SQLException ex){
            return null;
        }
    }
    
}
