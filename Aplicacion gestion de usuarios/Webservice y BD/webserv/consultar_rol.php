<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
$Rol = $_GET['id_rol'];
$consulta="SELECT id_permiso FROM $bd.permiso_de_rol where id_rol=$Rol;";
$resultado = mysqli_query($con, $consulta);

    $json1=array();
		
	while($fila = mysqli_fetch_assoc($resultado))
        {
	 
        array_push($json1,$fila);
	 
	}
	
//Esta instrucción (print), envía la respuesta a la App de antroid.
       
//Aquí se le devuelve a la App de Android.	
  print json_encode($json1); 


mysqli_close($con);

?>