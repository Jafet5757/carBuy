<%-- 
    Document   : index
    Created on : 31/05/2020, 10:09:21 AM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.service.impl.ClienteServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.EmpleadoServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
    private EmpleadoServiceImpl empleadoServiceImpl = new EmpleadoServiceImpl();
    private ClienteServiceImpl clienteServiceImpl = new ClienteServiceImpl();
    private MProductosServiceImpl mProductosServiceImpl = new MProductosServiceImpl();
    private DProductosServiceImpl dProductosServiceImpl = new DProductosServiceImpl();
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <title>Home</title>
        <style type="text/css">
            a{
                color: #CECECE;
            }
            span{
                color: #CECECE;
                background-color: #CECECE;
            }
            ul a i{
                margin-top: 10px;
                font-size: 25px;
            }
            nav{
                background: white;
            }
            .btn2{
                background-color: #ff5200; 
            }
            footer{
                padding: 20px;
                background: #5A0009;
                color: white;
            }
            #image{
                width: 8%;
                height: auto;
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
                                            } else if (cookie.getName().equals("idCliente")) {
                                                Connection con = datasource.getConnection();
                                                cliente = clienteServiceImpl.getCookie(cookie.getValue(), con);
                                                request.getSession().setAttribute("usuario", cliente);
                                            }
                                        }
                                    }
                                }
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
                                }
                            } else {
                            %>
                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                            <%
                                }
                            %>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- ________________________________________________ -->	


        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="img/carrusel1.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/carrusel2.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/carrusel3.jpg" class="d-block w-100" alt="...">
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

        <!-- _____________________________________ -->

        <div class="container mx-auto">
            <%
                try {
                    Connection con = datasource.getConnection();
                    ArrayList<DProductos> dProductosArray = dProductosServiceImpl.getAll(con);
                    if (dProductosArray == null) {

            %>
            <div class="alert alert-info" role="alert">
                Lo sentimos. Por el momento no hay productos en venta
            </div>
            <%                    } else {
            %>
            <div class="row m-3">
                <%
                    for (DProductos dProducto : dProductosArray) {
                        con = datasource.getConnection();
                        MProductos mProducto = mProductosServiceImpl.get(dProducto.getId_mprod(), con);
                        if (mProducto == null) {
                            break;
                        } else {
                            if (dProducto.getStock_prod() > 0) {
                %>
                <div class="card m-3" style="width: 18rem;">
                    <img src="<%=mProducto.getImg_prod()%>" class="card-img-top" alt="...">

                    <div class="card-body">
                        <h5 class="card-title"><%=mProducto.getNom_prod()%></h5>
                        <p class="card-text"><%=mProducto.getDes_prod()%></p>
                        <form action="description.jsp" method="post">
                            <input type="hidden" value="<%=mProducto.getId_mprod()%>" name="id"/>
                            <input type="submit" class="btn btn-danger btn2" value="Mas"/>
                        </form>
                    </div>
                </div>
                <br/>
                <%
                            }
                        }
                    }
                %>

            </div>
            <%
                }
            } catch (SQLException ex) {
            %>
            <div class="alert alert-info" role="alert">
                Lo sentimos. Por el momento no hay productos en venta
            </div>
            <%
                }
            %>
        </div>

        <footer>
            <div id="color">

            </div>
            <div class="container">
                <div class="row">
                    <div class="col">
                        Conócenos<br>  
                        Trabajar en DeepTure<br>
                        Información corporativa<br>
                        Departamento de tecnología<br>
                    </div>
                    <div class="col">
                        Gana dinero con nosotros<br>
                        Vender en carBuy<br>
                        Publica tu libro en Kindle<br>
                        Programa de afiliados<br>
                        Anuncia tus productos<br>
                    </div>
                    <div class="col">
                        Podemos ayudarte<br>
                        DeepTure y COVID-19<br>
                        Devolver o reemplazar productos<br>
                        Gestionar contenido y dispositivos<br>
                        Ayuda<br>
                    </div>
                    <div class="col">
                        Métodos de pago<br>
                        Tarjetas de crédito y débito<br>
                        Tarjetas de regalo<br>
                        Meses sin intereses<br>
                    </div>
                </div>
                <div class="row">
                    <img src="img/pp.jpg" class="mx-auto" id="image">
                </div>
            </div>
        </footer>

    </body>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</html>
