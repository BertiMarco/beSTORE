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

    require 'database.php';
    
    $imeiErr = "";
    $imei = "";
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 

    if($_SERVER["REQUEST_METHOD"] == "POST") {
        if(empty($_POST['imei'])) {
            $imeiErr = "Richiesto";
        }
        else {
            $imei = test_input($_POST['imei']);
        }
    }

    if($_SERVER["REQUEST_METHOD"] == "POST" && $imei != "") {

        $sql = 'SELECT * FROM utente WHERE imei = ?';
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $imei);
        $stmt->execute();
        $stmt->fetch();
        $rows = $stmt->num_rows;
        $stmt->close();
        if($rows == 0)
            $imeiErr = "imei non presente";
        else {
            $sql = 'DELETE FROM utente WHERE imei = ?';
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("s", $imei);
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

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
        }
?>

<h2>Rimuovi utente</h2>
<p><span class="error">* campi obbligatori</span></p>
    <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
        ID: <input type="text" name="imei" value="<?php echo $imei;?>">
        <span class="error">*<?php echo $imeiErr;?></span>
        <br><br>
    <input type="submit">
    </form>