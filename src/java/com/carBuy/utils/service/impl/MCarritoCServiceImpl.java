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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class MCarritoCServiceImpl implements MCarritoCServiceAPI {

    private MCarritoCDaoImpl mCarritoCDaoImpl = new MCarritoCDaoImpl() {
    };

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
    public boolean add(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try {
            if (checkKeys(mCarrito_Compra.getId_cli())) {
                return mCarritoCDaoImpl.add(mCarrito_Compra, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean quit(int id_mcc, String id_cli, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.quit(id_mcc, id_cli, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean quitAll(String id_cli, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.quitAll(id_cli, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean deleteByCli(String id_cli, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.deleteByCli(id_cli, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean buy(MCarrito_Compra mCarrito_Compra, Connection con) throws SQLException {
        try {
            if (checkKeys(mCarrito_Compra.getId_cli())) {
                return mCarritoCDaoImpl.buy(mCarrito_Compra, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public boolean buyAll(String id_cli, int id_dhis, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.buyAll(id_cli, id_dhis, con);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            con.close();
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAllUnreg(String id_cli, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.getAllUnreg(id_cli, con);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAllByCli(String id_cli, Connection con) throws SQLException {
        try {
            if (checkKeys(id_cli)) {
                return mCarritoCDaoImpl.getAllByCli(id_cli, con);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        } finally {
            con.close();
        }
    }

    @Override
    public ArrayList<MCarrito_Compra> getAllByHis(int id_dhis, Connection con) throws SQLException {
        try {
            return mCarritoCDaoImpl.getAllByHis(id_dhis, con);
        } catch (Exception ex) {
            return null;
        } finally {
            con.close();
        }
    }

}
