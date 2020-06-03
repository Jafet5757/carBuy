/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.MProductosDaoImpl;
import com.carBuy.utils.model.MProductos;
import com.carBuy.utils.service.MProductosServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class MProductosServiceImpl implements MProductosServiceAPI{

    private MProductosDaoImpl mProductosDaoImpl = new MProductosDaoImpl(){};
    
    
    @Override
    public MProductos get(int id_mprod, Connection con) throws SQLException{
        try{
            return mProductosDaoImpl.get(id_mprod, con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public ArrayList<MProductos> getAll(Connection con) throws SQLException {
        try{
            return mProductosDaoImpl.getAll(con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }
    
}
