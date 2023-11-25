<?php

include('conexion.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
    $rut = isset($_POST['rut']) ? $_POST['rut'] : null;

    if ($rut !== null) {
        $consulta = "DELETE FROM proveedores WHERE rut=?";

        $stmt = $conexion->prepare($consulta);

        $stmt->bind_param("s", $rut);

        $stmt->execute();

        $stmt->close();
        $conexion->close();

        echo "Operación exitosa";
    } else {
        echo "Falta el parámetro obligatorio";
    }
} else {
    echo "Método no permitido";
}

?>
