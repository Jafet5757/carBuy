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
@WebServlet(name = "DCarritoCController", urlPatterns = {"/DCarritoCController"})
public class DCarritoCController extends HttpServlet {

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
            case "confirmar":
                try {
                Pattern noNumbs = Pattern.compile("[^\\p{Punct}\\p{Digit}]+");
                Pattern noLetts = Pattern.compile("[^\\p{Punct}\\p{Alpha}]+");
                String noTar = request.getParameter("noTar");
                Matcher mat1 = noLetts.matcher(noTar);
                String nomApp = request.getParameter("nomApp");
                Matcher mat2 = noNumbs.matcher(nomApp);
                LocalDate expDat = LocalDate.parse(request.getParameter("expDat"));
                LocalDate pastLim = LocalDate.parse("2045-01-01");
                LocalDate BefLim = LocalDate.parse("1995-01-01");
                String codSec = request.getParameter("codSec");
                Matcher mat3 = noLetts.matcher(codSec);
                if ((noTar.length() < 16 || noTar.length() > 19 || noTar == "" || nomApp == "")
                        || (!mat1.matches()) || (!mat2.matches())
                        || (expDat.isAfter(pastLim) || expDat.isBefore(BefLim))
                        || (codSec.length() > 3 || codSec == "") || (!mat3.matches())) {
                    request.setAttribute("id_dprod", request.getParameter("id_dprod"));
                    request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                    request.getRequestDispatcher("confirmTarjeta.jsp").forward(request, response);
                } else {
                    Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
                    if (cliente != null) {
                        Connection con = datasource.getConnection();
                        DHistorial dHistorial = dHistorialServiceImpl.get(cliente.getId_cli(), con);
                        if (dHistorial != null) {
                            con = datasource.getConnection();
                            ArrayList<MCarrito_Compra> mCarritoCArray = mCarritoCServiceImpl.getAll(dHistorial.getId_dhis(), con);
                            con = datasource.getConnection();
                            boolean exito = dHistorialServiceImpl.confirm(cliente.getId_cli(), dHistorial.getId_dhis(), con);
                            if (exito) {
                                String Productos = "";
                                for (MCarrito_Compra mCar : mCarritoCArray) {
                                    con = datasource.getConnection();
                                    DProductos dProd = dProductosServiceImpl.get(mCar.getId_dprod(), con);
                                    con = datasource.getConnection();
                                    MProductos mProd = mProductosServiceImpl.get(dProd.getId_mprod(), con);
                                    Productos = Productos + "<p>-- " + mProd.getNom_prod() + " : " + dProd.getPrecio_prod() + "$</p>\n";
                                }
                                request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                        + "  <h4 class=\"alert-heading\">Ticket</h4>\n"
                                        + "  <p>Gracias por comprar en <strong>carBuy</strong></p>\n"
                                        + "<p>No. compra: " + dHistorial.getId_dhis() + "</p>\n"
                                        + "<p>Fecha: " + dHistorial.getFecha() + "</p>\n"
                                        + "<p>Articulos comprados:</p>\n"
                                        + "" + Productos + ""
                                        + "  <hr>\n"
                                                + "<p class=\"mb-0\">IVA: " + dHistorial.getIva()+ "$</p>\n"
                                        + "  <p class=\"mb-0\">Total a pagar: " + dHistorial.getTotal() + "$</p>\n"
                                        + "</div>");
                                request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
                        request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                + "Ocurrio un error. Intentelo nuevamente\n"
                                + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
                }

            } catch (Exception ex) {
                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                        + "Ocurrio un error. Intentelo nuevamente\n"
                        + "</div>");
                request.getRequestDispatcher("confirm.jsp").forward(request, response);
            }
            break;
            case "compraDirecta":
                try {
                Pattern noNumbs = Pattern.compile("[^\\p{Punct}\\p{Digit}]+");
                Pattern noLetts = Pattern.compile("[^\\p{Punct}\\p{Alpha}]+");
                String noTar = request.getParameter("noTar");
                Matcher mat1 = noLetts.matcher(noTar);
                String nomApp = request.getParameter("nomApp");
                Matcher mat2 = noNumbs.matcher(nomApp);
                LocalDate expDat = LocalDate.parse(request.getParameter("expDat"));
                LocalDate pastLim = LocalDate.parse("2045-01-01");
                LocalDate BefLim = LocalDate.parse("1995-01-01");
                String codSec = request.getParameter("codSec");
                Matcher mat3 = noLetts.matcher(codSec);
                if ((noTar.length() < 16 || noTar.length() > 19 || noTar == "" || nomApp == "")
                        || (!mat1.matches()) || (!mat2.matches())
                        || (expDat.isAfter(pastLim) || expDat.isBefore(BefLim))
                        || (codSec.length() > 3 || codSec == "") || (!mat3.matches())) {
                    request.setAttribute("id_dprod", request.getParameter("id_dprod"));
                    request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                    request.getRequestDispatcher("directTarjeta.jsp").forward(request, response);
                } else {
                    Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
                    if (cliente != null) {
                        Connection con = datasource.getConnection();
                        DProductos dProductos = dProductosServiceImpl.get(Integer.parseInt(request.getParameter("id_dprod")), con);
                        if (dProductos != null) {
                            if (dProductos.getStock_prod() > 0) {
                                DHistorial dHistorial = new DHistorial();
                                dHistorial.setId_dhis(0);
                                dHistorial.setId_cli(cliente.getId_cli());
                                dHistorial.setArticulos(1);
                                dHistorial.setSub_total(dProductos.getPrecio_prod());
                                dHistorial.setIva(dProductos.getPrecio_prod() * 0.16);
                                dHistorial.setTotal(dProductos.getPrecio_prod() + dHistorial.getIva());
                                Date sqlDate = new Date(new java.util.Date().getTime());
                                dHistorial.setFecha(sqlDate.toLocalDate());
                                dHistorial.setComprado(true);
                                con = datasource.getConnection();
                                boolean exito = dHistorialServiceImpl.instantSave(dHistorial, con);
                                if (exito) {
                                    con = datasource.getConnection();
                                    ArrayList<DHistorial> dHistorialArray = dHistorialServiceImpl.getHis(cliente.getId_cli(), con);
                                    dHistorial = dHistorialArray.get(dHistorialArray.size() - 1);
                                    MCarrito_Compra mCarritoC = new MCarrito_Compra();
                                    mCarritoC.setId_mcc(0);
                                    mCarritoC.setId_dprod(Integer.parseInt(request.getParameter("id_dprod")));
                                    mCarritoC.setId_dhis(dHistorial.getId_dhis());
                                    con = datasource.getConnection();
                                    mCarritoC = mCarritoCServiceImpl.add(mCarritoC, con);
                                    if (mCarritoC != null) {
                                        con = datasource.getConnection();
                                        dProductos.setStock_prod(dProductos.getStock_prod() - 1);
                                        dProductos = dProductosServiceImpl.modify(dProductos, con);
                                        con = datasource.getConnection();
                                        MProductos mProducto = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                                        if (dProductos != null) {
                                            request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                                    + "  <h4 class=\"alert-heading\">Ticket</h4>\n"
                                                    + "  <p>Gracias por comprar en <strong>carBuy</strong></p>\n"
                                                    + "<p>No. compra: " + dHistorial.getId_dhis() + "</p>\n"
                                                    + "<p>Fecha: " + dHistorial.getFecha() + "</p>\n"
                                                    + "<p>Articulos comprados:</p>\n"
                                                    + "<p>-- " + mProducto.getNom_prod() + " : " + dProductos.getPrecio_prod() + "$</p>\n"
                                                    + "  <hr>\n"
                                                            + "<p class=\"mb-0\">IVA: " + dHistorial.getIva() + "$</p>\n"
                                                    + "  <p class=\"mb-0\">Total a pagar: " + dHistorial.getTotal() + "$</p>\n"
                                                    + "</div>");
                                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
                            request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                                    + "Ocurrio un error. Intentelo nuevamente\n"
                                    + "</div>");
                            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                + "Necesita estar logueado como cliente para hacer compras.\n"
                                + "</div>");
                        request.getRequestDispatcher("confirm.jsp").forward(request, response);
                    }
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
                                                con = datasource.getConnection();
                                                dHistorial.setArticulos(dHistorial.getArticulos() - 1);
                                                dHistorial.setSub_total(dHistorial.getSub_total() - dProductos.getPrecio_prod());
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
            case "eliminarCarritoMul":
                try {
                Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
                if (cliente != null) {
                    Connection con = datasource.getConnection();
                    DHistorial dHistorial = dHistorialServiceImpl.get(cliente.getId_cli(), con);
                    if (dHistorial != null) {
                        con = datasource.getConnection();
                        boolean exito = mCarritoCServiceImpl.deleteAll(Integer.parseInt(request.getParameter("id_dhis")), con);
                        if (exito) {
                            con = datasource.getConnection();
                            dHistorial.setArticulos(0);
                            dHistorial.setSub_total(0);
                            dHistorial.setIva(0);
                            dHistorial.setTotal(0);
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
