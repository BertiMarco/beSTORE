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

$sql = "SELECT DISTINCT evento FROM prestito WHERE restituito_il IS NULL";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $res = "ok";
    while($row = $result->fetch_assoc()) {
        $res .=  "," . $row['evento'];
    }
    echo $res;
} 
else 
    echo "ko";

$conn->close();

?>