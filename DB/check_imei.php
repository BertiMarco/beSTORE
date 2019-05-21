<?php
require 'database.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";

$sql = 'SELECT nome, cognome FROM utente WHERE imei =?';
$imei = $_POST['imei'];
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $_POST['imei']);
//echo "bind done: $imei";
$stmt->execute();
//echo "stmt executed";
$stmt->bind_result($nome, $cognome);
//ATTENZIONE: non ho capito come funziona. Probabilemte il while è inutile ma così va.
while($stmt->fetch()) {}
//echo "fetched";
$rows = $stmt->num_rows;
//echo "ROWS: $rows";
if($rows >= 1)
    echo "ok," . $nome ."," . $cognome;
else
    echo "ko";

$stmt->close();
$conn->close();

?>