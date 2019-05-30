<?php

require 'database.php';

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
echo "Connessione riuscita <br>";

$sql = "SELECT p.evento, o.id, o.nome FROM prestito p JOIN oggetto o ON o.id = p.id_oggetto
        WHERE restituito_il IS NULL";
//var_dump($sql) . "<br>";
$result = $conn->query($sql);


if ($result) {
    $result_array = array();
    // output data of each row
    while($row = $result->fetch_row()) {
        $return_value = "";
        foreach ($row as $value)
            $return_value .= $value . ", ";
        array_push($result_array, $return_value);
    }
    echo json_encode($result_array);
}
else {
    echo "error: " . $conn->error;
    echo "0 results";
}

$conn->close();

