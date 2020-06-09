<%-- 
    Document   : hcompras
    Created on : 6/06/2020, 11:08:06 AM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="com.carBuy.utils.model.MCarrito_Compra"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.carBuy.utils.service.impl.MCarritoCServiceImpl"%>
<%@page import="com.carBuy.utils.model.DHistorial"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.service.impl.DHistorialServiceImpl"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
    private DHistorialServiceImpl dHistorialServiceImpl = new DHistorialServiceImpl();
    private MCarritoCServiceImpl mCarritoCServiceImpl = new MCarritoCServiceImpl();
    private DProductosServiceImpl dProductosServiceImpl = new DProductosServiceImpl();
    private MProductosServiceImpl mProductosServiceImpl = new MProductosServiceImpl();
%>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
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
            .btn3{
                background-color: #dbdbdb; 
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
        <title>HistorialCompras</title>
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
                                if ((cliente == null && empleado != null) || (cliente == null && empleado == null)) {
                                    request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                            + "Necesita estar logueado como cliente para acceder a su historial.\n"
                                            + "</div>");
                                    request.getRequestDispatcher("confirm.jsp").forward(request, response);
                                }
                                if (cliente != null || empleado != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                        <li class="nav-item"><a class="nav-link" href="hcompras.jsp">Historial</a></li>
                            <%
                                if (empleado != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                            <%
                                if (empleado.getId_cpe() == 1) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="list_emp.jsp">Empleados</a></li>
                            <%
                                }
                                if (empleado.getId_cpe() <= 2) {
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
                            %>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-5">
            <%
                try {
                    Connection con = datasource.getConnection();
                    ArrayList<DHistorial> dHistorialArray = dHistorialServiceImpl.getHis(cliente.getId_cli(), con);
                    if (dHistorialArray != null) {
                        for (DHistorial dHistorial : dHistorialArray) {
                            dHistorial.getId_dhis();
                            con = datasource.getConnection();
                            ArrayList<MCarrito_Compra> mProductosArray = mCarritoCServiceImpl.getAll(dHistorial.getId_dhis(), con);
                            if (mProductosArray != null) {
            %>
            <p class="font-weight-bold">Fecha del pedido: <%= dHistorial.getFecha()%></p><br>
            <div class="row">
                <%
                    for (MCarrito_Compra mProducto : mProductosArray) {
                        con = datasource.getConnection();
                        DProductos dProductos = dProductosServiceImpl.get(mProducto.getId_dprod(), con);
                        if (dProductos != null) {

                            con = datasource.getConnection();
                            MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);

                %>

                <div class="card mb-3  ml-2" style="max-width: 540px;">
                    <div class="row no-gutters">
                        <div class="col-md-4">
                            <img src="<%=mProductos.getImg_prod()%>" class="card-img" alt="...">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title"><%=mProductos.getNom_prod()%></h5>
                                <p>Precio unitario: <%= dProductos.getPrecio_prod()%>$</p>
                            </div>
                        </div>
                    </div>
                </div>

                <%} else {
                %>
                <div class="card mb-3  ml-2" style="max-width: 540px;">
                    <div class="alert alert-info" role="alert">
                        El producto ya no se encuentra disponible.
                    </div>
                </div>  
                <%
                        }
                    }
                %></div><p class="font-weight-bold">Sub total: <%= dHistorial.getSub_total()%>$</p>
            <p class="font-weight-bold">Iva: <%= dHistorial.getIva()%>$</p>
            <p class="font-weight-bold">Total: <%= dHistorial.getTotal()%>$</p>
            <hr>
            <%
                    } else {
                    }
                }
            } else {
            %>
            <div class="alert alert-info" role="alert">
                No Hay productos en su carrito de compras
            </div>
            <%
                }
            } catch (Exception ex) {
            %>
            <div class="alert alert-warning" role="alert">
                Ocurrio un error. Intentelo mas tarde.
            </div>
            <%
                }
            %>
        </div>
    </body>
</html>
