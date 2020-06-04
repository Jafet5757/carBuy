/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.DHistorialDaoImpl;
import com.carBuy.utils.model.DHistorial;
import com.carBuy.utils.service.DHistorialServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author kcram
 */
public class DHistorialServiceImpl implements DHistorialServiceAPI{

    private DHistorialDaoImpl dHistorialDaoImpl = new DHistorialDaoImpl();
    
    @Override
    public DHistorial getByDate(LocalDate fecha, Connection con) throws SQLException {
        try{
            return dHistorialDaoImpl.getByDate(fecha, con);
        }catch(Exception ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public DHistorial save(DHistorial dHistorial, Connection con) throws SQLException {
        try{
            return dHistorialDaoImpl.save(dHistorial, con);
        }catch(Exception ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean delete(int id_dhis, Connection con) throws SQLException {
        try{
            return dHistorialDaoImpl.delete(id_dhis, con);
        }catch(Exception ex){
            return false;
        }finally{
            con.close();
        }
    }
    
}
