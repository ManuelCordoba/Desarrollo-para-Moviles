<?php
//1. Invoca los datos de conexión
include_once("conexion.php");

//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Fallo la conexion');
mysqli_set_charset($con,"utf8");

//3. Tomar los campos provenientes del Formulario
$idU = $_GET['usuario'];
$dateI = $_GET['fechaI'];
$timeI = $_GET['tiempoI'];
$dateO = $_GET['fechaO'];
$timeO = $_GET['tiempoO'];

//4. Insertar campos en la Base de Datos
$inserta = "update $bd.hist_sesion set out_f_sesion='$dateO',out_h_sesion='$timeO' where in_f_sesion='$dateI' and in_h_sesion='$timeI' and id_usu=$idU;";
echo $inserta;
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);

//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
?>