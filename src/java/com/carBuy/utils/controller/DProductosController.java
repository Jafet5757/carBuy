/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carBuy.utils.controller;

import com.carBuy.utils.model.Cliente;
import com.carBuy.utils.model.DHistorial;
import com.carBuy.utils.model.DProductos;
import com.carBuy.utils.model.MCarrito_Compra;
import com.carBuy.utils.model.MProductos;
import com.carBuy.utils.service.impl.DHistorialServiceImpl;
import com.carBuy.utils.service.impl.DProductosServiceImpl;
import com.carBuy.utils.service.impl.MCarritoCServiceImpl;
import com.carBuy.utils.service.impl.MProductosServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@WebServlet(name = "DProductosController", urlPatterns = {"/DProductosController"})
public class DProductosController extends HttpServlet {

    private DProductosServiceImpl dProductosServiceImpl;
    private DHistorialServiceImpl dHistorialServiceImpl;
    private MCarritoCServiceImpl mCarritoCServiceImpl;
    private MProductosServiceImpl mProductosServiceImpl;

    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.dHistorialServiceImpl = new DHistorialServiceImpl();
            this.mCarritoCServiceImpl = new MCarritoCServiceImpl();
            this.dProductosServiceImpl = new DProductosServiceImpl();
            this.mProductosServiceImpl = new MProductosServiceImpl();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String instruction = request.getParameter("command");
        switch (instruction) {
            case "agregarProducto":
                
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
