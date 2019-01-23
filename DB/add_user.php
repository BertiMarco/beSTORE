<!DOCTYPE HTML>
<html>

<head>
    <style>
        .error {color: #FF0000;}
    </style>
</head>

<body>
<?php

    session_start();

    if(!isset($_SESSION['login_user'])){
        header("location: index.php");
    }

    $servername = "localhost";
    $username = "xsigomft_bestore";
    $password = "bertistore;2019";
    $dbname = "xsigomft_bestore";
    
    $imeiErr = $nomeErr = $cognomeErr = $globalErr ="";
    $imei = $nome = $cognome = "";
    
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
            $nome = test_input($_POST['name']);
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

    if($_SERVER["REQUEST_METHOD"] == "POST" && $imei != "" && $nome != "" && $cognome != "") {

        $sql = 'SELECT * FROM utente WHERE imei = ?';
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $imei);
        $stmt->execute();
        $stmt->fetch();
        $rows = $stmt->num_rows;
        $stmt->close();
        if($rows > 0)
            $idErr = "imei già utilizzato";
        else {
            //fare INSERT
            $sql = 'INSERT INTO utente(nome, cognome, imei) VALUES(?, ?, ?)';
    
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("sss", $nome, $cognome, $imei );
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
<h2>Aggiungi utente</h2>
<p><span class="error">* campi obbligatori</span></p>
    <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
        IMEI: <input type="text" name="imei" value="<?php echo $id;?>">
        <span class="error">*<?php echo $imeiErr;?></span>
        <br><br>
        Nome: <input type="text" name="name" value="<?php echo $nome;?>">
        <span class="error">*<?php echo $nameErr;?></span>
        <br><br>
        Cognome: <input type="text" name="surname" value="<?php echo $marca;?>">
        <span class="error">*<?php echo $cognomeErr;?></span>
        <br><br>

        <input type="submit">
        <span class="error"><?php echo $globalErr;?></span>
    </form>

</body>

</html>