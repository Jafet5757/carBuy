/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.Empleado;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public interface EmpleadoDaoAPI {
    
    Empleado save(Empleado empleado, Connection con) throws SQLException;
    
    boolean delete(String id,String pass, Connection con) throws SQLException;
    
    Empleado modify(String id,String pass,Empleado empleado, Connection con) throws SQLException;
    
    Empleado get(String id,String pass, Connection con) throws SQLException;
    
    ArrayList<Empleado> getAll(Connection con) throws SQLException;
}
