<%-- 
    Document   : login_cli
    Created on : 1/06/2020, 07:04:09 PM
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
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
            integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
            crossorigin="anonymous"
            />
        <title>Login</title>
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
            <%
                Cliente cliente = null;
                Empleado empleado = null;
                try {
                    cliente = (Cliente) request.getSession().getAttribute("usuario");
                } catch (Exception ex) {
                }
                try {
                    empleado = (Empleado) request.getSession().getAttribute("usuario");
                } catch (Exception ex) {
                }
                if (cliente != null || empleado != null) {
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            %>
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
                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--_________________________________________ -->

        <div class="container mx-auto m-5">
            <div class="row mx-auto">
                <form action="ClienteController" id="formula" method="post">
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
                    <input type="hidden" value="iniciarSesion" name="command"/>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault01">Nombre de usuario</label>
                            <input
                                type="text"
                                class="form-control"
                                id="validationDefault01"
                                name="id"
                                required
                                />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="validationDefault02">Contraseña</label>
                            <input
                                type="password"
                                class="form-control"
                                id="validationDefault02"
                                name="pass"
                                required
                                />
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Enviar</button>
                    <a href="login_emp.jsp"><button type="button" class="btn btn-secondary">Entrar como empleado</button></a>
                    <a href="reg_cli.jsp"><button type="button" class="btn btn-info">Registrarse</button></a>
                </form>
            </div>
        </div>
    </body>
    <script
        src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"
    ></script>
    <script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"
    ></script>
    <script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"
    ></script>
</html>
