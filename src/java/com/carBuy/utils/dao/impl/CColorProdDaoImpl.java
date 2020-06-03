/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CColorProdDaoApi;
import com.carBuy.utils.model.CColorProd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CColorProdDaoImpl implements CColorProdDaoApi{

    @Override
    public CColorProd get(int id_ccp,Connection con) throws SQLException {
        try{
            CColorProd cColorProd = new CColorProd();
            PreparedStatement ps = con.prepareStatement("select * from ccolorprod "
                    + "where id_ccp=?");
            ps.setInt(1, id_ccp);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                cColorProd.setId_ccp(rs.getInt("id_ccp"));
                cColorProd.setTipo_ccp(rs.getString("tipo_ccp"));
                return cColorProd;
            }
        }catch(SQLException ex){
            return null;
        }
    }
    
}
