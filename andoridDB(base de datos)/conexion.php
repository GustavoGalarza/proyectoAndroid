<?php

$hostname = "localhost";
$database = "ventas";
$username = "root";
$password = "";

$conexion = new mysqli($hostname, $username, $password, $database);

if ($conexion->connect_errno) {
    die("Error en la conexión a la base de datos: " . $conexion->connect_error);
}

?>
