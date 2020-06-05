/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.DHistorialDaoApi;
import com.carBuy.utils.model.DHistorial;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class DHistorialDaoImpl implements DHistorialDaoApi {

    @Override
    public DHistorial get(String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * into dhistorial "
                    + "where id_cli=? and comprado=false");
            ps.setString(1, id_cli);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                DHistorial dHistorial = new DHistorial();
                dHistorial.setId_dhis(rs.getInt("id_dhis"));
                dHistorial.setId_cli(rs.getString("id_cli"));
                dHistorial.setTotalpag(rs.getDouble("totalpag"));
                dHistorial.setTotalart(rs.getInt("totalart"));
                dHistorial.setFecha(rs.getDate("fecha").toLocalDate());
                dHistorial.setComprado(rs.getBoolean("comprado"));
                return dHistorial;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<DHistorial> getHis(String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * into dhistorial "
                    + "where id_cli=? and comprado=true");
            ps.setString(1, id_cli);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                ArrayList<DHistorial> dHistorialArray = new ArrayList<>();
                rs.beforeFirst();
                while (rs.next()) {
                    DHistorial dHistorial = new DHistorial();
                    dHistorial.setId_dhis(rs.getInt("id_dhis"));
                    dHistorial.setId_cli(rs.getString("id_cli"));
                    dHistorial.setTotalpag(rs.getDouble("totalpag"));
                    dHistorial.setTotalart(rs.getInt("totalart"));
                    dHistorial.setFecha(rs.getDate("fecha").toLocalDate());
                    dHistorial.setComprado(rs.getBoolean("comprado"));
                    dHistorialArray.add(dHistorial);
                }
                return dHistorialArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<DHistorial> getByDate(LocalDate fecha, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * into dhistorial "
                    + "where fecha=? and comprado=true");
            ps.setDate(1,Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                ArrayList<DHistorial> dHistorialArray = new ArrayList<>();
                rs.beforeFirst();
                while (rs.next()) {
                    DHistorial dHistorial = new DHistorial();
                    dHistorial.setId_dhis(rs.getInt("id_dhis"));
                    dHistorial.setId_cli(rs.getString("id_cli"));
                    dHistorial.setTotalpag(rs.getDouble("totalpag"));
                    dHistorial.setTotalart(rs.getInt("totalart"));
                    dHistorial.setFecha(rs.getDate("fecha").toLocalDate());
                    dHistorial.setComprado(rs.getBoolean("comprado"));
                    dHistorialArray.add(dHistorial);
                }
                return dHistorialArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public DHistorial modify(DHistorial dHistorial,Connection con)throws SQLException{
        try {
            PreparedStatement ps = con.prepareStatement("update dhistorial set "
                    + "id_dhis=?, id_cli=?, totalpag=?, totalart=?, fecha=?, comprado=? where id_cli=? and id_dhis=?");
            ps.setInt(1, dHistorial.getId_dhis());
            ps.setString(2, dHistorial.getId_cli());
            ps.setDouble(3, dHistorial.getTotalpag());
            ps.setInt(4, dHistorial.getTotalart());
            ps.setDate(5, Date.valueOf(dHistorial.getFecha()));
            ps.setBoolean(6, dHistorial.isComprado());
            ps.setString(7, dHistorial.getId_cli());
            ps.setInt(8, dHistorial.getId_dhis());
            ps.executeUpdate();
            ps.close();
            return dHistorial;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public DHistorial add(DHistorial dHistorial, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into dhistorial"
                    + "(id_dhis,id_cli,totalpag,totalart,fecha,comprado) "
                    + "values(?,?,?,?,?,?)");
            ps.setInt(1, dHistorial.getId_dhis());
            ps.setString(2, dHistorial.getId_cli());
            ps.setDouble(3, dHistorial.getTotalpag());
            ps.setInt(4, dHistorial.getTotalart());
            ps.setDate(5, Date.valueOf(dHistorial.getFecha()));
            ps.setBoolean(6, dHistorial.isComprado());
            ps.executeUpdate();
            ps.close();
            return dHistorial;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean confirm(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("update dhistorial set "
                    + "comprado=true where id_cli=? and id_dhis=?");
            ps.setString(1, id_cli);
            ps.setInt(2, id_dhis);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean cancell(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("delete from dhistorial "
                    + "where id_cli=? and id_dhis=? and comprado=false limit 1");
            ps.setString(1, id_cli);
            ps.setInt(2, id_dhis);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteAll(String id_cli, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("delete from dhistorial "
                    + "where id_cli=?");
            ps.setString(1, id_cli);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean instantSave(DHistorial dHistorial, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into dhistorial"
                    + "(id_dhis,id_cli,totalpag,totalart,fecha,comprado) "
                    + "values(?,?,?,?,?,?)");
            ps.setInt(1, dHistorial.getId_dhis());
            ps.setString(2, dHistorial.getId_cli());
            ps.setDouble(3, dHistorial.getTotalpag());
            ps.setInt(4, dHistorial.getTotalart());
            ps.setDate(5, Date.valueOf(dHistorial.getFecha()));
            ps.setBoolean(6, dHistorial.isComprado());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    
}
