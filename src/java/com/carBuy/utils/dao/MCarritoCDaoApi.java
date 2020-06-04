/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.MCarrito_Compra;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface MCarritoCDaoApi {
    
    boolean add(MCarrito_Compra mCarrito_Compra,Connection con)throws SQLException;
    
    boolean quit(int id_mcc,String id_cli,Connection con)throws SQLException;
    
    boolean quitAll(String id_cli,Connection con)throws SQLException;
    
    boolean deleteByCli(String id_cli,Connection con)throws SQLException;
    
    boolean buy(MCarrito_Compra mCarrito_Compra,Connection con)throws SQLException;
    
    boolean buyAll(String id_cli,int id_dhis,Connection con)throws SQLException;
    
    ArrayList<MCarrito_Compra> getAllUnreg(String id_cli,Connection con)throws SQLException;
    
    ArrayList<MCarrito_Compra> getAllByCli(String id_cli,Connection con)throws SQLException;
    
    ArrayList<MCarrito_Compra> getAllByHis(int id_dhis,Connection con)throws SQLException;
}
