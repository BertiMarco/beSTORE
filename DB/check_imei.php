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
while($stmt->fetch())
    echo "ok," . $nome ."," . $cognome;
$stmt->close();
//$res = $stmt->get_result(); pare non ci sia la versione adatta as essere usato
/*mysqli_stmt_bind_result($stmt, $col1, $col2);
while(mysqli_stmt_fetch($stmt, $col1, $col2)) {
    $res = 
}
echo "</br>";
var_dump($sql);

if ($res->num_rows > 0) {
    // output data of each row
    while($row = $res->fetch_assoc()) {
        echo "ok," . $row['nome'] . "," . $row['cognome'];
    }
} 
else {
    echo "ko";
}*/

$conn->close();

?>