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
    public boolean add(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into mcarrito_compra"
                    + "(id_mcc,id_cli,id_dprod,id_dhis,cantidad,sub_total,iva,total,comprado) "
                    + "values(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, mCarrito_Compra.getId_mcc());
            ps.setString(2, mCarrito_Compra.getId_cli());
            ps.setInt(3, mCarrito_Compra.getId_dprod());
            ps.setInt(4, mCarrito_Compra.getId_dhis());
            ps.setInt(5, mCarrito_Compra.getCantidad());
            ps.setDouble(6, mCarrito_Compra.getSub_total());
            ps.setDouble(7, mCarrito_Compra.getIva());
            ps.setDouble(8, mCarrito_Compra.getTotal());
            ps.setBoolean(9, mCarrito_Compra.isComprado());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean quit(int id_mcc, String id_cli, Connection con) throws SQLException {
        try {
            if (!checkExistenceUni(id_mcc, id_cli, con)) {
                return false;
            } else {
                PreparedStatement ps = con.prepareStatement("delete from mcarrito_compra "
                        + "where id_cli=? "
                        + "and id_mcc=? "
                        + "and comprado=false "
                        + "limit 1");
                ps.setString(1, id_cli);
                ps.setInt(2, id_mcc);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean quitAll(String id_cli, Connection con) throws SQLException {
        try {
            if (!checkExistenceMul(id_cli, con)) {
                return false;
            } else {
                PreparedStatement ps = con.prepareStatement("delete from mcarrito_compra "
                        + "where id_cli=? "
                        + "and comprado=false ");
                ps.setString(1, id_cli);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteByCli(String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("delete from mcarrito_compra "
                    + "where id_cli=? ");
            ps.setString(1, id_cli);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean buy(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into mcarrito_compra"
                    + "(id_mcc,id_cli,id_dprod,id_dhis,cantidad,sub_total,iva,total,comprado) "
                    + "values(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, mCarrito_Compra.getId_mcc());
            ps.setString(2, mCarrito_Compra.getId_cli());
            ps.setInt(3, mCarrito_Compra.getId_dprod());
            ps.setInt(4, mCarrito_Compra.getId_dhis());
            ps.setInt(5, mCarrito_Compra.getCantidad());
            ps.setDouble(6, mCarrito_Compra.getSub_total());
            ps.setDouble(7, mCarrito_Compra.getIva());
            ps.setDouble(8, mCarrito_Compra.getTotal());
            ps.setBoolean(9, mCarrito_Compra.isComprado());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean buyAll(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("update cliente set "
                    + "comprado=true, id_dhis=? where id_cli=? and comprado=false");
            ps.setInt(1, id_dhis);
            ps.setString(2, id_cli);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAllUnreg(String id_cli, Connection con) throws SQLException {
        try {
            ArrayList<MCarrito_Compra> mCarrito_CompraArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_cli=? and comprado=false");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    MCarrito_Compra mCarrito_Compra = new MCarrito_Compra();
                    mCarrito_Compra.setId_mcc(rs.getInt("id_mcc"));
                    mCarrito_Compra.setId_cli(rs.getString("id_cli"));
                    mCarrito_Compra.setId_dprod(rs.getInt("id_prod"));
                    mCarrito_Compra.setId_dhis(rs.getInt("id_dhis"));
                    mCarrito_Compra.setCantidad(rs.getInt("cantidad"));
                    mCarrito_Compra.setSub_total(rs.getInt("sub_total"));
                    mCarrito_Compra.setSub_total(rs.getDouble("sub_total"));
                    mCarrito_Compra.setIva(rs.getDouble("iva"));
                    mCarrito_Compra.setTotal(rs.getDouble("total"));
                    mCarrito_Compra.setComprado(rs.getBoolean("comprado"));
                    mCarrito_CompraArray.add(mCarrito_Compra);
                }
                return mCarrito_CompraArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    private boolean checkExistenceUni(int id_mcc, String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_cli=? "
                    + "and id_mcc=? "
                    + "and comprado=false");
            ps.setString(1, id_cli);
            ps.setInt(2, id_mcc);
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

    private boolean checkExistenceMul(String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_cli=? "
                    + "and comprado=false");
            ps.setString(1, id_cli);
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
    public ArrayList<MCarrito_Compra> getAllByCli(String id_cli, Connection con) throws SQLException {
        try {
            ArrayList<MCarrito_Compra> mCarrito_CompraArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_cli=? and comprado=true");
            ps.setString(1, id_cli);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    MCarrito_Compra mCarrito_Compra = new MCarrito_Compra();
                    mCarrito_Compra.setId_mcc(rs.getInt("id_mcc"));
                    mCarrito_Compra.setId_cli(rs.getString("id_cli"));
                    mCarrito_Compra.setId_dprod(rs.getInt("id_prod"));
                    mCarrito_Compra.setId_dhis(rs.getInt("id_dhis"));
                    mCarrito_Compra.setCantidad(rs.getInt("cantidad"));
                    mCarrito_Compra.setSub_total(rs.getInt("sub_total"));
                    mCarrito_Compra.setSub_total(rs.getDouble("sub_total"));
                    mCarrito_Compra.setIva(rs.getDouble("iva"));
                    mCarrito_Compra.setTotal(rs.getDouble("total"));
                    mCarrito_Compra.setComprado(rs.getBoolean("comprado"));
                    mCarrito_CompraArray.add(mCarrito_Compra);
                }
                return mCarrito_CompraArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAllByHis(int id_dhis, Connection con) throws SQLException {
        try {
            ArrayList<MCarrito_Compra> mCarrito_CompraArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from mcarrito_compra "
                    + "where id_dhis=? and comprado=true");
            ps.setInt(1, id_dhis);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    MCarrito_Compra mCarrito_Compra = new MCarrito_Compra();
                    mCarrito_Compra.setId_mcc(rs.getInt("id_mcc"));
                    mCarrito_Compra.setId_cli(rs.getString("id_cli"));
                    mCarrito_Compra.setId_dprod(rs.getInt("id_prod"));
                    mCarrito_Compra.setId_dhis(rs.getInt("id_dhis"));
                    mCarrito_Compra.setCantidad(rs.getInt("cantidad"));
                    mCarrito_Compra.setSub_total(rs.getInt("sub_total"));
                    mCarrito_Compra.setSub_total(rs.getDouble("sub_total"));
                    mCarrito_Compra.setIva(rs.getDouble("iva"));
                    mCarrito_Compra.setTotal(rs.getDouble("total"));
                    mCarrito_Compra.setComprado(rs.getBoolean("comprado"));
                    mCarrito_CompraArray.add(mCarrito_Compra);
                }
                return mCarrito_CompraArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
}
