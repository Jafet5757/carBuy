/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.DProductos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface DProductosDaoApi {
    
    DProductos get(int id_dprod, Connection con) throws SQLException;
    
    ArrayList<DProductos> getAll(Connection con) throws SQLException;
    
    DProductos modify(DProductos dProductos,Connection con) throws SQLException;
    
    boolean delete(int id_dprod, Connection con) throws SQLException;
    
    DProductos add(DProductos dProductos,Connection con) throws SQLException;
    
}
