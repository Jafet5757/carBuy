/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CatProductosDaoApi;
import com.carBuy.utils.model.CatProductos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class CatProductosDaoImpl implements CatProductosDaoApi {

    @Override
    public CatProductos get(int id_prod, Connection con) throws SQLException {
        try {
            CatProductos catProductos = new CatProductos();
            PreparedStatement ps = con.prepareStatement("select * from catproductos "
                    + "where id_prod=?");
            ps.setInt(1, id_prod);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                catProductos.setId_prod(rs.getInt("id_prod"));
                catProductos.setTipo_prod(rs.getString("tipo_prod"));
                return catProductos;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public CatProductos add(CatProductos catProductos, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareCall("insert into catproductos"
                    + "(id_prod,tipo_prod) "
                    + "values(null,?)");
            ps.setString(1, catProductos.getTipo_prod());
            ps.executeUpdate();
            ps.close();
            return catProductos;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean delete(CatProductos catProductos, Connection con) throws SQLException {
        try {
            if (!checkExistence(catProductos.getId_prod(), con)) {
                PreparedStatement ps = con.prepareStatement("delete from catproductos "
                        + "where id_prod=? limit 1");
                ps.setInt(1, catProductos.getId_prod());
                ps.executeUpdate();
                ps.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean checkExistence(int id_prod, Connection con) {
        try {
            CatProductos catProductos = new CatProductos();
            PreparedStatement ps = con.prepareStatement("select * from catproductos "
                    + "where id_prod=?");
            ps.setInt(1, id_prod);
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
    public ArrayList<CatProductos> getAll(Connection con) throws SQLException {
        try {
            ArrayList<CatProductos> catProductosArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from catproductos");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    CatProductos catProductos = new CatProductos();
                    catProductos.setId_prod(rs.getInt("id_prod"));
                    catProductos.setTipo_prod(rs.getString("tipo_prod"));
                    catProductosArray.add(catProductos);
                }
                return catProductosArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
}
