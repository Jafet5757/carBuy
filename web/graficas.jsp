<%-- 
    Document   : graficas
    Created on : 7/06/2020, 12:08:30 PM
    Author     : kcram
--%>

<%@page import="com.carBuy.utils.model.MProductos"%>
<%@page import="com.carBuy.utils.model.DProductos"%>
<%@page import="com.carBuy.utils.service.impl.MProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.DProductosServiceImpl"%>
<%@page import="com.carBuy.utils.service.impl.MCarritoCServiceImpl"%>
<%@page import="com.carBuy.utils.model.MCarrito_Compra"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.carBuy.utils.model.DHistorial"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.carBuy.utils.service.impl.DHistorialServiceImpl"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
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

    private LocalDate IniWeek(LocalDate ahora) {
        int restWeek = ahora.getDayOfWeek().getValue();
        return ahora.minusDays(restWeek - 1);
    }

    private LocalDate EndWeek(LocalDate ahora) {
        int restWeek = 7 - ahora.getDayOfWeek().getValue();
        return ahora.plusDays(restWeek);
    }

    private LocalDate InitMonth(LocalDate ahora) {
        int restMonth = ahora.getDayOfMonth();
        return ahora.minusDays(restMonth - 1);
    }

    private LocalDate EndMonth(LocalDate ahora) {
        int monthLenght = ahora.lengthOfMonth();
        int restMonth = monthLenght - ahora.getDayOfMonth();
        return ahora.plusDays(restMonth);
    }

    private LocalDate getHalfMonth(LocalDate ahora) {
        LocalDate IniMonth = InitMonth(ahora);
        return IniMonth.plusDays(14);
    }

    private LocalDate IniFortnight(LocalDate ahora) {
        LocalDate halfMonth = getHalfMonth(ahora);
        if (ahora.isAfter(halfMonth)) {
            return halfMonth;
        } else {
            return InitMonth(ahora);
        }
    }

    private LocalDate EndFortnight(LocalDate ahora) {
        LocalDate halfMonth = getHalfMonth(ahora);
        if (ahora.isAfter(halfMonth)) {
            return EndMonth(ahora);
        } else {
            return halfMonth;
        }
    }
%>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <title>graph</title>
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
        <script src="Chart.js"></script>
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
                                if ((cliente == null && empleado == null) || cliente != null) {
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
                                }
                            %>
                        <li class="nav-item"><a href="ccompras.jsp"><i class="fas fa-shopping-basket m-2" onclick="replaceW()"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <script>
            var opciones = {
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true,
                            },
                        },
                    ],
                },
            };
        </script>
        <div class="container mx-auto m-5 p-3" id="ocul">
            <%
                try {
                    LocalDateTime fecha = LocalDateTime.now();
                    DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_DATE;
                    LocalDate ahora = LocalDate.parse(fecha.format(isoFecha));
                    LocalDate IniWeek = IniWeek(ahora);
                    LocalDate EndWeek = EndWeek(ahora);
                    LocalDate IniMonth = InitMonth(ahora);
                    LocalDate EndMonth = EndMonth(ahora);
                    LocalDate IniFortnight = IniFortnight(ahora);
                    LocalDate EndFortnight = EndFortnight(ahora);
                    Connection con = datasource.getConnection();
                    ArrayList<DHistorial> dHistorialWeek = dHistorialServiceImpl.getByDate(IniWeek, EndWeek, con);
                    con = datasource.getConnection();
                    ArrayList<DHistorial> dHistorialMonth = dHistorialServiceImpl.getByDate(IniMonth, EndMonth, con);
                    con = datasource.getConnection();
                    ArrayList<DHistorial> dHistorialFortnight = dHistorialServiceImpl.getByDate(IniFortnight, EndFortnight, con);
                    if (dHistorialWeek != null || dHistorialMonth != null || dHistorialFortnight != null) {
                        ArrayList<String> labelsWeekArray = new ArrayList<>();
                        ArrayList<Integer> stockWeekArray = new ArrayList<>();
                        ArrayList<String> labelsMonthArray = new ArrayList<>();
                        ArrayList<Integer> stockMonthArray = new ArrayList<>();
                        ArrayList<String> labelsFortnightArray = new ArrayList<>();
                        ArrayList<Integer> stockFortnightArray = new ArrayList<>();
                        if (dHistorialWeek != null) {
                            for (DHistorial dHistorial : dHistorialWeek) {
                                con = datasource.getConnection();
                                ArrayList<MCarrito_Compra> mCarritoCArray = mCarritoCServiceImpl.getAll(dHistorial.getId_dhis(), con);
                                for (MCarrito_Compra mCarritoC : mCarritoCArray) {
                                    con = datasource.getConnection();
                                    DProductos dProductos = dProductosServiceImpl.get(mCarritoC.getId_dprod(), con);
                                    con = datasource.getConnection();
                                    MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                                    if (!labelsWeekArray.contains(mProductos.getNom_prod())) {
                                        labelsWeekArray.add(mProductos.getNom_prod());
                                        stockWeekArray.add(1);
                                    } else {
                                        int index = labelsWeekArray.indexOf(mProductos.getNom_prod());
                                        stockWeekArray.set(index, stockWeekArray.get(index) + 1);
                                    }
                                }

                            }
                        }
                        if (dHistorialMonth != null) {
                            for (DHistorial dHistorial : dHistorialMonth) {
                                con = datasource.getConnection();
                                ArrayList<MCarrito_Compra> mCarritoCArray = mCarritoCServiceImpl.getAll(dHistorial.getId_dhis(), con);
                                for (MCarrito_Compra mCarritoC : mCarritoCArray) {
                                    con = datasource.getConnection();
                                    DProductos dProductos = dProductosServiceImpl.get(mCarritoC.getId_dprod(), con);
                                    con = datasource.getConnection();
                                    MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                                    if (!labelsMonthArray.contains(mProductos.getNom_prod())) {
                                        labelsMonthArray.add(mProductos.getNom_prod());
                                        stockMonthArray.add(1);
                                    } else {
                                        int index = labelsMonthArray.indexOf(mProductos.getNom_prod());
                                        stockMonthArray.set(index, stockMonthArray.get(index) + 1);
                                    }
                                }
                            }
                        }
                        if (dHistorialFortnight != null) {
                            for (DHistorial dHistorial : dHistorialFortnight) {
                                con = datasource.getConnection();
                                ArrayList<MCarrito_Compra> mCarritoCArray = mCarritoCServiceImpl.getAll(dHistorial.getId_dhis(), con);
                                for (MCarrito_Compra mCarritoC : mCarritoCArray) {
                                    con = datasource.getConnection();
                                    DProductos dProductos = dProductosServiceImpl.get(mCarritoC.getId_dprod(), con);
                                    con = datasource.getConnection();
                                    MProductos mProductos = mProductosServiceImpl.get(dProductos.getId_mprod(), con);
                                    if (!labelsFortnightArray.contains(mProductos.getNom_prod())) {
                                        labelsFortnightArray.add(mProductos.getNom_prod());
                                        stockFortnightArray.add(1);
                                    } else {
                                        int index = labelsFortnightArray.indexOf(mProductos.getNom_prod());
                                        stockFortnightArray.set(index, stockFortnightArray.get(index) + 1);
                                    }
                                }

                            }
                        }
                        String labels = "";
                        String stock = "";
                        if (!labelsWeekArray.isEmpty() || !stockWeekArray.isEmpty()) {
                            labels = "";
                            stock = "";
                            labels = labels + "[";
                            for (String label : labelsWeekArray) {
                                labels = labels + "'" + label + "', ";
                            }
                            labels = labels + "]";
                            stock = stockWeekArray.toString();
            %>
            <div class="card mb-3" style="max-width: 1500px; min-width: 600px">
                <h3 class="card-title m-3">Productos vendidos en la semana.</h3>
                <canvas id="estadisticas_week" style="max-width: 1500px; min-width: 600px"></canvas>
            </div>
            <script>
                var ctx_w = document.getElementById("estadisticas_week").getContext("2d");
                ctx_w.height = 500;
                var myBarChart = new Chart(ctx_w, {
                    type: "bar",
                    data: {
                        labels: (<%=labels%>),
                        datasets: [
                            {
                                label: "Vendidos",
                                data: (<%=stock%>),
                                backgroundColor: "rgba(153, 0, 0,0.5)",
                                borderWidth: 3,
                            },
                        ],
                    },
                    options: opciones,
                });
            </script>
            <hr>
            <%
            } else {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron datos de esta semana.
            </div>
            <br>
            <%
                }
                if (!labelsFortnightArray.isEmpty() || !stockFortnightArray.isEmpty()) {
                    labels = "";
                    stock = "";
                    labels = labels + "[";
                    for (String label : labelsFortnightArray) {
                        labels = labels + "'" + label + "', ";
                    }
                    labels = labels + "]";
                    stock = stockFortnightArray.toString();
            %>
            <div class="card mb-3" style="max-width: 1500px; min-width: 600px">
                <h3 class="card-title m-3">Productos vendidos en la quincena.</h3>
                <canvas id="estadisticas_fortnight" style="max-width: 1500px; min-width: 600px"></canvas>
            </div>
            <script>
                var ctx_f = document.getElementById("estadisticas_fortnight").getContext("2d");
                ctx_f.height = 500;
                var myBarChart = new Chart(ctx_f, {
                    type: "bar",
                    data: {
                        labels: (<%=labels%>),
                        datasets: [
                            {
                                label: "Vendidos",
                                data: (<%=stock%>),
                                backgroundColor: "rgba(153, 0, 0,0.5)",
                                borderWidth: 3,
                            },
                        ],
                    },
                    options: opciones,
                });
            </script>
            <hr>
            <%
            } else {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron datos de esta quincena.
            </div>
            <br>
            <%
                }
                if (!labelsMonthArray.isEmpty() || !stockMonthArray.isEmpty()) {
                    labels = "";
                    stock = "";
                    labels = labels + "[";
                    for (String label : labelsMonthArray) {
                        labels = labels + "'" + label + "', ";
                    }
                    labels = labels + "]";
                    stock = stockMonthArray.toString();
            %>
            <div class="card mb-3" style="max-width: 1500px; min-width: 600px">
                <h3 class="card-title m-3">Productos vendidos en el mes.</h3>
                <canvas id="estadisticas_month" style="max-width: 1500px; min-width: 600px"></canvas>
            </div>
            <script>
                var ctx_m = document.getElementById("estadisticas_month").getContext("2d");
                ctx_m.height = 500;
                var myBarChart = new Chart(ctx_m, {
                    type: "bar",
                    data: {
                        labels: (<%=labels%>),
                        datasets: [
                            {
                                label: "Vendidos",
                                data: (<%=stock%>),
                                backgroundColor: "rgba(153, 0, 0,0.5)",
                                borderWidth: 3,
                            },
                        ],
                    },
                    options: opciones,
                });
            </script>
            <hr>
            <%
            } else {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron datos de este mes.
            </div>
            <br>
            <%
                }
            } else {
            %>
            <div class="alert alert-info" role="alert">
                No se encontraron datos
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
