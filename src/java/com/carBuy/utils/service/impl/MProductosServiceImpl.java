/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.MProductosDaoImpl;
import com.carBuy.utils.model.MProductos;
import com.carBuy.utils.service.MProductosServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class MProductosServiceImpl implements MProductosServiceAPI {

    private MProductosDaoImpl mProductosDaoImpl = new MProductosDaoImpl() {
    };

    private Pattern noStndr = Pattern.compile("[^();'*-]+");

    @Override
    public MProductos get(int id_mprod, Connection con) throws SQLException {
        try {
            return mProductosDaoImpl.get(id_mprod, con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public ArrayList<MProductos> getAll(Connection con) throws SQLException {
        try {
            return mProductosDaoImpl.getAll(con);
        } catch (SQLException ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public MProductos save(MProductos mProductos, Connection con) throws SQLException {
        if (checkMProd(mProductos)) {
            try {
                return mProductosDaoImpl.save(mProductos, con);
            } catch (SQLException ex) {
                return null;
            } finally {
                con.close();
            }
        } else {
            return null;
        }
    }

    private boolean checkMProd(MProductos mProductos) {
        String nom_prod = mProductos.getNom_prod();
        String img_prod = mProductos.getImg_prod();
        String des_prod = mProductos.getDes_prod();
        if (nom_prod.length() < 46 && img_prod.length() < 101 && des_prod.length() < 201) {
            Matcher mat1 = noStndr.matcher(nom_prod);
            Matcher mat2 = noStndr.matcher(img_prod);
            Matcher mat3 = noStndr.matcher(des_prod);
            mat3.matches();
            if (mat1.matches() && mat2.matches() && mat3.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(int id_mprod, Connection con) throws SQLException {
        try{
            return mProductosDaoImpl.delete(id_mprod, con);
        }catch(SQLException ex){
            return false;
        }finally{
            con.close();
        }
    }

    @Override
    public MProductos modify(MProductos mProductos, Connection con) throws SQLException {
        if (checkMProd(mProductos)) {
            try {
                return mProductosDaoImpl.modify(mProductos, con);
            } catch (SQLException ex) {
                return null;
            } finally {
                con.close();
            }
        } else {
            return null;
        }
    }
}
