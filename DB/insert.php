<?php
require 'database.php';
$nameErr = $idErr = $marcaErr = $annoErr = $lunghezzaErr = $posizioneErr = "";
$nome = $id = $marca = $anno = $lunghezza = $posizione = $descrizione = "";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['id'])) {
        $nameErr = "Richiesto";
    }
    else {
        $id = test_input($_POST['id']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['name'])) {
        $nameErr = "Richiesto";
    }
    else {
        $id = test_input($_POST['name']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['brand'])) {
        $marca = "";
    }
    else {
        $marca = test_input($_POST['brand']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['year'])) {
        $anno = "";
    }
    else {
        $anno = test_input($_POST['year']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['length'])) {
        $lunghezza = "";
    }
    else {
        $lunghezza = test_input($_POST['length']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['location'])) {
        $posizioneErr = "Richiesto";
    }
    else {
        $posizione = test_input($_POST['location']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['description'])) {
        $descrizione = "";
    }
    else {
        $descrizione = test_input($_POST['description']);
    }
}


if($_POST['pwd'] === "password") {
    $sql = 'SELECT id FROM oggetto WHERE id = ?';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param($id);
}


$sql = 'INSERT INTO prestito (id_oggetto, imei_utente, preso_il, evento) VALUES (?, ?, ?, ?)';
$stmt = $conn->prepare($sql);
$done = TRUE;
foreach($itemsArray as $item) {
    $stmt->bind_param("ssss", $item, $imei, $date, $evento);
    if(!$stmt->execute())
        $done = FALSE;
}
if($done)
    echo "ok";
else
    echo "ko";
$stmt->close();
$conn->close();

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
  }

?>