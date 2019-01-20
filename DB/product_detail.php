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
$stmt->bind_param("ss", $id, $id);
$stmt->execute();
$stmt->bind_result($id, $nome, $marca);
while($stmt->fetch()) {
    $rows = $stmt->num_rows;
    //error_log(print_r($rows, TRUE));
    if($rows >= 1)
        echo "ok," . $id ."," . $nome . ",cacca";
    else
        echo "ko";
}
$stmt->close();
$conn->close();

?>