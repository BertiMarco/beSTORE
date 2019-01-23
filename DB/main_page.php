<?php
session_start();

if(!isset($_SESSION['login_user'])){
    header("location: index.php");
}
?>

<!DOCTYPE html>
<html>
<head>
<title>Benvenuto</title>
<h1>Benvenuto <?php echo $_SESSION["login_user"];?></h1>

<a href="insertion.php">Aggiungi oggetto al magazzino</a> <br>
<a href="remove.php">Rimuovi oggetto dal magazzino</a> <br>
<a href="add_user.php">Aggiungi utente</a> <br>
<a href="remove_user.php">Rimuovi utente</a> <br>
<a href="logout.php">Logout</a> <br>
