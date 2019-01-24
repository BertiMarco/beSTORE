<?php
/**
 * Created by PhpStorm.
 * User: marco
 * Date: 24/01/19
 * Time: 11.57
 */

session_start();

if(!isset($_SESSION['login_user'])){
    header("location: index.php");
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Gestisci utenti</title>
    <link rel="stylesheet" href="bootsrap/css/bootstrap.min.css">
</head>
<body>
<h1>Gestisci</h1>


<a href="add_user.php" class="btn btn-primary" role="button">Aggiungi utente</a> <br>
<a href="remove_user.php" class="btn btn-primary" role="button">Rimuovi utente</a> <br>

<script src="bootsrap/js/bootstrap.min.js"></script>
<script src="bootsrap/js/bootstrap.bundle.min.js"></script>

</body>
</html>