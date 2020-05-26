<?php
//1. Invoca los datos de conexión
include_once("conexion.php");

//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Fallo la conexion');
mysqli_set_charset($con,"utf8");

//3. Tomar los campos provenientes del Formulario
$id_per=$_GET['id_per'];
$id_rol=$_GET['id_rol'];
$inserta="INSERT into $bd.permiso_de_rol (id_rol,id_permiso) VALUES ($id_rol,$id_per);";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>