<?php
/**
 * Created by PhpStorm.
 * User: marco
 * Date: 24/01/19
 * Time: 12.04
 */
session_start();

if(!isset($_SESSION['login_user'])){
    header("location: index.php");
}
require 'database.php';
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Gestisci magazzino</title>
    <link rel="stylesheet" href="bootsrap/css/bootstrap.min.css">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">

</head>
<body id="page-top">
<?php include ("navbar.html");?>

<div id="wrapper">

    <?php include("sidebar.html");?>

    <div id="content-wrapper">

        <div class="container-fluid">

            <h1>Gestisci</h1>

            <a href="add_item.php" class="btn btn-primary" role="button">Aggiungi oggetto al magazzino</a> <br>
            <a href="remove_item.php" class="btn btn-primary" role="button">Rimuovi oggetto dal magazzino</a> <br>

            <?php
            // Create connection
            $conn = new mysqli($servername, $username, $password, $dbname);
            // Check connection
            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            $sql = "SELECT id, nome, marca, anno, posizione, descrizione FROM oggetto ";
            //var_dump($sql) . "<br>";
            $result = $conn->query($sql);

            ?>

            <!-- DataTables Example -->
            <div class="card mb-3">
                <div class="card-header">
                    <i class="fas fa-table"></i>
                    Oggetti registrati</div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Marca</th>
                                <th>Anno</th>
                                <th>Posizione</th>
                                <th>Descrizione</th>
                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Marca</th>
                                <th>Anno</th>
                                <th>Posizione</th>
                                <th>Descrizione</th>
                            </tr>
                            </tfoot>
                            <tbody>

                            <?php
                            if ($result)
                                // output data of each row
                                while($row = $result->fetch_row()):?>
                                    <tr>
                                        <td><?php echo $row[0];?></td>
                                        <td><?php echo $row[1];?></td>
                                        <td><?php echo $row[2];?></td>
                                        <td><?php echo $row[3];?></td>
                                        <td><?php echo $row[4];?></td>
                                        <td><?php echo $row[5];?></td>
                                    </tr>
                                <?php endwhile;?>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
            </div>

        </div>
    </div>
</div>

        <script src="bootsrap/js/bootstrap.min.js"></script>
        <script src="bootsrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>