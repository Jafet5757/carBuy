/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.DProductosDaoApi;
import com.carBuy.utils.model.DProductos;
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

    @Override
    public DProductos modify(DProductos dProductos, Connection con) throws SQLException {
        try{
            if(!checkExistence(dProductos.getId_dprod(), con)){
                return null;
            }else{
                PreparedStatement ps = con.prepareStatement("update dproductos set "
                        + "id_dprod=?, id_prod=?, id_ccp=?, id_mprod=?, precio_prod=?, "
                        + "stock_prod=? "
                        + "where id_dprod=?");
                ps.setInt(1, dProductos.getId_dprod());
                ps.setInt(2, dProductos.getId_prod());
                ps.setInt(3, dProductos.getId_ccp());
                ps.setInt(4, dProductos.getId_mprod());
                ps.setDouble(5, dProductos.getPrecio_prod());
                ps.setInt(6, dProductos.getStock_prod());
                ps.setInt(7, dProductos.getId_dprod());
                ps.executeUpdate();
                ps.close();
                return dProductos;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public boolean delete(int id_dprod, Connection con) throws SQLException {
        try{
            if(!checkExistence(id_dprod, con)){
                return false;
            }else{
                PreparedStatement ps = con.prepareStatement("delete from dproductos "
                        + "where id_dprod=? limit 1");
                ps.setInt(1, id_dprod);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
    }

    @Override
    public DProductos add(DProductos dProductos, Connection con) throws SQLException {
        try{
            if(!checkExistence(dProductos.getId_dprod(), con)){
                return null;
            }else{
                PreparedStatement ps = con.prepareStatement("insert into dproductos"
                        + "(id_dprod, id_prod, id_ccp, id_mprod, precio_prod, stock_prod) "
                        + "values(null,?,?,?,?,?)");
                ps.setInt(1, dProductos.getId_prod());
                ps.setInt(2, dProductos.getId_ccp());
                ps.setInt(3, dProductos.getId_mprod());
                ps.setDouble(4, dProductos.getPrecio_prod());
                ps.setInt(5, dProductos.getStock_prod());
                ps.executeUpdate();
                ps.close();
                return dProductos;
            }
        }catch(SQLException ex){
            return null;
        }
    }
    
    private boolean checkExistence(int id, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * from dproductos "
                    + "where id_dprod=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                return false;
            } else {
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }
}
