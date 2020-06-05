/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.MCarrito_Compra;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface MCarritoCDaoApi {
    
    MCarrito_Compra add(MCarrito_Compra mCarrito_Compra,Connection con)throws SQLException;
    
    boolean delete(int id_mcc,Connection con)throws SQLException;
    
    boolean deleteAll(int id_dhis,Connection con)throws SQLException;
    
    ArrayList<MCarrito_Compra> getAll(int id_dhis,Connection con)throws SQLException;
}
