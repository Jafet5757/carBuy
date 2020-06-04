/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.service.impl;

import com.carBuy.utils.dao.impl.CPrivilegioEDaoImpl;
import com.carBuy.utils.model.CPrivilegio_Empleado;
import com.carBuy.utils.service.CPrivilegioEServiceAPI;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CPrivilegioEServiceImpl implements CPrivilegioEServiceAPI{
    
    private CPrivilegioEDaoImpl cPrivilegioEDaoImpl = new CPrivilegioEDaoImpl(){};

    @Override
    public CPrivilegio_Empleado get(int id_cpe, Connection con) throws SQLException {
        try{
            return cPrivilegioEDaoImpl.get(id_cpe,con);
        }catch(SQLException ex){
            return null;
        }finally{
            con.close();
        }
    }
    
}
