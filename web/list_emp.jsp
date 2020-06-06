<%-- 
    Document   : list_emp
    Created on : 2/06/2020, 12:38:53 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.model.CPrivilegio_Empleado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.carBuy.utils.service.impl.CPrivilegioEServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.EmpleadoServiceImpl"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
    private EmpleadoServiceImpl empleadoServiceImpl = new EmpleadoServiceImpl();
    private CPrivilegioEServiceImpl cPrivilegioEServiceImpl = new CPrivilegioEServiceImpl();
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
                        <li class="nav-item"><a class="nav-link" href="stock.jsp">Inventario</a></li>
                            <%
                                if (empleadoSesion.getId_cpe() == 1) {
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
                                }
                            %>
                        <li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <br>
            <div>
                <a href="reg_emp.jsp" class="btn btn-primary">Agregar</a>
            </div>
            <%
                try {
                    Connection con = datasource.getConnection();
                    ArrayList<Empleado> empleadoArray = empleadoServiceImpl.getAll(con);
                    if (empleadoArray == null) {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron registros
            </div>
            <%
            } else {
            %>
            <div>
                <h2>Empleados</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Usuario</th>
                            <th>Nombre</th>
                            <th>APaterno</th>
                            <th>AMaterno</th>
                            <th>FechaNac</th>
                            <th>Direccion</th>
                            <th>Telefono</th>
                            <th>Celular</th>
                            <th>Contraseña</th>
                            <th>Puesto</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Empleado empleado : empleadoArray) {
                                if (!empleado.getId_emp().equals(empleadoSesion.getId_emp())) {
                                    try {
                                        con = datasource.getConnection();
                                        CPrivilegio_Empleado cPrivilegio_Empleado = cPrivilegioEServiceImpl.get(empleado.getId_cpe(), con);
                        %>
                        <tr>
                            <th><%= empleado.getId_emp()%></th>
                            <th><%= empleado.getNom_emp()%></th>
                            <th><%= empleado.getApp_emp()%></th>
                            <th><%= empleado.getApm_emp()%></th>
                            <th><%= empleado.getFnac_emp()%></th>
                            <th><%= empleado.getDir_emp()%></th>
                            <th><%= empleado.getTel_emp()%></th>
                            <th><%= empleado.getCel_emp()%></th>
                            <th><%= empleado.getPass_emp()%></th>
                            <th><%= cPrivilegio_Empleado.getTipo_pe()%></th>
                            <th>
                                <form action="act_emp.jsp" method="post">
                                    <input type="hidden" value="<%= empleado.getId_emp()%>" name="id_emp"/>
                                    <input type="hidden" value="<%= empleado.getPass_emp()%>" name="pass_emp"/>
                                    <input type="submit" class="btn btn-info" value="Editar"/>
                                </form>
                            </th>
                            <th>
                                <form action="conf_Del_emp.jsp" method="post">
                                    <input type="hidden" value="<%= empleado.getId_emp()%>" name="id_emp"/>
                                    <input type="hidden" value="<%= empleado.getPass_emp()%>" name="pass_emp"/>
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
    </div>


</body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</html>
