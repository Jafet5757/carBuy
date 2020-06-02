<%-- 
    Document   : description
    Created on : 1/06/2020, 05:08:25 PM
    Author     : kcram
--%>

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
    Cliente cliente = null;
    Empleado empleado = null;
    @Resource(name="jdbc/dbPool")
    private DataSource datasource;
    private boolean checkKeys(String id){
        Matcher mat1 = noStndr.matcher(id);
        if(mat1.matches()){
            return true;
        }else{
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
                                            try{
                                                cliente = (Cliente)request.getSession().getAttribute("usuario");
                                            }catch(Exception ex){}
                                            try{
                                                empleado = (Empleado)request.getSession().getAttribute("usuario");
                                            }catch(Exception ex){}
                                            if(cliente!=null || empleado!=null){
                                        %>
					<li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                                        <li class="nav-item"><a class="nav-link" href="HitorialCompras.jsp">Historial</a></li>
                                        <%
                                            }else{
                                        %>
                                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                                        <%
                                            }
                                        %>
					<li class="nav-item"><a class="nav-link" href="graficas.html">Estadisticas</a></li>
					<li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
				</ul>
			</div>
		</div>
	</nav>
        <div class="container mx-auto m-5 p-3" id="ocul">
		<div class="card mb-3" style="max-width: 1500px; min-width: 600px">
		  <div class="row no-gutters">
                      <%
                        try{
                            Connection con = datasource.getConnection();
                            String id = request.getParameter("id");
                            System.out.println(id);
                            if(checkKeys(id)){
                            String sql = "select * from DProductos where id_dprod="+id+"";
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            rs.next();
                            System.out.println(rs.getInt("id_dprod"));
                            sql = "select * from mproductos where id_mprod="+String.valueOf(rs.getInt("id_mprod"))+"";
                            Statement st2 = con.createStatement();
                            ResultSet rs2 = st2.executeQuery(sql);
                            rs2.next();
                            System.out.println(rs.getInt("id_mprod"));
                            sql = "select * from ccolorprod where id_ccp="+String.valueOf(rs.getInt("id_ccp"))+"";
                            Statement st3 = con.createStatement();
                            ResultSet rs3 = st3.executeQuery(sql);
                            rs3.next();
                            sql = "select * from catproductos where id_prod="+String.valueOf(rs.getInt("id_prod"))+"";
                            Statement st4 = con.createStatement();
                            ResultSet rs4 = st4.executeQuery(sql);
                            rs4.next();
                      %>
		    <div class="col-md-4">
                        <img src="<%=rs2.getString("img_prod")%>" class="card-img m-2" alt="...">
		    </div>
		    <div class="col-md-8">
		      <div class="card-body">
		        <h5 class="card-title h1"><%=rs2.getString("nom_prod")%></h5>		       
		        <p>Color: <%=rs3.getString("tipo_ccp")%><br><br>marca: <%=rs4.getString("tipo_prod")%><br><br>Precio: $<%=rs.getString("precio_prod")%> mxn</p>
		        <form class="mt-3">
		        	<button class="btn btn-danger m-2 btn2" id="buy">comprar</button>
		        	<button class="btn btn3">Agregar al carro de compras</button>
		        </form>
		        <hr><p class="card-text"><%=rs2.getString("des_prod")%></p>
		      </div>
		    </div>
                      <%
                          rs.close();
                          rs2.close();
                          rs3.close();
                          rs4.close();
                          }else{
                        %>
                        <div class="alert alert-info" role="alert">
                            Lo sentimos. El producto que busca no esta disponible.
                        </div>
                        <%
                            }
                           
                          con.close();
                            }catch(Exception ex){
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
