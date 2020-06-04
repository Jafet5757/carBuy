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

/**
 *
 * @author kcram
 */
public class DHistorialDaoImpl implements DHistorialDaoApi{

    @Override
    public DHistorial getByDate(LocalDate fecha, Connection con) throws SQLException {
        try{
            PreparedStatement ps = con.prepareStatement("select * into dhistorial "
                    + "where fecha=?");
            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                DHistorial dHistorial = new DHistorial();
                dHistorial.setId_dhis(rs.getInt("id_dhis"));
                dHistorial.setTotalpag(rs.getDouble("totalpag"));
                dHistorial.setTotalart(rs.getInt("totalart"));
                dHistorial.setFecha(rs.getDate("fecha").toLocalDate());
                return dHistorial;
            }
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public DHistorial save(DHistorial dHistorial, Connection con) throws SQLException {
        try{
            PreparedStatement ps = con.prepareStatement("insert into dhistorial"
                    + "(id_dhis,totalpag,totalart,fecha) "
                    + "value(?,?,?,?)");
            ps.setInt(1, dHistorial.getId_dhis());
            ps.setDouble(2, dHistorial.getTotalpag());
            ps.setInt(3, dHistorial.getTotalart());
            ps.setDate(4, Date.valueOf(dHistorial.getFecha()));
            ps.executeUpdate();
            ps.close();
            return dHistorial;
        }catch(SQLException ex){
            return null;
        }
    }

    @Override
    public boolean delete(int id_dhis, Connection con) throws SQLException {
        try{
            PreparedStatement ps = con.prepareStatement("delete from dhistorial "
                    + "where id_dhis=?");
            ps.setInt(1, id_dhis);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            return false;
        }
    }
    
}
