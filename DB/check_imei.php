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

$sql = 'SELECT nome, cognome FROM utente WHERE imei = ?';
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $_POST['imei']);
$stmt->execute();
$stmt->bind_result($nome, $cognome);

$stmt->fetch();
$rows = $stmt->num_rows;
if($rows >= 1)
    echo "ok," . $nome ."," . $cognome;
else
    echo "ko";

$stmt->close();
$conn->close();

?>