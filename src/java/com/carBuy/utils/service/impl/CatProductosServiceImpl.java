/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CatProductosDaoImpl;
import com.carBuy.utils.model.CatProductos;
import com.carBuy.utils.service.CatProductosServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CatProductosServiceImpl implements CatProductosServiceAPI{
    
    private CatProductosDaoImpl catProductosDaoImpl = new CatProductosDaoImpl(){};

    @Override
    public CatProductos get(int id_prod, Connection con) throws SQLException {
        try{
            return catProductosDaoImpl.get(id_prod,con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }
    
}
