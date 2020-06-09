<%-- 
    Document   : reg_prod
    Created on : 7/06/2020, 05:55:42 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="com.carBuy.utils.service.impl.ClienteServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.EmpleadoServiceImpl"%>
<%@page import="com.carBuy.utils.model.CColorProd"%>
<%@page import="com.carBuy.utils.model.CatProductos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.carBuy.utils.service.impl.CatProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.CColorProdServiceImpl"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    String msg = null;
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
    private MProductosServiceImpl mProductosServiceImpl = new MProductosServiceImpl();
    private DProductosServiceImpl dProductosServiceImpl = new DProductosServiceImpl();
    private EmpleadoServiceImpl empleadoServiceImpl = new EmpleadoServiceImpl();
    private CColorProdServiceImpl cColorProdServiceImpl = new CColorProdServiceImpl();
    private CatProductosServiceImpl catProductosServiceImpl = new CatProductosServiceImpl();
%>
<html>
    <head>
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
            integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
            crossorigin="anonymous"
            />
        <title>Registro</title>
        <style type="text/css">
            .nav-link{
                color: #CECECE;
            }
            .navbar-brand{
                color: #CECECE;
            }
            #formula {
                border-radius: 4px;
                background-color: white;
                padding: 7px;
                position: relative;
                left: 15%;
                position: relative;
                left: 50%;
            }
            body {
                background: url("https://www.honda.mx/assets/img/autos/acura/modelos/nsx2020/galerias/galeria_1/1.jpg");
                background-repeat: no-repeat;
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
                                Cliente cliente = null;
                                Empleado empleado = null;
                                try {
                                    cliente = (Cliente) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                try {
                                    empleado = (Empleado) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                if (cliente == null && empleado == null) {
                                    Cookie[] misCookies = request.getCookies();
                                    if (misCookies != null) {
                                        for (Cookie cookie : misCookies) {
                                            if (cookie.getName().equals("idEmpleado")) {
                                                Connection con = datasource.getConnection();
                                                empleado = empleadoServiceImpl.getCookie(cookie.getValue(), con);
                                                request.getSession().setAttribute("usuario", empleado);
                                            }
                                        }
                                    }
                                }
                                if ((cliente == null && empleado == null) || cliente != null) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                } else {
                                    if (cliente != null || empleado != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                        <li class="nav-item"><a class="nav-link" href="hcompras.jsp">Historial</a></li>
                            <%
                                if (empleado != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="stock.jsp">Inventario</a></li>
                            <%
                                if (empleado.getId_cpe() == 1) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="list_emp.jsp">Empleados</a></li>
                            <%
                                }
                                if (empleado.getId_cpe() == 2) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="stock.jsp">Inventario</a></li>
                            <%
                                    }
                                }
                            } else {
                            %>
                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                            <%
                                    }
                                }
                            %>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container mx-auto m-5">
            <div class="row mx-auto">
                <form action="DProductosController" enctype="MULTIPART/FORM-DATA" id="formula" method="post">
                    <input type="hidden" value="actualizarProducto" name="command" />
                    <%
                        try {
                            msg = (String) request.getAttribute("msg");
                        } catch (Exception ex) {
                        }
                        if (msg != null) {
                    %>
                    <div class="alert alert-danger" role="alert">
                        <%=msg%>
                    </div>
                    <%
                        }
                        try {
                            int id_dprod = Integer.parseInt(request.getParameter("Id_dprod"));
                            Connection con = datasource.getConnection();
                            DProductos dProductos = dProductosServiceImpl.get(id_dprod, con);
                            con = datasource.getConnection();
                            MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                    %>
                    <input type="hidden" value="<%= id_dprod %>" name="id_dprod" />
                    <div class="form-row">
                        <div class="col mb-3">
                            <label for="validationDefault01">Nombre</label>
                            <input
                                type="text"
                                class="form-control"
                                id="validationDefault01"
                                name="nom_prod"
                                value="<%= mProductos.getNom_prod() %>"
                                required
                                />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault01">Marca</label>
                            <select class="form-control" name="id_prod"/>
                            <%
                                con = datasource.getConnection();
                                ArrayList<CatProductos> catProductosArray = catProductosServiceImpl.getAll(con);
                                for (CatProductos catProductos : catProductosArray) {
                            %>
                            <option value="<%= catProductos.getId_prod()%>"><%= catProductos.getTipo_prod()%></option>
                            <%
                                }
                            %>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault02">Color</label>
                            <select class="form-control" name="id_ccp"/>
                            <%
                                con = datasource.getConnection();
                                ArrayList<CColorProd> cColorProdArray = cColorProdServiceImpl.getAll(con);
                                for (CColorProd cColorProd : cColorProdArray) {
                            %>
                            <option value="<%= cColorProd.getId_ccp()%>"><%= cColorProd.getTipo_ccp()%></option>
                            <%
                                }
                            %>
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col mb-3">
                            <label for="validationDefault03">Descripcion</label>
                            <textarea
                                maxlength="200"
                                class="form-control"
                                id="validationDefault03"
                                name="des_prod"
                                required
                                ><%= mProductos.getDes_prod() %></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault03">Precio Unitario</label>
                            <input
                                type="text"
                                class="form-control"
                                id="validationDefault03"
                                name="precio_prod"
                                value="<%= dProductos.getPrecio_prod() %>"
                                required
                                />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault01">Unidades</label>
                            <input
                                type="text"
                                class="form-control"
                                id="validationDefault01"
                                name="stock_prod"
                                value="<%= dProductos.getStock_prod() %>"
                                required
                                />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col mb-3">
                            <label for="validationDefault01">Imagen (si no se ingresa una se ocupara la actual)</label>
                            <input
                                type="file"
                                class="form-control"
                                id="validationDefault01"
                                name="img_prod"
                                />
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Enviar</button>
                    <a href="stock.jsp"><button class="btn btn-info" type="submit">Regresar</button></a>
                    <%
                        } catch (Exception ex) {
                            request.getRequestDispatcher("error_page.jsp").forward(request, response);
                        }
                    %>
                </form>
            </div>
        </div>
    </body>
</html>
