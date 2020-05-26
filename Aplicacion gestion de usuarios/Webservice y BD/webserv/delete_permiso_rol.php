<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
$id_per_rol=$_GET['id_per_rol'];
$inserta="DELETE FROM $bd.permiso_de_rol where id_per_rol=$id_per_rol;";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>