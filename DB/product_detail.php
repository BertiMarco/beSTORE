<?php

require 'database.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";

//file_put_contents('log', print_r($_POST['id'], TRUE));
error_log(print_r($_POST['id'], TRUE));
$id = $_POST["id"];
error_log(print_r($id, TRUE));

$sql = 'SELECT id, nome, marca 
    FROM oggetto
    WHERE id = ? AND id NOT IN
    (SELECT id
    FROM prestito
    WHERE id_oggetto = ? AND restituito_il IS NULL)';
$stmt = $conn->prepare($sql);
error_log(print_r("statement prepared", TRUE));
$stmt->bind_param("ss", $id, $id);
error_log(print_r("param binded", TRUE));
$executed = $stmt->execute();
error_log(print_r("executed ". $executed, TRUE));
$stmt->bind_result($id, $nome, $marca);
$stmt->fetch();
$rows = $stmt->num_rows;
error_log(print_r("rows " . $rows, TRUE));
$stmt->close();
if($rows >= 1)
    echo "ok," . $id ."," . $nome . ",cacca";
else {
    $sql = "SELECT p.id_oggetto, p.imei_utente, u.nome, u.cognome 
        FROM prestito p JOIN utente u ON p.imei_utente = u.imei
        WHERE p.id_oggetto = ? AND p.restituito_il IS NULL";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    $executed = $stmt->execute();
    $stmt->bind_result($id, $imei, $user_name, $user_surname);
    $stmt->fetch();
    $rows = $stmt->num_rows;
    error_log(print_r("ROWS " . $rows, TRUE));
    if($rows >= 1)
        echo "ko," . $id . "," . $imei . "," . $user_name . "," . $user_surname; 
    else
        echo "unregistered";
}

$stmt->close();
$conn->close();

?>