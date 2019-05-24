<?php
session_start();

if(!isset($_SESSION['login_user'])){
    header("location: index.php");
}

require 'database.php';

?>
<!DOCTYPE html>
<html lang="it">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>beSTORE - Dashboard</title>

    <!-- Bootstrap core CSS-->
    <link href="bootsrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">

    <!-- Page level plugin CSS-->
    <link href="bootsrap/css/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="bootsrap/css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">
<?php include ("navbar.html");?>

<div id="wrapper">

    <?php include("sidebar.html");?>

    <div id="content-wrapper">

        <div class="container-fluid">

            <!-- Breadcrumbs-->
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="#">Dashboard</a>
                </li>
                <li class="breadcrumb-item active">Overview</li>
            </ol>

            <!-- Icon Cards-->
            <div class="row">
                <div class="col-xl-3 col-sm-6 mb-3">
                    <div class="card text-white bg-primary o-hidden h-100">
                        <div class="card-body">
                            <div class="card-body-icon">
                                <i class="fas fa-fw fa-comments"></i>
                            </div>
                            <div class="mr-5">
                                <?php

                                // Create connection
                                $conn = new mysqli($servername, $username, $password, $dbname);
                                // Check connection

                                if ($conn->connect_error) {
                                    die("Connection failed: " . $conn->connect_error);
                                }

                                $sql = 'SELECT COUNT(imei) FROM utente';
                                $result = $conn->query($sql);
                                if($result->num_rows > 0) {
                                    $row = $result->fetch_assoc();
                                    $numero_utenti = $row["COUNT(imei)"];
                                }
                                else
                                    $numero_utenti = 0;
                                $conn->close();

                                echo $numero_utenti . " Utenti registrati!";
                                ?>
                            </div>
                        </div>
                        <a class="card-footer text-white clearfix small z-1" href="manage_user.php">
                            <span class="float-left">Gestisci utenti</span>
                            <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                        </a>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 mb-3">
                    <div class="card text-white bg-warning o-hidden h-100">
                        <div class="card-body">
                            <div class="card-body-icon">
                                <i class="fas fa-fw fa-list"></i>
                            </div>
                            <div class="mr-5">
                                <?php

                                // Create connection
                                $conn = new mysqli($servername, $username, $password, $dbname);
                                // Check connection

                                if ($conn->connect_error) {
                                    die("Connection failed: " . $conn->connect_error);
                                }

                                $sql = 'SELECT COUNT(id) FROM oggetto';
                                $result = $conn->query($sql);
                                if($result->num_rows > 0) {
                                    $row = $result->fetch_assoc();
                                    $numero_oggetti = $row["COUNT(id)"];
                                }
                                else
                                    $numero_oggetti = 0;
                                $conn->close();

                                echo $numero_oggetti . " Oggetti registrati nel magazzino!";
                                ?>
                            </div>
                        </div>
                        <a class="card-footer text-white clearfix small z-1" href="manage_items.php">
                            <span class="float-left">Gestisci magazzino</span>
                            <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                        </a>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 mb-3">
                    <div class="card text-white bg-success o-hidden h-100">
                        <div class="card-body">
                            <div class="card-body-icon">
                                <i class="fas fa-fw fa-shopping-cart"></i>
                            </div>
                            <div class="mr-5">
                                <?php

                                // Create connection
                                $conn = new mysqli($servername, $username, $password, $dbname);
                                // Check connection

                                if ($conn->connect_error) {
                                    die("Connection failed: " . $conn->connect_error);
                                }

                                $sql = 'SELECT COUNT(DISTINCT (evento)) AS result FROM prestito WHERE restituito_il IS NULL';
                                $result = $conn->query($sql);
                                if($result->num_rows > 0) {
                                    $row = $result->fetch_assoc();
                                    $numero_eventi = $row["result"];
                                }
                                else
                                    $numero_eventi = 0;
                                $conn->close();

                                echo $numero_eventi . " Eventi in corso!";
                                ?>
                            </div>
                        </div>
                        <a class="card-footer text-white clearfix small z-1" href="#">
                            <span class="float-left">Gestisci eventi in corso</span>
                            <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                        </a>
                    </div>
                </div>
                <div class="col-xl-3 col-sm-6 mb-3">
                    <div class="card text-white bg-danger o-hidden h-100">
                        <div class="card-body">
                            <div class="card-body-icon">
                                <i class="fas fa-fw fa-life-ring"></i>
                            </div>
                            <div class="mr-5">
                                <?php
                                // Create connection
                                $conn = new mysqli($servername, $username, $password, $dbname);
                                // Check connection

                                if ($conn->connect_error) {
                                    die("Connection failed: " . $conn->connect_error);
                                }

                                $sql = 'SELECT COUNT(DISTINCT (evento)) AS result FROM prestito WHERE restituito_il IS NOT NULL';
                                $result = $conn->query($sql);
                                if($result->num_rows > 0) {
                                    $row = $result->fetch_assoc();
                                    $numero_eventi_conclusi = $row["result"];
                                }
                                else
                                    $numero_eventi = 0;
                                $conn->close();

                                echo $numero_eventi . " Eventi conclusi!";
                                ?>

                            </div>
                        </div>
                        <a class="card-footer text-white clearfix small z-1" href="#">
                            <span class="float-left">Gestisci eventi terminati</span>
                            <span class="float-right">
                    <i class="fas fa-angle-right"></i>
                  </span>
                        </a>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container-fluid -->

        <!-- Sticky Footer -->
        <footer class="sticky-footer">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright © beSTORE 2018</span>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="logout.php">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="jquery/jquery.min.js"></script>
<script src="bootsrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Page level plugin JavaScript-->
<script src="bootsrap/js/dataTables.bootstrap4.js"></script>

<!-- Custom scripts for all pages-->
<script src="bootsrap/js/sb-admin.min.js"></script>

<!-- Demo scripts for this page-->
<script src="bootsrap/js/datatables-demo.js"></script>
</body>
</html>