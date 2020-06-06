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
import com.carBuy.utils.service.impl.DHistorialServiceImpl;
import com.carBuy.utils.service.impl.DProductosServiceImpl;
import com.carBuy.utils.service.impl.MCarritoCServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
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
@WebServlet(name = "DCarritoCController", urlPatterns = {"/DCarritoCController"})
public class DCarritoCController extends HttpServlet {

    private DProductosServiceImpl dProductosServiceImpl;
    private DHistorialServiceImpl dHistorialServiceImpl;
    private MCarritoCServiceImpl mCarritoCServiceImpl;

    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.dHistorialServiceImpl = new DHistorialServiceImpl();
            this.mCarritoCServiceImpl = new MCarritoCServiceImpl();
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
                Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
                if (cliente != null) {
                    Connection con = datasource.getConnection();
                    DHistorial dHistorial = dHistorialServiceImpl.get(cliente.getId_cli(), con);
                    con = datasource.getConnection();
                    DProductos dProductos = dProductosServiceImpl.get(Integer.parseInt(request.getParameter("id_dprod")), con);
                    if (dProductos != null) {
                        if (dProductos.getStock_prod() > 0) {
                            if (dHistorial != null) {
                                MCarrito_Compra mCarrito_Compra = new MCarrito_Compra();
                                mCarrito_Compra.setId_mcc(0);
                                mCarrito_Compra.setId_dprod(Integer.parseInt(request.getParameter("id_dprod")));
                                mCarrito_Compra.setId_dhis(dHistorial.getId_dhis());
                                con = datasource.getConnection();
                                mCarrito_Compra = mCarritoCServiceImpl.add(mCarrito_Compra, con);
                                if (mCarrito_Compra != null) {
                                    con = datasource.getConnection();
                                    dHistorial.setArticulos(dHistorial.getArticulos() + 1);
                                    dHistorial.setSub_total(dHistorial.getSub_total() + dProductos.getPrecio_prod());
                                    dHistorial.setIva(dHistorial.getSub_total() * 0.16);
                                    dHistorial.setTotal(dHistorial.getSub_total() + dHistorial.getIva());
                                    Date sqlDate = new Date(new java.util.Date().getTime());
                                    dHistorial.setFecha(sqlDate.toLocalDate());
                                    dHistorial.setComprado(false);
                                    dHistorial = dHistorialServiceImpl.modify(dHistorial, con);
                                    if (dHistorial != null) {
                                        response.sendRedirect("ccompras.jsp");
                                    } else {
                                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                                + "Ocurrio un error. Intentelo nuevamente\n"
                                                + "</div>");
                                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                            + "Ocurrio un error. Intentelo nuevamente\n"
                                            + "</div>");
                                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                }
                            } else {
                                dHistorial = new DHistorial();
                                dHistorial.setId_dhis(0);
                                dHistorial.setId_cli(cliente.getId_cli());
                                dHistorial.setArticulos(1);
                                dHistorial.setSub_total(dProductos.getPrecio_prod());
                                dHistorial.setIva(dProductos.getPrecio_prod() * 0.16);
                                dHistorial.setTotal(dProductos.getPrecio_prod() + dHistorial.getIva());
                                Date sqlDate = new Date(new java.util.Date().getTime());
                                dHistorial.setFecha(sqlDate.toLocalDate());
                                dHistorial.setComprado(false);
                                con = datasource.getConnection();
                                dHistorial = dHistorialServiceImpl.add(dHistorial, con);
                                if (dHistorial != null) {
                                    con = datasource.getConnection();
                                    dHistorial = dHistorialServiceImpl.get(cliente.getId_cli(), con);
                                    MCarrito_Compra mCarritoC = new MCarrito_Compra();
                                    mCarritoC.setId_mcc(0);
                                    mCarritoC.setId_dprod(Integer.parseInt(request.getParameter("id_dprod")));
                                    mCarritoC.setId_dhis(dHistorial.getId_dhis());
                                    con = datasource.getConnection();
                                    mCarritoC = mCarritoCServiceImpl.add(mCarritoC, con);
                                    if (mCarritoC != null) {
                                        response.sendRedirect("ccompras.jsp");
                                    } else {
                                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                                + "Ocurrio un error. Intentelo nuevamente\n"
                                                + "</div>");
                                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                            + "Ocurrio un error. Intentelo nuevamente\n"
                                            + "</div>");
                                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                }
                            }
                        } else {
                            request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                    + "Lo sentimos. El producto que busca no se encuentra disponible\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }

                    } else {
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                + "Ocurrio un error. Intentelo nuevamente\n"
                                + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                            + "Necesita estar logueado como cliente para acceder al carrito.\n"
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
            case "eliminarCarritoUni":
                try {
                Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
                if (cliente != null) {
                    Connection con = datasource.getConnection();
                    DHistorial dHistorial = dHistorialServiceImpl.get(cliente.getId_cli(), con);
                    con = datasource.getConnection();
                    DProductos dProductos = dProductosServiceImpl.get(Integer.parseInt(request.getParameter("id_dprod")), con);
                    if (dProductos != null) {
                        if (dProductos.getStock_prod() > 0) {
                            if (dHistorial != null) {
                                con = datasource.getConnection();
                                ArrayList<MCarrito_Compra> mCarritoCArray = mCarritoCServiceImpl.getAll(Integer.parseInt(request.getParameter("id_dhis")), con);
                                if (mCarritoCArray != null) {
                                    for (MCarrito_Compra mCc : mCarritoCArray) {
                                        if (mCc.getId_mcc() == Integer.parseInt(request.getParameter("id_mcc"))) {
                                            con = datasource.getConnection();
                                            boolean exito = mCarritoCServiceImpl.delete(Integer.parseInt(request.getParameter("id_mcc")), con);
                                            if (exito) {
                                                response.sendRedirect("ccompras.jsp");
                                            } else {
                                                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                                        + "Ocurrio un error. Intentelo nuevamente\n"
                                                        + "</div>");
                                                request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                            }
                                        }
                                    }
                                } else {
                                    request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                            + "Ocurrio un error. Intentelo nuevamente\n"
                                            + "</div>");
                                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                        + "Ocurrio un error. Intentelo nuevamente\n"
                                        + "</div>");
                                request.getRequestDispatcher("confirm.jsp").forward(request, response);
                            }

                        } else {
                            request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                    + "Lo sentimos. El producto que busca no se encuentra disponible\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }

                    } else {
                        con = datasource.getConnection();
                        boolean exito = mCarritoCServiceImpl.delete(Integer.parseInt(request.getParameter("id_mcc")), con);
                        if (exito) {
                            request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                    + "Lo sentimos. El producto que busca ya no se encuentra disponible\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        } else {
                            request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                    + "Lo sentimos. El producto que busca ya no se encuentra disponible\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }
                    }
                } else {
                    request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                            + "Necesita estar logueado como cliente para acceder al carrito.\n"
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
