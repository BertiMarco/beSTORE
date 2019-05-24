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

require 'database.php';

$nameErr = $idErr = $marcaErr = $annoErr = $lunghezzaErr = $posizioneErr = $pwdErr = $globalErr = "";
$nome = $id = $marca = $anno = $lunghezza = $posizione = $descrizione = "";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
//echo "Connessione riuscita</br>";

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['id'])) {
        $idErr = "Richiesto";
    }
    else {
        $id = test_input($_POST['id']);
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
    if(empty($_POST['brand'])) {
        $marca = "";
    }
    else {
        $marca = test_input($_POST['brand']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['year'])) {
        $anno = "";
    }
    else {
        $anno = test_input($_POST['year']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['length'])) {
        $lunghezza = "";
    }
    else {
        $lunghezza = test_input($_POST['length']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['location'])) {
        $posizioneErr = "Richiesto";
    }
    else {
        $posizione = test_input($_POST['location']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    if(empty($_POST['description'])) {
        $descrizione = "";
    }
    else {
        $descrizione = test_input($_POST['description']);
    }
}

if($_SERVER["REQUEST_METHOD"] == "POST" && $id != "" && $nome != "" && $posizione != "") {

    $sql = 'SELECT * FROM oggetto WHERE id = ?';
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    $stmt->execute();
    while ($stmt->fetch());
    $rows = $stmt->num_rows;
    $stmt->close();
    if($rows > 0)
        $idErr = "ID già utilizzato";
    else {
        //fare INSERT
        $sql = 'INSERT INTO oggetto(id, nome, marca, anno, lunghezza, posizione, descrizione)
                VALUES(?, ?, ?, ?, ?, ?, ?)';

        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sssssss", $id, $nome, set_empty_to_null($marca), set_empty_to_null($anno), set_empty_to_null($lunghezza), set_empty_to_null($posizione), set_empty_to_null($descrizione));
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

function set_empty_to_null($data) {
    if(empty($data))
        return NULL;
    else
        return $data;
}

?>
<div class="col-xl-4 col-sm-auto">
    <h2>Aggiungi oggetto in magazzino</h2>
    <p><span class="error">* campi obbligatori</span></p>
    <div class="form-group">
        <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
            ID: <input type="text" class="form-control" name="id" value="<?php echo $id;?>">
            <span class="error">*
            <?php echo $idErr;?></span>
            <br>
            Nome: <input type="text" class="form-control" name="name" value="<?php echo $nome;?>">
            <span class="error">*
            <?php echo $nameErr;?></span>
            <br>
            Marca: <input type="text" class="form-control" name="brand" value="<?php echo $marca;?>">
            <br>
            Anno: <input type="number" class="form-control" name="year" value="<?php echo $anno;?>">
            <br>
            Lunghezza: <input type="number" class="form-control" name="length" value="<?php echo $lunghezza;?>">
            <br>
            Posizione: <input type="text" class="form-control" name="location" value="<?php echo $posizione;?>">
            <span class="error">*
            <?php echo $posizioneErr;?></span>
            <br>
            Descrizione: <textarea name="description" rows="5" cols="40" class="form-control" value="<?php echo $descrizione;?>"></textarea>
            <br><br>

            <input type="submit" class="btn btn-primary">
            <span class="error"><?php echo $globalErr;?></span>
        </form>
    </div>
</div>

</body>

</html>