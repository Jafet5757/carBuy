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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class DHistorialServiceImpl implements DHistorialServiceAPI {

    private DHistorialDaoImpl dHistorialDaoImpl = new DHistorialDaoImpl();

    private Pattern noStndr = Pattern.compile("[^();''*--]+");

    private boolean checkKeys(String id_cli) {
        Matcher mat1 = noStndr.matcher(id_cli);
        if (mat1.matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DHistorial get(String id_cli, Connection con) throws SQLException {
        try {
            if (!checkKeys(id_cli)) {
                return null;
            } else {
                return dHistorialDaoImpl.get(id_cli, con);
            }
        } catch (Exception ex) {
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public ArrayList<DHistorial> getHis(String id_cli, Connection con) throws SQLException {
        try {
            if (!checkKeys(id_cli)) {
                return null;
            } else {
                return dHistorialDaoImpl.getHis(id_cli, con);
            }
        } catch (Exception ex) {
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public ArrayList<DHistorial> getByDate(LocalDate fecha, Connection con) throws SQLException {
        try {
            return dHistorialDaoImpl.getByDate(fecha, con);
        } catch (Exception ex) {
            return null;
        }finally{
            con.close();
        }
    }
    
    @Override
    public DHistorial modify(DHistorial dHistorial,Connection con)throws SQLException{
        try {
            if (!checkKeys(dHistorial.getId_cli())) {
                return null;
            } else {
                return dHistorialDaoImpl.modify(dHistorial, con);
            }
        } catch (Exception ex) {
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public DHistorial add(DHistorial dHistorial, Connection con) throws SQLException {
        try {
            if (!checkKeys(dHistorial.getId_cli())) {
                return null;
            } else {
                return dHistorialDaoImpl.add(dHistorial, con);
            }
        } catch (Exception ex) {
            return null;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean confirm(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            if (!checkKeys(id_cli)) {
                return false;
            } else {
                return dHistorialDaoImpl.confirm(id_cli, id_dhis, con);
            }
        } catch (Exception ex) {
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean cancell(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            if (!checkKeys(id_cli)) {
                return false;
            } else {
                return dHistorialDaoImpl.cancell(id_cli, id_dhis, con);
            }
        } catch (Exception ex) {
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean deleteAll(String id_cli, Connection con) throws SQLException {
        try {
            if (!checkKeys(id_cli)) {
                return false;
            } else {
                return dHistorialDaoImpl.deleteAll(id_cli, con);
            }
        } catch (Exception ex) {
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public boolean instantSave(DHistorial dHistorial, Connection con) throws SQLException {
        try {
            if (!checkKeys(dHistorial.getId_cli())) {
                return false;
            } else {
                return dHistorialDaoImpl.instantSave(dHistorial, con);
            }
        } catch (Exception ex) {
            return false;
        }finally{
            con.close();
        }
    }

}
