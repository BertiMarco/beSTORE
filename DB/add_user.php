<!DOCTYPE HTML>
<html>

<head>
    <style>
        .error {color: #FF0000;}
    </style>
</head>

<body>
<!-- Bootstrap -->
<link href="bootsrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootsrap/js/bootstrap.min.js"></script>
<?php

session_start();

if(!isset($_SESSION['login_user'])){
    header("location: index.php");
}

$servername = "localhost";
$username = "xsigomft_bestore";
$password = "bertistore;2019";
$dbname = "xsigomft_bestore";

$imeiErr = $nameErr = $cognomeErr = $globalErr ="";
$imei = $name = $cognome = "";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
//echo "Connessione riuscita</br>";

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['imei'])) {
        $imeiErr = "Richiesto";
    }
    else {
        $imei = test_input($_POST['imei']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['name'])) {
        $nameErr = "Richiesto";
    }
    else {
        $name = test_input($_POST['name']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['surname'])) {
        $cognomeErr = "Richiesto";
    }
    else {
        $cognome = test_input($_POST['surname']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST" && $imei != "" && $name != "" && $cognome != "") {

    $sql = 'SELECT * FROM utente WHERE imei = ?';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $imei);
    $stmt->execute();
    $stmt->fetch();
    $rows = $stmt->num_rows;
    $stmt->close();
    if($rows > 0)
        $imeiErr = "imei già utilizzato";
    else {
        //fare INSERT
        $sql = 'INSERT INTO utente(nome, cognome, imei) VALUES(?, ?, ?)';

        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sss", $name, $cognome, $imei );
        if($stmt->execute() == TRUE) {
            $stmt->close();
            $conn->close();
            header('Location: test.html');
        }
        else {
            echo "NON VA UN CAZZO DI NIENTE </br>";
            echo $stmt->error;
        }
    }
}

else
    $globalErr = "ERRORE: Controlla campi obbligatori";

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>
<div class="col-xl-3 col-sm-auto">
    <h2>Aggiungi utente</h2>
    <p><span class="error">* campi obbligatori</span></p>
    <div class="form-group">
        <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
            IMEI: <input type="text" class="form-control" name="imei" value="<?php echo $imei;?>">
            <span class="error">*<?php echo $imeiErr;?></span>
            <br><br>
            Nome: <input type="text" class="form-control" name="name" value="<?php echo $name;?>">
            <span class="error">*<?php echo $nameErr;?></span>
            <br><br>
            Cognome: <input type="text" class="form-control" name="surname" value="<?php echo $cognome;?>">
            <span class="error">*<?php echo $cognomeErr;?></span>
            <br><br>

            <input type="submit" class="btn btn-primary">
            <span class="error"><?php echo $globalErr;?></span>
        </form>

    </div>

</div>

</body>

</html>