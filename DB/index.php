<h1>Cacca</h1>
<p>Ciao</p>
<p style="color:red;">
<?php
$servername = "localhost";
$username = "xsigomft_bestore";
$password = "bertistore;2019";
$dbname = "xsigomft_bestore";

// function cripta_risultati($testo_in_chiaro){
//     global $key_cookie_id;
//     $ivlen = openssl_cipher_iv_length($cipher="AES-256-CBC");
//     $iv = openssl_random_pseudo_bytes($ivlen);
//     $ciphertext_raw = openssl_encrypt($testo_in_chiaro, $cipher, $key_cookie_id, $options=OPENSSL_RAW_DATA, $iv);
//     $hmac = hash_hmac('sha256', $ciphertext_raw, $key_cookie_id, $as_binary=true);
//     return base64_encode( $iv.$hmac.$ciphertext_raw );
//   }

// $stringa = $_POST["query"];
// $stringa;


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
echo "Connessione riuscita\n";

$sql = "UPDATE oggetto SET nome = 'microfono' WHERE id = 'bestore_3'";
echo "</br>";
var_dump($sql);
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
</p>