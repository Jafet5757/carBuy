/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.controller;

import com.carBuy.utils.model.Cliente;
import com.carBuy.utils.service.impl.CCarritoCServiceImpl;
import com.carBuy.utils.service.impl.ClienteServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 *
 * @author kcram
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/ClienteController"})
public class ClienteController extends HttpServlet {
    
    private ClienteServiceImpl clienteServiceImpl;
    private CCarritoCServiceImpl cCarritoCServiceImpl;
    
    @Resource(name="jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init(); 
        try{
            this.clienteServiceImpl = new ClienteServiceImpl();
            this.cCarritoCServiceImpl = new CCarritoCServiceImpl();
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            String instruction = request.getParameter("command");
            switch (instruction){
                case "crearUsuario":
                    try{
                        Connection con = datasource.getConnection();
                        Cliente cliente = new Cliente();
                        cliente.setId_cli(request.getParameter("id_cli"));
                        cliente.setNom_cli(request.getParameter("nom_cli"));
                        cliente.setApp_cli(request.getParameter("app_cli"));
                        cliente.setApm_cli(request.getParameter("apm_cli"));
                        String fnac = request.getParameter("fnac_cli");
                        LocalDate localDate = LocalDate.parse(fnac);
                        cliente.setFnac_cli(localDate);
                        cliente.setDir_cli(request.getParameter("dir_cli"));
                        cliente.setTel_cli(request.getParameter("tel_cli"));
                        cliente.setCel_cli(request.getParameter("cel_cli"));
                        if((request.getParameter("pass_cli_org")).equals(request.getParameter("pass_cli_cop"))){
                            cliente.setPass_cli(request.getParameter("pass_cli_org"));
                            cliente = clienteServiceImpl.save(cliente, con);
                            request.getSession().setAttribute("usuario", cliente);
                            request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                                    + "Se ha registrado exitosamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "La contraseña no coincide");
                            request.getRequestDispatcher("reg_cli.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("reg_cli.jsp").forward(request, response);
                    }
                break;
                case "iniciarSesion":
                    try {
                        Connection con = datasource.getConnection();
                        Cliente cliente = new Cliente();
                        String id = (request.getParameter("id"));
                        String pass = (request.getParameter("pass"));
                        cliente = clienteServiceImpl.get(id, pass, con);
                        if(cliente != null){
                            request.getSession().setAttribute("usuario", cliente);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("login_cli.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("login_cli.jsp").forward(request, response);
                    }
                break;
                case "borraCuenta":
                {
                    try {
                        Connection con = datasource.getConnection();
                        Cliente cliente = (Cliente)request.getSession().getAttribute("usuario");
                        boolean exito2 = cCarritoCServiceImpl.delete(cliente.getId_cli(),con);
                        con = datasource.getConnection();
                        boolean exito = clienteServiceImpl.delete(cliente.getId_cli(),cliente.getPass_cli(), con);
                        if(exito && exito2){
                            request.getSession().setAttribute("usuario",null);
                            request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                                    + "Se ha borrado su cuenta exitosamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }else{
                            request.getSession().setAttribute("usuario",null);
                            request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                    + "Ocurrio un error. Intentelo nuevamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.getSession().setAttribute("usuario",null);
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                + "Ocurrio un error. Intentelo nuevamente\n"
                                + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
                }
                break;

                case "actualizarDatos":
                    try{
                        Connection con = datasource.getConnection();
                        Cliente cliente = new Cliente();
                        cliente.setId_cli(request.getParameter("id_cli"));
                        cliente.setNom_cli(request.getParameter("nom_cli"));
                        cliente.setApp_cli(request.getParameter("app_cli"));
                        cliente.setApm_cli(request.getParameter("apm_cli"));
                        String fnac = request.getParameter("fnac_cli");
                        LocalDate localDate = LocalDate.parse(fnac);
                        cliente.setFnac_cli(localDate);
                        cliente.setDir_cli(request.getParameter("dir_cli"));
                        cliente.setTel_cli(request.getParameter("tel_cli"));
                        cliente.setCel_cli(request.getParameter("cel_cli"));
                        if((request.getParameter("pass_cli_org")).equals(request.getParameter("pass_cli_cop"))){
                            cliente.setPass_cli(request.getParameter("pass_cli_org"));
                            Cliente cliente_act = (Cliente)request.getSession().getAttribute("usuario");
                            cliente = clienteServiceImpl.modify(cliente_act.getId_cli(), cliente_act.getPass_cli(),cliente,con);
                            request.getSession().setAttribute("usario", cliente);
                            request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                                    + "Se ha actualizado su cuenta exitosamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                    + "Ocurrio un error. Intentelo nuevamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                    + "Ocurrio un error. Intentelo nuevamente\n"
                                    + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
                break;
                case "cerrarSesion":
                    try{
                        request.getSession().setAttribute("usuario", null);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } catch (Exception ex) {
                        out.println(ex);
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                    + "Ocurrio un error. Intentelo nuevamente\n"
                                    + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
                break;

            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("error_page.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
