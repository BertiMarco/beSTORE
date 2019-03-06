<?php
/**
 * Created by PhpStorm.
 * User: marco
 * Date: 11/02/19
 * Time: 20.17
 */

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

$sql = "SELECT p.evento, o.id, o.nome FROM prestito p JOIN oggetto o ON o.id = p.id_oggetto
        WHERE restituito_il IS NULL ";
//var_dump($sql) . "<br>";
$result = $conn->query($sql);


if ($result) {
    $result_string = "ok.";
    // output data of each row
    while($row = $result->fetch_row()) {
        $return_value = "";
        foreach ($row as $value)
            $return_value .= $value . ",";
        $result_string .= $return_value . ".";
        //array_push($result_array, $return_value);
    }
    echo $result_string;
}
else {
    echo "error: " . $conn->error;
    echo "0 results";
}

$conn->close();

?>