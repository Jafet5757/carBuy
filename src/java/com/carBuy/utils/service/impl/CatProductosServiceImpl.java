/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CatProductosDaoImpl;
import com.carBuy.utils.model.CatProductos;
import com.carBuy.utils.service.CatProductosServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class CatProductosServiceImpl implements CatProductosServiceAPI {

    private CatProductosDaoImpl catProductosDaoImpl = new CatProductosDaoImpl() {
    };

    private Pattern noStndr = Pattern.compile("[^();''*--]+");

    @Override
    public CatProductos get(int id_prod, Connection con) throws SQLException {
        try {
            return catProductosDaoImpl.get(id_prod, con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public CatProductos add(CatProductos catProductos, Connection con) throws SQLException {
        try {
            return catProductosDaoImpl.add(catProductos, con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean delete(CatProductos catProductos, Connection con) throws SQLException {
        if (checkTipo(catProductos.getTipo_prod())) {
            try {
                return catProductosDaoImpl.delete(catProductos, con);
            } catch (SQLException ex) {
                return false;
            } finally {
                con.close();
            }
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<CatProductos> getAll(Connection con) throws SQLException {
        try {
            return catProductosDaoImpl.getAll(con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    private boolean checkTipo(String tipo) {
        Matcher mat1 = noStndr.matcher(tipo);
        if (mat1.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
