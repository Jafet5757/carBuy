<%-- 
    Document   : account
    Created on : 31/05/2020, 04:26:58 PM
    Author     : kcram
--%>

<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.carBuy.utils.model.Empleado"%>
<%@page import="com.carBuy.utils.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    Cliente cliente = null;
    Empleado empleado = null;
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
                                            try{
                                                cliente = (Cliente)request.getSession().getAttribute("usuario");
                                            }catch(Exception ex){}
                                            try{
                                                empleado = (Empleado)request.getSession().getAttribute("usuario");
                                            }catch(Exception ex){}
                                            if(cliente==null && empleado==null){
                                                request.getRequestDispatcher("error_page.jsp").forward(request, response);
                                            }else{
                                            if(cliente!=null || empleado!=null){
                                        %>
					<li class="nav-item"><a class="nav-link" href="account.jsp">mi cuenta</a></li>
                                        <li class="nav-item"><a class="nav-link" href="HitorialCompras.jsp">Historial</a></li>
                                        <%
                                            }else{
                                        %>
                                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
                                        <%
                                            }}
                                        %>
					<li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
					<li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
				</ul>
			</div>
		</div>
	</nav>
                                        
        	<div class="container mx-auto m-5">
		<div class="row mx-auto">
            <form action="ClienteController" method="post">
                    <div class="form-row">
                        <div class="col">
                          Mi cuenta
                        </div>
                    </div>
                    <%if(cliente!=null){%>
                    <div class="form-row">
                        <div class="col">
                          Usuario: <br> 
                          <input type="text" name="id_cli" value=<%=cliente.getId_cli()%>>
                        </div>

                        <div class="col">
                          Nombre: <br>
                          <input type="text" name="nom_cli" value=<%=cliente.getNom_cli()%>>
                        </div>

                        <div class="col">
                          Apellido Paterno: <br>
                          <input type="text" name="app_cli" value=<%=cliente.getApp_cli()%>>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                          Apellido Materno: <br>
                          <input type="text" name="apm_cli" value=<%=cliente.getApm_cli()%>>
                        </div>

                        <div class="col">
                          Fecha de nacimiento: <br>
                          <input type="text" name="fnac_cli" value=<%=cliente.getFnac_cli()%>>
                        </div>

                        <div class="col">
                          Direccion: <br>
                          <input type="text" name="dir_cli" value=<%=cliente.getDir_cli()%>>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                          Telefono fijo: <br>
                          <input type="text" name="tel_cli" value=<%=cliente.getTel_cli()%>>
                        </div>

                        <div class="col">
                          Telefono celular: 
                          <input type="text" name="cel_cli" value=<%=cliente.getCel_cli()%>>
                        </div>
                        
                        <div class="col">
                            Contraseña:<br>
                            <input type="hidden" name="pass_cli_cop" value=<%=cliente.getPass_cli()%>>
                            <input type="password" name="pass_cli_org" value=<%=cliente.getPass_cli()%>>
                        </div>
                    </div>
                    <input type="hidden" value="actualizarDatos" name="command"/>
                    <button class="btn btn-warning m-3 btn2" type="submit">Guardar cambios</button>

                        <hr>

                    <%}else{%>

                        <hr>

                    <div class="row">
                        <div class="col">
                          Mi cuenta
                        </div>

                        <div class="col">
                          Usuario: <br>
                          <input type="text" name="" value=<%=empleado.getId_emp()%>>
                        </div>

                        <div class="col">
                          Nombre: <br>
                          <input type="text" name="" value=<%=empleado.getNom_emp()%>>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                          Apellido Paterno: <br>
                          <input type="text" name="" value=<%=empleado.getApp_emp()%>>
                        </div>

                        <div class="col">
                          Apellido Materno: <br>
                          <input type="text" name="" value=<%=empleado.getApm_emp()%>>
                        </div>

                        <div class="col">
                          Fecha de nacimiento: <br>
                          <input type="text" name="" value=<%=empleado.getFnac_emp()%>>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                          Direccion: <br>
                          <input type="text" name="" value=<%=empleado.getDir_emp()%>>
                        </div>

                        <div class="col">
                          Telefono fijo: <br>
                          <input type="text" name="" value=<%=empleado.getTel_emp()%>>
                        </div>

                        <div class="col">
                          Telefono celular: <br>
                          <input type="text" name="" value=<%=empleado.getCel_emp()%>>
                        </div>
                    </div>
                        <div class="row">
                            <div class="col">
                                Contraseña:<br>
                                <input type="hidden" name="pass_cli_cop" value=<%=empleado.getPass_emp()%>>
                                <input type="password" name="pass_cli_org" value=<%=empleado.getPass_emp()%>>
                            </div>
                        </div>

                    <br><br>
                    <input type="hidden" value="actualizarDatosEmpleado" name="command"/>
                    <button class="btn btn-warning m-3 btn2" type="submit">Guardar cambios</button>
                    <%}%>
            </form>
		</div>
                <form action="ClienteController" method="post">
                    <input type="hidden" value="cerrarSesion" name="command"/>
                    <button class="btn btn-info m-3 btn2" type="submit">Cerrar Sesion</button>
                </form>

                <form action="conf_Del_cli.jsp" method="post">
                    <input type="hidden" value="borraCuenta" name="command"/>
                    <button class="btn btn-danger m-3 btn2" type="submit">Borrar Cuenta</button>
                </form>
		</div>
	</div>


</body>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</html>
    </body>
</html>
