/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.MCarritoCDaoImpl;
import com.carBuy.utils.model.MCarrito_Compra;
import com.carBuy.utils.service.MCarritoCServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kcram
 */
public class MCarritoCServiceImpl implements MCarritoCServiceAPI {

    private MCarritoCDaoImpl mCarritoCDaoImpl = new MCarritoCDaoImpl() {
    };

    @Override
    public MCarrito_Compra add(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try{
            return mCarritoCDaoImpl.add(mCarrito_Compra, con);
        }catch(Exception ex){
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean delete(int id_mcc, Connection con) throws SQLException {
        try{
            return mCarritoCDaoImpl.delete(id_mcc, con);
        }catch(Exception ex){
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean deleteAll(int id_dhis, Connection con) throws SQLException {
        try{
            return mCarritoCDaoImpl.deleteAll(id_dhis, con);
        }catch(Exception ex){
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAll(int id_dhis, Connection con) throws SQLException {
        try{
            return mCarritoCDaoImpl.getAll(id_dhis, con);
        }catch(Exception ex){
            return null;
        }finally{
            con.close();
        }
    }

}
