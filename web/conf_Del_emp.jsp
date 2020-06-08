<%-- 
    Document   : conf_Del_cli
    Created on : 1/06/2020, 02:39:19 PM
    Author     : kcram
--%>

<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%!
    private Pattern noStndr = Pattern.compile("[^();''*--]+");

    private boolean checkKeys(String id, String pass) {
        Matcher mat1 = noStndr.matcher(id);
        Matcher mat2 = noStndr.matcher(pass);
        if (mat1.matches() && mat2.matches()) {
            return true;
        } else {
            return false;
        }
    }
%>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <title>My cuenta</title>
        <style type="text/css">
            a{
                color: #CECECE;
            }
        </style>
    </head>
    <body>
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
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                } else {
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

        <div class="container mx-auto m-5">
            <div class="row mx-auto">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Advertencia</h5>
                        <p class="card-text">Esta seguro de que quiere eliminar al empleado. Una vez hecho esto ya no se podra recuperar</p>
                        <a href="list_emp.jsp" class="btn btn-primary">Regresar</a>
                        <form action="EmpleadoController" method="post">
                            <%
                                String id = "";
                                String pass = "";
                                try {
                                    id = (String) request.getParameter("id_emp");
                                    pass = (String) request.getParameter("pass_emp");
                                    if (!checkKeys(id, pass)) {
                                        request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                    }
                                } catch (Exception ex) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                }
                            %>
                            <input type="hidden" value="<%= id%>" name="id_emp"/>
                            <input type="hidden" value="<%= pass%>" name="pass_emp"/>
                            <input type="hidden" value="borraCuentaExt" name="command"/>
                            <button class="btn btn-danger m-3 btn2" type="submit">Borrar Cuenta</button>
                        </form>
                    </div>
                </div>
            </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</html>
