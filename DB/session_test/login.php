<?php
session_start(); // Starting Session
$error=''; // Variable To Store Error Message
if (isset($_POST['submit'])) {
    if (empty($_POST['username']) || empty($_POST['password'])) {
        $error = "Username or Password is invalid";
    }
    else{
            // Define $username and $password
            $username=$_POST['username'];
            $password=$_POST['password'];

            $pwdHash = password_hash($password, PASSWORD_DEFAULT);

            $solution = password_hash("password", PASSWORD_DEFAULT);
            // Establishing Connection with Server by passing server_name, user_id and password as a parameter
            if($username == 'admin' || $pwdHash == $solution ) {
                $_SESSION['login_user'] = $username; // Initializing Session
                header("Location: main_page.php"); // Redirecting To Other Page
            } 
            else {
                $error = "Username or Password is invalid";
        }
    }
}
?>