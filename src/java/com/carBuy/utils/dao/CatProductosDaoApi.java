/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao;

import com.carBuy.utils.model.CatProductos;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public interface CatProductosDaoApi {
    CatProductos get(int id_prod,Connection con)throws SQLException;
}
