<?php
$servername = "localhost";
$username = "xsigomft_bestore";
$password = "bertistore;2019";
$dbname = "xsigomft_bestore";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";
// echo $date . "</br>";
// var_dump($itemsArray);
$sql = 'SELECT id, nome, marca, posizione 
    FROM oggetto
    WHERE id = ? AND id IN
    (SELECT id
    FROM prestito
    WHERE id_oggetto = ? AND restituito_il IS NULL)';
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $_POST['id'], $_POST['id']);
$stmt->execute();

$stmt->bind_result($id, $nome, $marca, $posizione);
while($stmt->fetch())
    echo "ok," . $id ."," . $nome . ",cacca" . "," . $posizione;

$stmt->close();
$conn->close();

?>