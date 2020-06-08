/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CColorProdDaoApi;
import com.carBuy.utils.model.CColorProd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class CColorProdDaoImpl implements CColorProdDaoApi {

    @Override
    public CColorProd get(int id_ccp, Connection con) throws SQLException {
        try {
            CColorProd cColorProd = new CColorProd();
            PreparedStatement ps = con.prepareStatement("select * from ccolorprod "
                    + "where id_ccp=?");
            ps.setInt(1, id_ccp);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                cColorProd.setId_ccp(rs.getInt("id_ccp"));
                cColorProd.setTipo_ccp(rs.getString("tipo_ccp"));
                return cColorProd;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public CColorProd add(CColorProd cColorProd, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into ccolorprod"
                    + "(id_ccp,tipo_ccp) "
                    + "values(null,?)");
            ps.setString(1, cColorProd.getTipo_ccp());
            ps.executeUpdate();
            ps.close();
            return cColorProd;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean delete(int id_ccp, Connection con) throws SQLException {
        try {
            if (!checkExistence(id_ccp, con)) {
                return false;
            } else {
                PreparedStatement ps = con.prepareStatement("delete from ccolorprod "
                        + "where id_ccp=? limit 1");
                ps.setInt(id_ccp, id_ccp);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean checkExistence(int id_ccp, Connection con) {
        try {
            CColorProd cColorProd = new CColorProd();
            PreparedStatement ps = con.prepareStatement("select * from ccolorprod "
                    + "where id_ccp=?");
            ps.setInt(1, id_ccp);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                return false;
            } else {
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<CColorProd> getAll(Connection con) throws SQLException {
        try {
            ArrayList<CColorProd> cColorProdArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from ccolorprod");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    CColorProd cColorProd = new CColorProd();
                    cColorProd.setId_ccp(rs.getInt("id_ccp"));
                    cColorProd.setTipo_ccp(rs.getString("tipo_ccp"));
                    cColorProdArray.add(cColorProd);
                }
                return cColorProdArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
}
