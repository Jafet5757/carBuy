/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.CColorProd;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface CColorProdDaoApi {
    
    CColorProd get(int id_ccp, Connection con)throws SQLException;
    
    CColorProd add(CColorProd cColorProd, Connection con)throws SQLException;
    
    boolean delete(int id_ccp, Connection con)throws SQLException;
    
    ArrayList<CColorProd> getAll(Connection con)throws SQLException;
}
