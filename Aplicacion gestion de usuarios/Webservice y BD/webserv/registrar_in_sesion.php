<?php
//1. Invoca los datos de conexión
include_once("conexion.php");

//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Fallo la conexion');
mysqli_set_charset($con,"utf8");

//3. Tomar los campos provenientes del Formulario
$idU = $_GET['usuario'];
$date = $_GET['fecha'];
$time = $_GET['tiempo'];

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.hist_ses (id_usu,in_f_sesion,in_h_sesion)
VALUES ($idU,'$date','$time');";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>