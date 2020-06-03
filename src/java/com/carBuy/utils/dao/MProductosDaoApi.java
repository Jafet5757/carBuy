/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.MProductos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface MProductosDaoApi {
    
    MProductos get(int id_mprod,Connection con) throws SQLException;
    
    ArrayList<MProductos> getAll(Connection con) throws SQLException;
}
