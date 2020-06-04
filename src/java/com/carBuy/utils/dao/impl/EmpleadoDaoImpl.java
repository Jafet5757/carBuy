/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.EmpleadoDaoAPI;
import com.carBuy.utils.model.Empleado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class EmpleadoDaoImpl implements EmpleadoDaoAPI {

    @Override
    public Empleado save(Empleado empleado, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("insert into empleado"
                    + "(id_emp,nom_emp,app_emp,apm_emp,fnac_emp,dir_emp,tel_emp,cel_emp,pass_emp,id_cpe) "
                    + "values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, empleado.getId_emp());
            ps.setString(2, empleado.getNom_emp());
            ps.setString(3, empleado.getApp_emp());
            ps.setString(4, empleado.getApm_emp());
            ps.setDate(5, Date.valueOf(empleado.getFnac_emp()));
            ps.setString(6, empleado.getDir_emp());
            ps.setString(7, empleado.getTel_emp());
            ps.setString(8, empleado.getCel_emp());
            ps.setString(9, empleado.getPass_emp());
            ps.setInt(10, empleado.getId_cpe());
            ps.executeUpdate();
            ps.close();
            return empleado;
        } catch (SQLException ex) {
            System.err.println(ex);
            return null;
        }
    }

    @Override
    public boolean delete(String id, String pass, Connection con) throws SQLException {
        try {
            if (!checkExistence(id, pass, con)) {
                return false;
            } else {
                PreparedStatement ps = con.prepareStatement("delete from empleado "
                        + "where id_emp=? "
                        + "and pass_emp=? "
                        + "limit 1");
                ps.setString(1, id);
                ps.setString(2, pass);
                ps.executeUpdate();
                ps.close();
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Empleado modify(String id, String pass, Empleado empleado, Connection con) throws SQLException {
        try {
            if (!checkExistence(id, pass, con)) {
                return null;
            } else {
                PreparedStatement ps = con.prepareStatement("update empleado set "
                        + "id_emp=?, nom_emp=?, app_emp=?, apm_emp=?, fnac_emp=?, "
                        + "dir_emp=?, tel_emp=?, cel_emp=?, pass_emp=?, id_cpe=? "
                        + "where id_emp=? "
                        + "and pass_emp=?");
                ps.setString(1, empleado.getId_emp());
                ps.setString(2, empleado.getNom_emp());
                ps.setString(3, empleado.getApp_emp());
                ps.setString(4, empleado.getApm_emp());
                ps.setDate(5, Date.valueOf(empleado.getFnac_emp()));
                ps.setString(6, empleado.getDir_emp());
                ps.setString(7, empleado.getTel_emp());
                ps.setString(8, empleado.getCel_emp());
                ps.setString(9, empleado.getPass_emp());
                ps.setInt(10, empleado.getId_cpe());
                ps.setString(11, id);
                ps.setString(12, pass);
                ps.executeUpdate();
                ps.close();
                return empleado;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Empleado get(String id, String pass, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * from empleado "
                    + "where id_emp=? "
                    + "and pass_emp=?");
            ps.setString(1, id);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                Empleado empleado = new Empleado();
                empleado.setId_emp(rs.getString("id_emp"));
                empleado.setNom_emp(rs.getString("nom_emp"));
                empleado.setApp_emp(rs.getString("app_emp"));
                empleado.setApm_emp(rs.getString("apm_emp"));
                empleado.setFnac_emp((rs.getDate("fnac_emp")).toLocalDate());
                empleado.setDir_emp(rs.getString("dir_emp"));
                empleado.setTel_emp(rs.getString("tel_emp"));
                empleado.setCel_emp(rs.getString("cel_emp"));
                empleado.setPass_emp(rs.getString("pass_emp"));
                empleado.setId_cpe(rs.getInt("id_cpe"));
                ps.close();
                return empleado;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    private boolean checkExistence(String id, String pass, Connection con) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement("select * from empleado "
                    + "where id_emp=? "
                    + "and pass_emp=?");
            ps.setString(1, id);
            ps.setString(2, pass);
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
    public ArrayList<Empleado> getAll(Connection con) throws SQLException {
        try {
            ArrayList<Empleado> empleadoArray = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement("select * from empleado");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setId_emp(rs.getString("id_emp"));
                    empleado.setNom_emp(rs.getString("nom_emp"));
                    empleado.setApp_emp(rs.getString("app_emp"));
                    empleado.setApm_emp(rs.getString("apm_emp"));
                    empleado.setFnac_emp((rs.getDate("fnac_emp")).toLocalDate());
                    empleado.setDir_emp(rs.getString("dir_emp"));
                    empleado.setTel_emp(rs.getString("tel_emp"));
                    empleado.setCel_emp(rs.getString("cel_emp"));
                    empleado.setPass_emp(rs.getString("pass_emp"));
                    empleado.setId_cpe(rs.getInt("id_cpe"));
                    empleadoArray.add(empleado);
                }
                return empleadoArray;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

}
