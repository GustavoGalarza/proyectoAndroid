<?php

include('conexion.php');

$rut = $_GET['rut'];

$consulta = "SELECT * FROM proveedores WHERE rut = '$rut'";
$resultado = $conexion->query($consulta);

while ($fila = $resultado->fetch_array()) {
    $proveedor[] = array_map('utf8_encode', $fila);
}

echo json_encode($proveedor);
$resultado->close();

?>
