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
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Gestisci magazzino</title>
    <link rel="stylesheet" href="bootsrap/css/bootstrap.min.css">
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

        </div>
    </div>
</div>

        <script src="bootsrap/js/bootstrap.min.js"></script>
        <script src="bootsrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>