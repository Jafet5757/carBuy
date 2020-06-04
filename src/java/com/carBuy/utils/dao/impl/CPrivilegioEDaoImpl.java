/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.dao.impl;

import com.carBuy.utils.dao.CPrivilegioEDaoApi;
import com.carBuy.utils.model.CPrivilegio_Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kcram
 */
public class CPrivilegioEDaoImpl implements CPrivilegioEDaoApi{

    @Override
    public CPrivilegio_Empleado get(int id_cpe, Connection con) throws SQLException{
        try{
            CPrivilegio_Empleado cPrivilegio_Empleado = new CPrivilegio_Empleado();
            PreparedStatement ps = con.prepareStatement("select * from cprivilegio_empleado "
                    + "where id_cpe=?");
            ps.setInt(1, id_cpe);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }else{
                cPrivilegio_Empleado.setId_cpe(rs.getInt("id_cpe"));
                cPrivilegio_Empleado.setTipo_pe(rs.getString("tipo_pe"));
                return cPrivilegio_Empleado;
            }
        }catch(SQLException ex){
            return null;
        }
    }
    
}
