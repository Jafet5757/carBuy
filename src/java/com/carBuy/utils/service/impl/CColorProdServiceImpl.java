/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CColorProdDaoImpl;
import com.carBuy.utils.model.CColorProd;
import com.carBuy.utils.service.CColorProdServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class CColorProdServiceImpl implements CColorProdServiceAPI {

    private CColorProdDaoImpl cColorProdDaoImpl = new CColorProdDaoImpl() {
    };

    private Pattern noLetts = Pattern.compile("[^\\p{Punct}\\p{Alpha}]+");

    @Override
    public CColorProd get(int id_ccp, Connection con) throws SQLException {
        try {
            return cColorProdDaoImpl.get(id_ccp, con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public CColorProd add(CColorProd cColorProd, Connection con) throws SQLException {
        if (checkTipo(cColorProd.getTipo_ccp())) {
            try {
                return cColorProdDaoImpl.add(cColorProd, con);
            } catch (SQLException ex) {
                return null;
            } finally {
                con.close();
            }
        } else {
            return null;
        }

    }

    @Override
    public boolean delete(int id_ccp, Connection con) throws SQLException {
        try {
            return cColorProdDaoImpl.delete(id_ccp, con);
        } catch (SQLException ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public ArrayList<CColorProd> getAll(Connection con) throws SQLException {
        try {
            return cColorProdDaoImpl.getAll(con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    private boolean checkTipo(String tipo) {
        Matcher mat1 = noLetts.matcher(tipo);
        if (mat1.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
