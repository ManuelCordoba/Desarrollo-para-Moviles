<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
$id_usuario=$_GET['id_usuario'];
$Nom = $_GET['nomusu'];
$Usu = $_GET['username'];
$Email = $_GET['emausu'];
$Pass = $_GET['password'];
$Ape = $_GET['apeusu'];
$ID = $_GET['rol'];
$inserta="update $bd.usuario set username='$Usu',email_usu='$Email',password='$Pass',nombre_usu='$Nom',apellido_usu='$Ape',id_rol=$ID where id_usu=$id_usuario;";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>