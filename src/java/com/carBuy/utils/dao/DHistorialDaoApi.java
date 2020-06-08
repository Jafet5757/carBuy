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
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface DHistorialDaoApi {
    
    DHistorial get(String id_cli,Connection con)throws SQLException;
    
    ArrayList<DHistorial> getHis(String id_cli,Connection con)throws SQLException;
    
    ArrayList<DHistorial> getByDate(LocalDate fecha_ini,LocalDate fecha_end,Connection con)throws SQLException;
    
    DHistorial modify(DHistorial dHistorial,Connection con)throws SQLException;
    
    DHistorial add(DHistorial dHistorial,Connection con)throws SQLException;
    
    boolean confirm(String id_cli,int id_dhis,Connection con)throws SQLException;
    
    boolean cancell(String id_cli,int id_dhis,Connection con)throws SQLException;
    
    boolean deleteAll(String id_cli,Connection con)throws SQLException;
    
    boolean instantSave(DHistorial dHistorial,Connection con)throws SQLException;
}
