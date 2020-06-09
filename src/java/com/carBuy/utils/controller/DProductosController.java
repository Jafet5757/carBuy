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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

/**
 *
 * @author kcram
 */
@WebServlet(name = "DProductosController", urlPatterns = {"/DProductosController"})
@MultipartConfig
public class DProductosController extends HttpServlet {

    private DProductosServiceImpl dProductosServiceImpl;
    private DHistorialServiceImpl dHistorialServiceImpl;
    private MCarritoCServiceImpl mCarritoCServiceImpl;
    private MProductosServiceImpl mProductosServiceImpl;
    private String url = "http://localhost:8080/images/";

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
                try {
                String nom_prod = request.getParameter("nom_prod");
                int id_prod = Integer.parseInt(request.getParameter("id_prod"));
                int id_ccp = Integer.parseInt(request.getParameter("id_ccp"));
                String des_prod = request.getParameter("des_prod");
                double precio_prod = Double.parseDouble(request.getParameter("precio_prod"));
                int stock_prod = Integer.parseInt(request.getParameter("stock_prod"));
                Part filePart = request.getPart("img_prod");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                Pattern ext = Pattern.compile("^(^[A-Za-z0-9_]+([.](jpeg|png|jpg)){1}$){1}");
                Matcher mat = ext.matcher(fileName);
                if (mat.matches()) {
                    InputStream fileContent = filePart.getInputStream();
                    File uploads = new File("C:\\carBuy_images");
                    File file = new File(uploads, fileName);
                    if (!file.exists()) {
                        Files.copy(fileContent, file.toPath());
                    }
                    MProductos mProductos = new MProductos();
                    mProductos.setId_mprod(0);
                    mProductos.setNom_prod(nom_prod);
                    mProductos.setDes_prod(des_prod);
                    mProductos.setImg_prod(url + fileName);
                    Connection con = datasource.getConnection();
                    mProductos = mProductosServiceImpl.save(mProductos, con);
                    if (mProductos != null) {
                        DProductos dProductos = new DProductos();
                        dProductos.setId_dprod(0);
                        dProductos.setId_ccp(id_ccp);
                        dProductos.setId_mprod(mProductos.getId_mprod());
                        dProductos.setId_prod(id_prod);
                        dProductos.setPrecio_prod(precio_prod);
                        dProductos.setStock_prod(stock_prod);
                        con = datasource.getConnection();
                        dProductos = dProductosServiceImpl.add(dProductos, con);
                        if (dProductos != null) {
                            response.sendRedirect("stock.jsp");
                        } else {
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("msg", "El formato del archivo no esta soportado");
                    request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
            }
            break;
            case "borrarProd":
                try {
                int id_dprod = Integer.parseInt(request.getParameter("id_dprod"));
                Connection con = datasource.getConnection();
                DProductos dProductos = dProductosServiceImpl.get(id_dprod, con);
                con = datasource.getConnection();
                MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                if (dProductos != null) {
                    con = datasource.getConnection();
                    boolean exito = mCarritoCServiceImpl.deleteByProd(id_dprod, con);
                    if (exito) {
                        con = datasource.getConnection();
                        boolean exito2 = dProductosServiceImpl.delete(dProductos.getId_dprod(), con);
                        if (exito2) {
                            con = datasource.getConnection();
                            boolean exito3 = mProductosServiceImpl.delete(dProductos.getId_mprod(), con);
                            if (exito3) {
                                String img_org = mProductos.getImg_prod();
                                String img_name = img_org.substring(29);
                                File uploads = new File("C:\\carBuy_images");
                                File file_org = new File(uploads, img_name);
                                Files.deleteIfExists(file_org.toPath());
                                request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                                        + "Se ha borrado el producto exitosamente\n"
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
            case "actualizarProducto":
                try {
                int id_dprod = Integer.parseInt(request.getParameter("id_dprod"));
                Connection con = datasource.getConnection();
                DProductos dProductos = dProductosServiceImpl.get(id_dprod, con);
                con = datasource.getConnection();
                MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                mProductos.setNom_prod(request.getParameter("nom_prod"));
                dProductos.setId_prod(Integer.parseInt(request.getParameter("id_prod")));
                dProductos.setId_ccp(Integer.parseInt(request.getParameter("id_ccp")));
                mProductos.setDes_prod(request.getParameter("des_prod"));
                dProductos.setPrecio_prod(Double.parseDouble(request.getParameter("precio_prod")));
                dProductos.setStock_prod(Integer.parseInt(request.getParameter("stock_prod")));
                Part filePart = null;
                try {
                    filePart = request.getPart("img_prod");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (fileName.equals("")) {
                        filePart = null;
                    }
                } catch (Exception ex) {
                }
                if (filePart == null) {
                    con = datasource.getConnection();
                    mProductos = mProductosServiceImpl.modify(mProductos, con);
                    if (mProductos != null) {
                        con = datasource.getConnection();
                        dProductos = dProductosServiceImpl.modify(dProductos, con);
                        if (dProductos != null) {
                            response.sendRedirect("stock.jsp");
                        } else {
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                        request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                    }
                } else {
                    String img_org = mProductos.getImg_prod();
                    String img_name = img_org.substring(29);
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                    Pattern ext = Pattern.compile("^(^[A-Za-z0-9_]+([.](jpeg|png|jpg)){1}$){1}");
                    Matcher mat = ext.matcher(fileName);
                    if (mat.matches()) {
                        InputStream fileContent = filePart.getInputStream();
                        File uploads = new File("C:\\carBuy_images");
                        File file_org = new File(uploads, img_name);
                        File file = new File(uploads, fileName);
                        Files.deleteIfExists(file_org.toPath());
                        if (!file.exists()) {
                            Files.copy(fileContent, file.toPath());
                        }
                        mProductos.setImg_prod(url + fileName);
                        con = datasource.getConnection();
                        mProductos = mProductosServiceImpl.modify(mProductos, con);
                        if (mProductos != null) {
                            con = datasource.getConnection();
                            dProductos = dProductosServiceImpl.modify(dProductos, con);
                            if (dProductos != null) {
                                response.sendRedirect("stock.jsp");
                            } else {
                                request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                                request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                            request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("msg", "El formato del archivo no esta soportado");
                        request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
                    }
                }
            } catch (Exception ex) {
                request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                request.getRequestDispatcher("reg_prod.jsp").forward(request, response);
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
