/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author JAFET & kcram
 */
public interface CCarritoCDaoApi {
    
    boolean delete(String id_cli, Connection con)throws SQLException;
}
