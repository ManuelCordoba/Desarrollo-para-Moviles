<?php
//1. Invoca los datos de conexión
include_once("conexion.php");

//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Fallo la conexion');
mysqli_set_charset($con,"utf8");

//3. Tomar los campos provenientes del Formulario
$idU = $_GET['usuario'];
$idS = $_GET['servicio'];
$date = $_GET['fecha'];
$time = $_GET['tiempo'];

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.reserva (id_ser,id_usu,date,time,estado)
VALUES ($idS,$idU,'$date','$time',1);";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>