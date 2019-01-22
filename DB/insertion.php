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

    //$pwd = test_input($_POST['pwd']);   
    if($_SERVER["REQUEST_METHOD"] == "POST") {
        $pwd = test_input($_POST['pwd']);
        if($pwd !== "password") {
            $pwdErr = "Password errata!";
        }
        else {
            $sql = 'SELECT id FROM oggetto WHERE id = ?';
            $stmt = $conn->prepare($sql);
            $stmt->bind_param($id);
            $stmt->execute();
            $stmt->fetch();
            if($stmt->num_rows >=1) {
                //id già presente.
                $idErr = "id già utilizzato";
            }
            else {
                $stmt->close();
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
    }
    
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
<h2>Aggiungi oggetto in magazzino</h2>
<p><span class="error">* campi obbligatori</span></p>
    <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
        ID: <input type="text" name="id" value="<?php echo $id;?>">
        <span class="error">*
            <?php echo $idErr;?></span>
        <br><br>
        Nome: <input type="text" name="name" value="<?php echo $nome;?>">
        <span class="error">*
            <?php echo $nameErr;?></span>
        <br><br>
        Marca: <input type="text" name="brand" value="<?php echo $marca;?>">
        <br><br>
        Anno: <input type="number" name="year" value="<?php echo $anno;?>">
        <br><br>
        Lunghezza: <input type="number" name="length" value="<?php echo $lunghezza;?>">
        <br><br>
        Posizione: <input type="text" name="location" value="<?php echo $posizione;?>">
        <span class="error">*
            <?php echo $posizioneErr;?></span>
        <br><br>
        Descrizione: <textarea name="description" rows="5" cols="40" value="<?php echo $descrizione;?>"></textarea>
        <br><br>
        PASSWORD: <input type="password" name="pwd">
        <span class="error">* <?php echo $pwdErr;?></span>

        <input type="submit">
    </form>

</body>

</html>