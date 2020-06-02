<%-- 
    Document   : error_page
    Created on : 1/06/2020, 07:00:34 PM
    Author     : kcram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
	<title>Error</title>
	<style type="text/css">
		a{
			color: #CECECE;
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
                                        <li class="nav-item"><a class="nav-link" href="login_cli.jsp">iniciar sesion</a></li>
					<li class="nav-item"><a class="nav-link" href="graficas.jsp">Estadisticas</a></li>
					<li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
				</ul>
			</div>
		</div>
	</nav>
        <br>
        <div class="jumbotron">
          <h1 class="display-4">ERROR: 404</h1>
          <p class="lead">No se encontro la pagina que buscas</p>
          <hr class="my-4">
          <p>La pagina solicitada no aparece en nuestro servidor. Porfavor intentelo mas tarde</p>
          <a class="btn btn-primary btn-lg" href="index.jsp" role="button">Inicio</a>
        </div>
    </body>
</html>
