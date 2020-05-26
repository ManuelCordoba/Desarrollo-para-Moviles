<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos

$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
//2. Tomar los campos provenientes de la tabla

$usuario=$_GET['user'];
$consulta="SELECT * FROM $bd.usuario where username='$usuario';";
$resultado = mysqli_query($con, $consulta);
while($fila = mysqli_fetch_row($resultado))
 {
echo json_encode($fila);
}
mysqli_close($con);
?>