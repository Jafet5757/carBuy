<%-- 
    Document   : reg_prod
    Created on : 7/06/2020, 05:55:42 PM
    Author     : kcram
--%>

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
                    <input type="hidden" value="agregarProducto" name="command" />
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
                    %>
                    <div class="form-row">
                        <div class="col mb-3">
                            <label for="validationDefault01">Nombre</label>
                            <input
                                type="text"
                                class="form-control"
                                id="validationDefault01"
                                name="nom_prod"
                                required
                                />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault01">Marca</label>
                            <select class="form-control" name="id_prod"/>
                            <%
                                try {
                                    Connection con = datasource.getConnection();
                                    ArrayList<CatProductos> catProductosArray = catProductosServiceImpl.getAll(con);
                                    for (CatProductos catProductos : catProductosArray) {
                            %>
                            <option value="<%= catProductos.getId_prod()%>"><%= catProductos.getTipo_prod()%></option>
                            <%
                                    }
                                } catch (SQLException ex) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                }
                            %>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault02">Color</label>
                            <select class="form-control" name="id_ccp"/>
                            <%
                                try {
                                    Connection con = datasource.getConnection();
                                    ArrayList<CColorProd> cColorProdArray = cColorProdServiceImpl.getAll(con);
                                    for (CColorProd cColorProd : cColorProdArray) {
                            %>
                            <option value="<%= cColorProd.getId_ccp()%>"><%= cColorProd.getTipo_ccp()%></option>
                            <%
                                    }
                                } catch (SQLException ex) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
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
                                >maximo 200 caracteres</textarea>
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
                                required
                                />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col mb-3">
                            <label for="validationDefault01">Imagen</label>
                            <input
                                type="file"
                                class="form-control"
                                id="validationDefault01"
                                name="img_prod"
                                required
                                />
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Enviar</button>
                    <a href="stock.jsp"><button class="btn btn-info" type="submit">Regresar</button></a>
                </form>
            </div>
        </div>
    </body>
</html>
