/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.DProductosDaoImpl;
import com.carBuy.utils.model.DProductos;
import com.carBuy.utils.service.DProductosServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class DProductosServiceImpl implements DProductosServiceAPI{
    
    private DProductosDaoImpl dProductosDaoImpl = new DProductosDaoImpl(){};

    @Override
    public ArrayList<DProductos> getAll(Connection con) throws SQLException {
        try{
            return dProductosDaoImpl.getAll(con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public DProductos get(int id_dprod, Connection con) throws SQLException {
        try{
            return dProductosDaoImpl.get(id_dprod,con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public DProductos modify(DProductos dProductos, Connection con) throws SQLException {
        try{
            return dProductosDaoImpl.modify(dProductos, con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean delete(int id_dprod, Connection con) throws SQLException {
       try{
            return dProductosDaoImpl.delete(id_dprod, con);
        }catch(SQLException ex){
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public DProductos add(DProductos dProductos, Connection con) throws SQLException {
        try{
            return dProductosDaoImpl.add(dProductos, con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }
    
}
