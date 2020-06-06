<%-- 
    Document   : description
    Created on : 1/06/2020, 05:08:25 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.model.CatProductos"%>
<%@page import="com.carBuy.utils.service.impl.CatProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.CColorProdServiceImpl"%>
<%@page import="com.carBuy.utils.model.CColorProd"%>
<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
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
    private Pattern noStndr = Pattern.compile("[^();''*--]+");
    private DProductosServiceImpl dProductosServiceImpl = new DProductosServiceImpl();
    private MProductosServiceImpl mProductosServiceImpl = new MProductosServiceImpl();
    private CColorProdServiceImpl cColorProdServiceImpl = new CColorProdServiceImpl();
    private CatProductosServiceImpl catProductosServiceImpl = new CatProductosServiceImpl();
    @Resource(name = "jdbc/dbPool")
    private DataSource datasource;

    private boolean checkKeys(String id) {
        Matcher mat1 = noStndr.matcher(id);
        if (mat1.matches()) {
            return true;
        } else {
            return false;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <title>description</title>
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
        <div class="container mx-auto m-5 p-3" id="ocul">
            <div class="card mb-3" style="max-width: 1500px; min-width: 600px">
                <div class="row no-gutters">
                    <%
                        try {
                            Connection con = datasource.getConnection();
                            String id = request.getParameter("id");
                            int id_dprod = Integer.parseInt(id);
                            DProductos dProductos = dProductosServiceImpl.get(id_dprod, con);
                            con = datasource.getConnection();
                            MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                            con = datasource.getConnection();
                            CColorProd cColorProd = cColorProdServiceImpl.get(dProductos.getId_ccp(), con);
                            con = datasource.getConnection();
                            CatProductos catProductos = catProductosServiceImpl.get(dProductos.getId_prod(), con);
                            if (dProductos == null || mProductos == null || cColorProd == null || catProductos == null) {
                    %>
                    <div class="alert alert-info" role="alert">
                        Lo sentimos. El producto que busca no esta disponible.
                    </div>
                    <%
                    } else {
                    %>
                    <div class="col-md-4">
                        <img src="<%=mProductos.getImg_prod()%>" class="card-img m-2" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title h1"><%=mProductos.getNom_prod()%></h5>		       
                            <p>Color: <%=cColorProd.getTipo_ccp()%><br><br>marca: <%=catProductos.getTipo_prod()%><br><br>Precio: $<%=dProductos.getPrecio_prod()%> mxn</p>
                            <div class="row">
                                <form class="col-md-auto mt-3" action="DCarritoCController" method="post">
                                    <input type="hidden" value="<%= dProductos.getId_dprod()%>" name="id_dprod"/>
                                    <input type="hidden" value="compraDirecta" name="command"/>
                                    <input type="submit" class="btn btn-danger m-2 btn2" id="buy" value="Comprar"/>
                                </form>
                                <form class="col-md-auto mt-3" action="DCarritoCController" method="post">
                                    <input type="hidden" value="<%= dProductos.getId_dprod()%>" name="id_dprod"/>
                                    <input type="hidden" value="agregarCarrito" name="command"/>
                                    <input type="submit" class="btn btn3" value="Agregar al carro de compras"/>
                                </form>
                            </div>
                            <hr><p class="card-text"><%=mProductos.getDes_prod()%></p>
                        </div>
                    </div>
                    <%
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    %>
                    <div class="alert alert-info" role="alert">
                        Lo sentimos. El producto que busca no esta disponible.
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </body>
</html>
