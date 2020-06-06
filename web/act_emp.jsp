<%-- 
    Document   : act_emp
    Created on : 2/06/2020, 03:20:23 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.service.impl.EmpleadoServiceImpl"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.sql.SQLException"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    String msg = null;
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;
    private EmpleadoServiceImpl empleadoServiceImpl = new EmpleadoServiceImpl();
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
                                Cliente clienteSession = null;
                                Empleado empleadoSession = null;
                                try {
                                    clienteSession = (Cliente) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                try {
                                    empleadoSession = (Empleado) session.getAttribute("usuario");
                                } catch (Exception ex) {
                                }
                                if ((clienteSession == null && empleadoSession == null) || clienteSession != null) {
                                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                } else {
                                    if (clienteSession != null || empleadoSession != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                        <li class="nav-item"><a class="nav-link" href="hcompras.jsp">Historial</a></li>
                            <%
                                if (empleadoSession != null) {
                            %>
                        <li class="nav-item"><a class="nav-link" href="stock.jsp">Inventario</a></li>
                            <%
                                if (empleadoSession.getId_cpe() == 1) {
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
    </nav>

    <!--_________________________________________ -->

    <div class="container mx-auto m-5">
        <div class="row mx-auto">
            <form action="EmpleadoController" id="formula" method="post">
                <input type="hidden" value="actualizarDatosHighLevel" name="command" />
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
                    String id = "";
                    String pass = "";
                    try {
                        id = request.getParameter("id_emp");
                        pass = request.getParameter("pass_emp");
                        if (!checkKeys(id, pass)) {
                            request.getRequestDispatcher("error_page.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.getRequestDispatcher("error_page.jsp").forward(request, response);
                    }
                    try {
                        Connection con = datasource.getConnection();
                        Empleado empleado = empleadoServiceImpl.get(id, pass, con);
                        if (empleado == null) {
                            request.getRequestDispatcher("error_page.jsp").forward(request, response);
                        } else {
                %>
                <input type="hidden" value="<%=empleado.getId_emp() %>" name="id_emp_org" />
                <input type="hidden" value="<%=empleado.getPass_emp() %>" name="pass_emp_org" />
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault01">Nombre de Usuario</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault01"
                            name="id_emp"
                            required
                            value="<%=empleado.getId_emp()%>"
                            />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault01">Nombre(s)</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault01"
                            name="nom_emp"
                            required
                            value="<%= empleado.getNom_emp()%>"
                            />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault02">Apellido paterno</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault02"
                            name="app_emp"
                            required
                            value="<%=empleado.getApp_emp()%>"
                            />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault03">Apellido materno</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault03"
                            name="apm_emp"
                            required
                            value="<%=empleado.getApm_emp()%>"
                            />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault03">Fecha de nacimiento</label>
                        <input
                            type="date"
                            class="form-control"
                            id="validationDefault03"
                            name="fnac_emp"
                            required
                            value="<%=empleado.getFnac_emp()%>"
                            />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault01">Direccion</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault01"
                            name="dir_emp"
                            required
                            value="<%=empleado.getDir_emp()%>"
                            />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault01">Telefono</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault01"
                            name="tel_emp"
                            required
                            value="<%=empleado.getTel_emp()%>"
                            />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault01">Celular</label>
                        <input
                            type="text"
                            class="form-control"
                            id="validationDefault01"
                            name="cel_emp"
                            required
                            value="<%=empleado.getCel_emp()%>"
                            />
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault02">Contraseña</label>
                        <input
                            type="password"
                            class="form-control"
                            id="validationDefault02"
                            name="pass_emp"
                            required
                            value="<%=empleado.getPass_emp()%>"
                            />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="validationDefault02">Privilegio</label>
                        <select class="form-control" name="id_cpe"/>
                        <option>Admin</option>
                        <option>Stocker</option>
                        <option>Consultor</option>
                        </select>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Enviar</button>
                <%
                        }
                    } catch (SQLException ex) {
                        request.getRequestDispatcher("error_page.jsp").forward(request, response);
                    }
                %>
                <a href="list_emp.jsp" class="btn btn-primary">Regresar</a>
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
