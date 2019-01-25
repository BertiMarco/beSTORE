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

$nameErr = $idErr = $marcaErr = $annoErr = $lunghezzaErr = $posizioneErr = $pwdErr = "";
$pwd = $nome = $id = $marca = $anno = $lunghezza = $posizione = $descrizione = "";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['id'])) {
        $idErr = "Richiesto";
    }
    else {
        $id = test_input($_POST['id']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST" && $id != "") {

    $sql = 'SELECT * FROM oggetto WHERE id = ?';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    $stmt->execute();
    $stmt->fetch();
    $rows = $stmt->num_rows;
    $stmt->close();
    if($rows == 0)
        $idErr = "id oggetto non presente";
    else {

        $sql = 'DELETE FROM oggetto WHERE id = ?';
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $id);
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

<div class="col-xl-4 col-sm-auto">
    <h2>Rimuovi oggetto dal magazzino</h2>
    <p><span class="error">* campi obbligatori</span></p>

    <div class="form-group">

        <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
            ID: <input type="text" class="form-control" name="id" value="<?php echo $id;?>">
            <span class="error">*
            <?php echo $idErr;?></span>
            <br><br>
            <input type="submit" class="btn btn-primary">
        </form>
    </div>
</div>

</body>

<!-- Bootstrap -->
<link href="bootsrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootsrap/js/bootstrap.min.js"></script>
</html>
