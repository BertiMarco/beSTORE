<?php
require 'database.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connessione riuscita</br>";
$imei = $_POST['imei'];
$datemillis = $_POST['date'];
$date = date("Y-m-d H:i:s.u", $datemillis/1000);
$itemsArray = explode(",", trim($_POST['itemsID'], ','));
// echo $date . "</br>";
// var_dump($itemsArray);
$sql = 'UPDATE prestito SET restituito_da = ?, restituito_il = ? WHERE id_oggetto = ? AND restituito_il IS NULL';
$stmt = $conn->prepare($sql);
$done = TRUE;
foreach($itemsArray as $item) {
    $stmt->bind_param("sss", $imei, $date, $item);
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