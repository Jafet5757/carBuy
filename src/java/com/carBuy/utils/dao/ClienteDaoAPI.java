/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.Cliente;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public interface ClienteDaoAPI{
    
    Cliente save(Cliente cliente, Connection con) throws SQLException;
    
    boolean delete(String id,String pass, Connection con) throws SQLException;
    
    Cliente modify(String id,String pass,Cliente cliente, Connection con) throws SQLException;
    
    Cliente get(String id,String pass, Connection con) throws SQLException;
    
    Cliente getCookie(String id, Connection con) throws SQLException;
}
