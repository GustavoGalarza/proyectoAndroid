<?php

include('conexion.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
    $rut = isset($_POST['rut']) ? $_POST['rut'] : null;
    $nombre = isset($_POST['nombre']) ? $_POST['nombre'] : null;
    $direccion_calle = isset($_POST['direccion_calle']) ? $_POST['direccion_calle'] : null;
    $direccion_numero = isset($_POST['direccion_numero']) ? $_POST['direccion_numero'] : null;
    $direccion_comuna = isset($_POST['direccion_comuna']) ? $_POST['direccion_comuna'] : null;
    $direccion_ciudad = isset($_POST['direccion_ciudad']) ? $_POST['direccion_ciudad'] : null;
    $telefono = isset($_POST['telefono']) ? $_POST['telefono'] : null;
    $pagina_web = isset($_POST['pagina_web']) ? $_POST['pagina_web'] : null;

    if ($rut !== null && $nombre !== null) {
        
        $consulta = "UPDATE proveedores 
                     SET nombre=?, direccion_calle=?, direccion_numero=?, direccion_comuna=?, direccion_ciudad=?, telefono=?, pagina_web=?
                     WHERE rut=?";
        
        $stmt = $conexion->prepare($consulta);

        $stmt->bind_param("ssssssss", $nombre, $direccion_calle, $direccion_numero, $direccion_comuna, $direccion_ciudad, $telefono, $pagina_web, $rut);
 
        $stmt->execute();

        $stmt->close();
        $conexion->close();

        echo "Operación exitosa";
    } else {
        echo "Faltan parámetros obligatorios";
    }
} else {
    echo "Método no permitido";
}

?>
