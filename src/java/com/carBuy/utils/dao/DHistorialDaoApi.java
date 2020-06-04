/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.DHistorial;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author kcram
 */
public interface DHistorialDaoApi {
    
    DHistorial getByDate(LocalDate fecha,Connection con)throws SQLException;
    
    DHistorial save(DHistorial dHistorial,Connection con)throws SQLException;
    
    boolean delete(int id_dhis,Connection con)throws SQLException;
}
