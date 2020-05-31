/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.controller;

import com.carBuy.utils.model.Empleado;
import com.carBuy.utils.service.impl.EmpleadoServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author kcram
 */
@WebServlet(name = "EmpleadoController", urlPatterns = {"/EmpleadoController"})
public class EmpleadoController extends HttpServlet {
    
    private EmpleadoServiceImpl empleadoServiceImpl;
    
    @Resource(name="jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init(); 
        try{
            this.empleadoServiceImpl = new EmpleadoServiceImpl();
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String instruction = request.getParameter("command");
            switch (instruction){
                case "crearUsuario":
                    try (Connection con = datasource.getConnection();){
                        Empleado empleado = new Empleado();
                        empleado.setId_emp(request.getParameter("id_emp"));
                        empleado.setNom_emp(request.getParameter("nom_emp"));
                        empleado.setApp_emp(request.getParameter("app_emp"));
                        empleado.setApm_emp(request.getParameter("apm_emp"));
                        String fnac = request.getParameter("fnac_emp");
                        LocalDate localDate = LocalDate.parse(fnac);
                        empleado.setFnac_emp(localDate);
                        empleado.setDir_emp(request.getParameter("dir_emp"));
                        empleado.setTel_emp(request.getParameter("tel_emp"));
                        empleado.setCel_emp(request.getParameter("cel_emp"));
                        empleado.setPass_emp(request.getParameter("pass_emp_org"));
                        empleado.setId_cpe(Integer.parseInt(request.getParameter("id_cpe")));
                        empleado = empleadoServiceImpl.save(empleado, con);
                        request.setAttribute("usuario", empleado);
                        request.getRequestDispatcher("loginRes.jsp").forward(request, response);
                    } catch (Exception ex) {
                        request.setAttribute("usuario", null);
                        request.getRequestDispatcher("loginRes.jsp").forward(request, response);
                    }
                break;
                case "iniciarSesion":
                    try (Connection con = datasource.getConnection();){
                        Empleado empleado = new Empleado();
                        String id = (request.getParameter("id"));
                        String pass = (request.getParameter("pass"));
                        empleado = empleadoServiceImpl.get(id, pass, con);
                        if(empleado != null){
                            request.getSession().setAttribute("usuario", empleado);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("loginRes_emp.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("loginRes_emp.jsp").forward(request, response);
                    }
                break;
                case "borraCuenta":
                    
                break;
                case "actualizarDatos":
                
                break;


            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
