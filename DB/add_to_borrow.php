<?php
require 'database.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";
$evento = $_POST['causale'];
$imei = $_POST['imei'];
$datemillis = $_POST['date'];
$date = date("Y-m-d H:i:s.u", $datemillis/1000);
// error_log(print_r($date, TRUE));
$itemsArray = explode(",", trim($_POST['itemsID'], ','));
// echo $date . "</br>";
// var_dump($itemsArray);
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

?>