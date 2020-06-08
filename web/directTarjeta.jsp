<%-- 
    Document   : añadirTarjeta
    Created on : 7/06/2020, 10:20:01 AM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    String msg = null;
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
            .btn4{
                background-color: #4242f5; 
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
        <title>CarritoCompras</title>
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
                                if ((cliente == null && empleado == null) || empleado != null) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                } else {

                                    if ((cliente == null && empleado != null) || (cliente == null && empleado == null)) {
                                        request.setAttribute("msg", "<div class=\"alert alert-info\" role=\"alert\">\n"
                                                + "Necesita estar logueado como cliente para acceder al carrito.\n"
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
                                    }if (empleado.getId_cpe() <= 2) {
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
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <br>
        <div class="container mx-auto" id="tarjeta">
            <div class="card mb-3" style="max-width: 540px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="img/card.PNG" class="card-img" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">Ingrese datos de la tarjeta</h5>
                            <!-- formulario de la tarjet-->
                            <form class="mx-auto" action="DCarritoCController" method="post">
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
                                    <div class="col-md-6 mb-3">
                                        <label for="validationDefault01">Numero de la tarjeta</label>
                                        <input name="noTar" type="text" class="form-control" id="validationDefault01" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="validationDefault03">Nombre y apellido</label>
                                        <input name="nomApp" type="text" class="form-control" id="validationDefault01" required>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <label for="validationDefault03">Fecha de expiracion</label>
                                        <input
                                            type="date"
                                            class="form-control"
                                            id="validationDefault03"
                                            name="expDat"
                                            required
                                            />
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="validationDefault03">Codigo de seguridad</label>
                                        <input name="codSec" type="text" class="form-control" id="validationDefault03" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
                                        <label class="form-check-label" for="invalidCheck2">
                                            Agree to <a href="termsAndConditions.html" style="color: blue;">terms and conditions</a>
                                        </label>
                                    </div>
                                </div>
                                <input type="hidden" value="compraDirecta" name="command"/>
                                <input type="hidden" value="<%= request.getParameter("id_dprod")%>" name="id_dprod"/>
                                <div class="form-row">
                                    <div class="col mb-3">
                                        <button class="btn btn-danger btn2" type="submit">Finalizar compra</button>
                                    </div>
                                    <div class="col mb-3">
                                        <a href="index.jsp" class="btn btn3">Cancelar compra</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
