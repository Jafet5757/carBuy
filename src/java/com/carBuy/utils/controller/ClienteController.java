/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.controller;

import com.carBuy.utils.dao.impl.CCarritoCDaoImpl;
import com.carBuy.utils.model.Cliente;
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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kcram
 */
@WebServlet(name = "ClienteController", urlPatterns = {"/ClienteController"})
public class ClienteController extends HttpServlet {
    
    private ClienteServiceImpl clienteServiceImpl;
    
    @Resource(name="jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init(); 
        try{
            this.clienteServiceImpl = new ClienteServiceImpl();
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
                            request.setAttribute("usuario", cliente);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("regRes_cli.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("regRes_cli.jsp").forward(request, response);
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
                            request.getRequestDispatcher("loginRes_cli.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("loginRes_cli.jsp").forward(request, response);
                    }
                break;
                case "borraCuenta":
                {
                    try {
                        Connection con = datasource.getConnection();
                        CCarritoCDaoImpl ccc=new CCarritoCDaoImpl();
                        String id = request.getParameter("id_cli");
                        String password = request.getParameter("pass_cli_org");
                        boolean exito = clienteServiceImpl.delete(id, password, con);
                        boolean exito2 = ccc.delete(id,con);
                        if(exito && exito2){
                            request.setAttribute("msg", "Informacion borrada con exito");
                            request.getRequestDispatcher("account.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "Ha ocurrido un error desconocido");
                            request.getRequestDispatcher("account.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        out.println(ex);
                        request.setAttribute("msg", "Ha ocurrido un error:"+ex);
                        request.getRequestDispatcher("account.jsp").forward(request, response);
                        Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
                            cliente = clienteServiceImpl.modify(cliente.getId_cli(), cliente.getPass_cli(),cliente,con);
                            request.setAttribute("usuario", cliente);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }else{
                            request.setAttribute("msg", "La contraseña no coincide");
                            request.getRequestDispatcher("account.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        out.println(ex);
                        request.setAttribute("msg", "Ocurrio un error:"+ex);
                        request.getRequestDispatcher("account.jsp").forward(request, response);
                    }
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
