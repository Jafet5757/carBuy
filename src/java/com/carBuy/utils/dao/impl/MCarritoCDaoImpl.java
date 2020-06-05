/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.MCarritoCDaoApi;
import com.carBuy.utils.model.MCarrito_Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class MCarritoCDaoImpl implements MCarritoCDaoApi {

    @Override
    public MCarrito_Compra add(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into mcarrito_compra"
                    + "(id_mcc,id_dhis,id_dprod,id_dhis,cantidad,sub_total,iva,total) "
                    + "values(?,?,?,?,?,?,?,?)");
            ps.setInt(1, mCarrito_Compra.getId_mcc());
            ps.setInt(2, mCarrito_Compra.getId_dhis());
            ps.setInt(3, mCarrito_Compra.getId_dprod());
            ps.setInt(4, mCarrito_Compra.getId_dhis());
            ps.setInt(5, mCarrito_Compra.getCantidad());
            ps.setDouble(6, mCarrito_Compra.getSub_total());
            ps.setDouble(7, mCarrito_Compra.getIva());
            ps.setDouble(8, mCarrito_Compra.getTotal());
            ps.executeUpdate();
            ps.close();
            return mCarrito_Compra;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean delete(int id_mcc, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("delete from mcarrito_compra "
                    + "where id_mcc=?");
            ps.setInt(1, id_mcc);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteAll(int id_dhis, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("delete from mcarrito_compra "
                    + "where id_dhis=?");
            ps.setInt(1, id_dhis);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAll(int id_dhis, Connection con) throws SQLException {
        try {
            ArrayList<MCarrito_Compra> mCarrito_CompraArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_dhis=?");
            ps.setInt(1, id_dhis);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    MCarrito_Compra mCarrito_Compra = new MCarrito_Compra();
                    mCarrito_Compra.setId_mcc(rs.getInt("id_mcc"));
                    mCarrito_Compra.setId_dhis(rs.getInt("id_dhis"));
                    mCarrito_Compra.setId_dprod(rs.getInt("id_prod"));
                    mCarrito_Compra.setId_dhis(rs.getInt("id_dhis"));
                    mCarrito_Compra.setCantidad(rs.getInt("cantidad"));
                    mCarrito_Compra.setSub_total(rs.getInt("sub_total"));
                    mCarrito_Compra.setSub_total(rs.getDouble("sub_total"));
                    mCarrito_Compra.setIva(rs.getDouble("iva"));
                    mCarrito_Compra.setTotal(rs.getDouble("total"));
                    mCarrito_CompraArray.add(mCarrito_Compra);
                }
                return mCarrito_CompraArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
}
