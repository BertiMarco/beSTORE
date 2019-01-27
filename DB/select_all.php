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
echo "Connessione riuscita <br>";

$sql = "SELECT id, nome, marca, anno, posizione, descrizione FROM oggetto ";
//var_dump($sql) . "<br>";
$result = $conn->query($sql);


if ($result) {

    // output data of each row
    while($row = $result->fetch_row()) {
        $retun_value = "";
        foreach ($row as $value)
            $retun_value .= $value . " ";
        echo $retun_value . "<br>";
    }
}
else {
    echo "error: " . $conn->error;
    echo "0 results";
}

$conn->close();

