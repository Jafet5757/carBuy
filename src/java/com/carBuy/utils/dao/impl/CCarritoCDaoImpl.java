/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CCarritoCDaoApi;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author JAFET
 */
public class CCarritoCDaoImpl implements CCarritoCDaoApi {

    @Override
    public boolean delete(String id_cli, Connection cn) throws Exception {
        //no coloque try catch adentro por si hay exceptions que se vean en el controlador
        PreparedStatement ps =cn.prepareStatement("DELET * FROM ccarrito_compras WHERE id_cli=?");
        ps.setString(1,id_cli);
        ps.executeUpdate();
        ps.close();
        return true;
    }
    
}
