<?php
//1. Invoca los datos de conexión
include_once("conexion.php");

//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Fallo la conexion');
mysqli_set_charset($con,"utf8");

//3. Tomar los campos provenientes del Formulario
$Nom = $_GET['nomusu'];
$Usu = $_GET['username'];
$Email = $_GET['emausu'];
$Pass = $_GET['password'];
$Ape = $_GET['apeusu'];
$ID = $_GET['rol'];

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.usuario (username,email_usu,password,nombre_usu,apellido_usu,id_rol)
VALUES ('$Usu','$Email','$Pass','$Nom','$Ape',$ID);";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>