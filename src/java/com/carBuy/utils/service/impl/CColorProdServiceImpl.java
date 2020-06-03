/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CColorProdDaoImpl;
import com.carBuy.utils.model.CColorProd;
import com.carBuy.utils.service.CColorProdServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CColorProdServiceImpl implements CColorProdServiceAPI{
    
    private CColorProdDaoImpl cColorProdDaoImpl = new CColorProdDaoImpl(){};

    @Override
    public CColorProd get(int id_ccp, Connection con) throws SQLException {
        try{
            return cColorProdDaoImpl.get(id_ccp,con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }
    
}
