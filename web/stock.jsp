<%-- 
    Document   : stock
    Created on : 7/06/2020, 05:23:31 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.service.impl.EmpleadoServiceImpl"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.carBuy.utils.model.CColorProd"%>
<%@page import="com.carBuy.utils.model.CatProductos"%>
<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.service.impl.CatProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.CColorProdServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    private EmpleadoServiceImpl empleadoServiceImpl = new EmpleadoServiceImpl();
    private DProductosServiceImpl dProductosServiceImpl = new DProductosServiceImpl();
    private MProductosServiceImpl mProductosServiceImpl = new MProductosServiceImpl();
    private CColorProdServiceImpl cColorProdServiceImpl = new CColorProdServiceImpl();
    private CatProductosServiceImpl catProductosServiceImpl = new CatProductosServiceImpl();
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <title>stock</title>
        <style type="text/css">
            a{
                color: #CECECE;
            }
        </style>
    </head>
    <body>

        <nav class="navbar navbar-expand-md navbar-danger bg-danger">
            <div class="container">
                <a href="index.jsp" class="navbar-brand">
                    carBuy
                </a>
                <button class="navbar-toggler" data-toggle="collapse" data-target="#secondNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="secondNavbar">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
                            <%
                                Cliente clienteSesion = null;
                                Empleado empleadoSesion = null;
                                try {
                                    clienteSesion = (Cliente) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                try {
                                    empleadoSesion = (Empleado) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                if (clienteSesion == null && empleadoSesion == null) {
                                    Cookie[] misCookies = request.getCookies();
                                    if (misCookies != null) {
                                        for (Cookie cookie : misCookies) {
                                            if (cookie.getName().equals("idEmpleado")) {
                                                Connection con = datasource.getConnection();
                                                empleadoSesion = empleadoServiceImpl.getCookie(cookie.getValue(), con);
                                                request.getSession().setAttribute("usuario", empleadoSesion);
                                            }
                                        }
                                    }
                                }
                                if ((clienteSesion == null && empleadoSesion == null) || clienteSesion != null) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                } else {
                                    if (clienteSesion != null || empleadoSesion != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                        <li class="nav-item"><a class="nav-link" href="hcompras.jsp">Historial</a></li>
                            <%
                                if (empleadoSesion != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                            <%
                                if (empleadoSesion.getId_cpe() == 1) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="list_emp.jsp">Empleados</a></li>
                            <%
                                }
                                if (empleadoSesion.getId_cpe() <= 2) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="stock.jsp">Inventario</a></li>
                            <%
                                    } else {
                                        request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                    }
                                }
                            } else {
                            %>
                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                            <%
                                    }
                                }
                            %>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <br>
            <div class="row">
                <div class="col m-2">
                    <a href="reg_prod.jsp" class="btn btn-primary">Agregar</a>
                </div>
            </div>
            <%
                try {
                    Connection con = datasource.getConnection();
                    ArrayList<DProductos> dProductosArray = dProductosServiceImpl.getAll(con);
                    if (dProductosArray == null) {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron registros
            </div>
            <%
            } else {
            %>
            <div>
                <h2>Productos</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Imagen</th>
                            <th>Nombre</th>
                            <th>Marca</th>
                            <th>Color</th>
                            <th>Descripcion</th>
                            <th>Precio Unitario</th>
                            <th>Unidades</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (DProductos dProductos : dProductosArray) {
                                try {
                                    con = datasource.getConnection();
                                    MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                                    con = datasource.getConnection();
                                    CColorProd cColorProd = cColorProdServiceImpl.get(dProductos.getId_ccp(), con);
                                    con = datasource.getConnection();
                                    CatProductos catProductos = catProductosServiceImpl.get(dProductos.getId_prod(), con);
                        %>
                        <tr>
                            <th><img src="<%=mProductos.getImg_prod()%>" class="img-fluid" alt="ERROR: revise los registros"></th>
                            <th><%= mProductos.getNom_prod()%></th>
                            <th><%= catProductos.getTipo_prod()%></th>
                            <th><%= cColorProd.getTipo_ccp()%></th>
                            <th><%= mProductos.getDes_prod()%></th>
                            <th><%= dProductos.getPrecio_prod()%></th>
                            <th><%= dProductos.getStock_prod()%></th>
                            <th>
                                <form action="act_prod.jsp" method="post">
                                    <input type="hidden" value="<%=dProductos.getId_dprod()%>" name="Id_dprod"/>
                                    <input type="submit" class="btn btn-info" value="Editar"/>
                                </form>
                            </th>
                            <th>
                                <form action="conf_Del_prod.jsp" method="post">
                                    <input type="hidden" value="<%=dProductos.getId_dprod()%>" name="Id_dprod"/>
                                    <input type="submit" class="btn btn-danger" value="Eliminar"/>
                                </form>
                            </th>
                        </tr>
                        <%
                                    } catch (SQLException ex) {
                                        request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                    }

                                }
                            }
                        } catch (Exception ex) {
                        %>
                    <div class="alert alert-info" role="alert">
                        No se encontraron registros
                    </div>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
