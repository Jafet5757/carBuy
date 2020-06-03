/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.DProductosDaoApi;
import com.carBuy.utils.model.DProductos;
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
public class DProductosDaoImpl implements DProductosDaoApi{

    @Override
    public ArrayList<DProductos> getAll(Connection con) throws SQLException {
        try{
            ArrayList<DProductos> dProductosArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from dproductos");
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                rs.beforeFirst();
                while(rs.next()){
                    DProductos dProductos = new DProductos();
                    dProductos.setId_dprod(rs.getInt("id_dprod"));
                    dProductos.setId_prod(rs.getInt("id_prod"));
                    dProductos.setId_ccp(rs.getInt("id_ccp"));
                    dProductos.setId_mprod(rs.getInt("id_mprod"));
                    dProductos.setPrecio_prod(rs.getInt("precio_prod"));
                    dProductos.setStock_prod(rs.getInt("stock_prod"));
                    dProductosArray.add(dProductos);
                }
                return dProductosArray;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public DProductos get(int id_dprod, Connection con) throws SQLException {
        try{
            DProductos dProductos = new DProductos();
            PreparedStatement ps = con.prepareStatement("select * from dproductos "
                    + "where id_dprod=?");
            ps.setInt(1, id_dprod);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                dProductos.setId_dprod(rs.getInt("id_dprod"));
                dProductos.setId_prod(rs.getInt("id_prod"));
                dProductos.setId_ccp(rs.getInt("id_ccp"));
                dProductos.setId_mprod(rs.getInt("id_mprod"));
                dProductos.setPrecio_prod(rs.getInt("precio_prod"));
                dProductos.setStock_prod(rs.getInt("stock_prod"));
                return dProductos;
            }
        }catch(SQLException ex){
            return null;
        }
    }
}
