/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.MProductosDaoApi;
import com.carBuy.utils.model.MProductos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class MProductosDaoImpl implements MProductosDaoApi{

    @Override
    public MProductos get(int id_mprod, Connection con) throws SQLException{
        try{
            MProductos mProductos = new MProductos();
            PreparedStatement ps = con.prepareStatement("select * from mproductos "
                    + "where id_mprod=?");
            ps.setInt(1, id_mprod);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                mProductos.setId_mprod(rs.getInt("id_mprod"));
                mProductos.setNom_prod(rs.getString("nom_prod"));
                mProductos.setImg_prod(rs.getString("img_prod"));
                mProductos.setDes_prod(rs.getString("des_prod"));
                return mProductos;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public ArrayList<MProductos> getAll(Connection con) throws SQLException {
        try{
            ArrayList<MProductos> mProductosArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from mproductos");
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                rs.beforeFirst();
                while(rs.next()){
                    MProductos mProductos = new MProductos();
                    mProductos.setId_mprod(rs.getInt("id_mprod"));
                    mProductos.setNom_prod(rs.getString("nom_prod"));
                    mProductos.setImg_prod(rs.getString("img_prod"));
                    mProductos.setDes_prod(rs.getString("des_prod"));
                    mProductosArray.add(mProductos);
                }
                return mProductosArray;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    
}
