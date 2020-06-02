/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CCarritoCDaoImpl;
import java.sql.Connection;
import java.sql.SQLException;
import com.carBuy.utils.service.CCarritoCServiceAPI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcram
 */
public class CCarritoCServiceImpl implements CCarritoCServiceAPI{
    
    private CCarritoCDaoImpl cCarritoCDaoImpl = new CCarritoCDaoImpl() {};
    
    private Pattern noStndr = Pattern.compile("[^();''*--]+");
    
    @Override
    public boolean delete(String id_cli, Connection con) throws SQLException {
        if(checkKeys(id_cli)){
            try{
                return cCarritoCDaoImpl.delete(id_cli, con);
            }catch(SQLException ex){
                return false;
            }finally{
                con.close();
            }
        }else{
            return false;
        }
    }
    
    private boolean checkKeys(String id){
        Matcher mat1 = noStndr.matcher(id);
        if(mat1.matches()){
            return true;
        }else{
            return false;
        }
    }
}
