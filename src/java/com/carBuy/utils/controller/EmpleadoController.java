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
import javax.servlet.http.Cookie;
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

    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.empleadoServiceImpl = new EmpleadoServiceImpl();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String instruction = request.getParameter("command");
        switch (instruction) {
            case "crearUsuario":
                    try (Connection con = datasource.getConnection();) {
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
                empleado.setPass_emp(request.getParameter("pass_emp"));
                switch (request.getParameter("id_cpe")) {
                    case "Admin":
                        empleado.setId_cpe(1);
                        break;
                    case "Stocker":
                        empleado.setId_cpe(2);
                        break;
                    case "Consultor":
                        empleado.setId_cpe(3);
                        break;
                }
                empleado = empleadoServiceImpl.save(empleado, con);
                if (empleado != null) {
                    request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                            + "Se ha registrado al empleado exitosamente\n"
                            + "</div>");
                    Cookie miCookie = new Cookie("idEmpleado", empleado.getId_emp());
                    miCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(miCookie);
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                    request.getRequestDispatcher("reg_emp.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                request.getRequestDispatcher("reg_emp.jsp").forward(request, response);
            }
            break;
            case "iniciarSesion":
                    try (Connection con = datasource.getConnection();) {
                Empleado empleado = new Empleado();
                String id = (request.getParameter("id"));
                String pass = (request.getParameter("pass"));
                empleado = empleadoServiceImpl.get(id, pass, con);
                if (empleado != null) {
                    request.getSession().setAttribute("usuario", empleado);
                    Cookie miCookie = new Cookie("idEmpleado", empleado.getId_emp());
                    miCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(miCookie);
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                    request.getRequestDispatcher("login_emp.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                request.setAttribute("msg", "Ocurrio un error. Intentelo nuevamente");
                request.getRequestDispatcher("login_emp.jsp").forward(request, response);
            }
            break;
            case "borraCuenta":
                try {
                Connection con = datasource.getConnection();
                Empleado empleado = (Empleado) request.getSession().getAttribute("usuario");
                con = datasource.getConnection();
                boolean exito = empleadoServiceImpl.delete(empleado.getId_emp(), empleado.getPass_emp(), con);
                if (exito) {
                    request.getSession().setAttribute("usuario", null);
                    request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                            + "Se ha borrado su cuenta exitosamente\n"
                            + "</div>");
                    Cookie[] misCookies = request.getCookies();
                    for (Cookie cookie : misCookies) {
                        if (cookie.getName().equals("idEmpleado")) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("usuario", null);
                    request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                            + "Ocurrio un error. Intentelo nuevamente\n"
                            + "</div>");
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                request.getSession().setAttribute("usuario", null);
                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                        + "Ocurrio un error. Intentelo nuevamente\n"
                        + "</div>");
                request.getRequestDispatcher("confirm.jsp").forward(request, response);
            }
            break;
            case "borraCuentaExt":
                try {
                Connection con = datasource.getConnection();
                String id = (request.getParameter("id_emp"));
                String pass = (request.getParameter("pass_emp"));
                con = datasource.getConnection();
                boolean exito = empleadoServiceImpl.delete(id, pass, con);
                if (exito) {
                    request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                            + "Se ha borrado el registro\n"
                            + "</div>");
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
            case "actualizarDatosLowLevel":
                    try {
                Connection con = datasource.getConnection();
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
                empleado.setPass_emp(request.getParameter("pass_emp"));
                Empleado empleado_act = (Empleado) request.getSession().getAttribute("usuario");
                empleado.setId_cpe(empleado_act.getId_cpe());
                empleado = empleadoServiceImpl.modify(empleado_act.getId_emp(), empleado_act.getPass_emp(), empleado, con);
                if (empleado_act != null) {
                    request.getSession().setAttribute("usuario", empleado);
                    request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                            + "Se ha actualizado su cuenta exitosamente\n"
                            + "</div>");
                    Cookie[] misCookies = request.getCookies();
                    for (Cookie cookie : misCookies) {
                        if (cookie.getName().equals("idEmpleado")) {
                            cookie.setValue(empleado_act.getId_emp());
                            response.addCookie(cookie);
                        }
                    }
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
            case "actualizarDatosHighLevel":
                    try {
                Connection con = datasource.getConnection();
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
                empleado.setPass_emp(request.getParameter("pass_emp"));
                switch (request.getParameter("id_cpe")) {
                    case "Admin":
                        empleado.setId_cpe(1);
                        break;
                    case "Stocker":
                        empleado.setId_cpe(2);
                        break;
                    case "Consultor":
                        empleado.setId_cpe(3);
                        break;
                }
                empleado = empleadoServiceImpl.modify(request.getParameter("id_emp_org"), request.getParameter("pass_emp_org"), empleado, con);
                if (empleado != null) {
                    request.setAttribute("msg", "<div class=\"alert alert-success\" role=\"alert\">\n"
                            + "Se ha actualizado al empleado\n"
                            + "</div>");
                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
            case "cerrarSesion":
                    try {
                request.getSession().setAttribute("usuario", null);
                Cookie[] misCookies = request.getCookies();
                for (Cookie cookie : misCookies) {
                    if (cookie.getName().equals("idEmpleado")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
                response.sendRedirect("index.jsp");
            } catch (Exception ex) {
                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                        + "Ocurrio un error. Intentelo nuevamente\n"
                        + "</div>");
                request.getRequestDispatcher("confirm.jsp").forward(request, response);
            }
            break;
            default:
                request.setAttribute("msg", "<div class=\"alert alert-warning\" role=\"alert\">\n"
                        + "Ocurrio un error. Intentelo nuevamente\n"
                        + "</div>");
                request.getRequestDispatcher("confirm.jsp").forward(request, response);
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
