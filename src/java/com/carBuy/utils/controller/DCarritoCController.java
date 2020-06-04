/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.controller;

import com.carBuy.utils.model.Cliente;
import com.carBuy.utils.model.DProductos;
import com.carBuy.utils.model.Empleado;
import com.carBuy.utils.service.impl.DProductosServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author kcram
 */
@WebServlet(name = "DCarritoCController", urlPatterns = {"/DCarritoCController"})
public class DCarritoCController extends HttpServlet {

    private DProductosServiceImpl dProductosServiceImpl;

    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.dProductosServiceImpl = new DProductosServiceImpl();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String instruction = request.getParameter("command");
        switch (instruction) {
            case "agregarCarrito":
                try {
                Cliente cliente = null;
                Empleado empleado = null;
                try {
                    cliente = (Cliente) request.getSession().getAttribute("usuario");
                } catch (Exception ex) {
                }
                try {
                    empleado = (Empleado) request.getSession().getAttribute("usuario");
                } catch (Exception ex) {
                }
                if (cliente == null || empleado == null) {
                    response.sendRedirect("login_cli.jsp");
                } else {
                    int id_dprod = Integer.parseInt((request.getParameter("id_dprod")));
                    Connection con = datasource.getConnection();
                    DProductos dProductos = dProductosServiceImpl.get(id_dprod, con);
                    if (dProductos == null) {
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                + "Ocurrio un error. Intentelo nuevamente\n"
                                + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }else{
                        
                        Cookie [] cookies = request.getCookies();
                    }
                }
            } catch (Exception ex) {
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
