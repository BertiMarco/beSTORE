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
echo "Connessione riuscita\n";

$sql = "SELECT p.id_oggetto, p.imei_utente, u.nome, u.cognome 
FROM prestito p JOIN utente u ON p.imei_utente = u.imei
WHERE p.id_oggetto = 1 AND p.restituito_il IS NULL";
echo "</br>";
var_dump($sql) . "</br>";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        foreach($row as $value)
            echo $value . "</br>";
    }
} 
else {
    echo "0 results";
}

$conn->close();

?>