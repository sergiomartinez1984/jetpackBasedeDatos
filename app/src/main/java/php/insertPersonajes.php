

<?php

$server = "localhost";
$user = "root";
$pass = "clave";
$bd = "BDsergioMartinez";

//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Algo ha fallado en la conexion a la base de datos");

//generamos la consulta
$id = $_GET["id"];
$nombre = $_GET["nombre"];
$compania = $_GET["compania"];
$imagen = $_GET["imagen"];

  $sql = "INSERT INTO Personajes (id, nombre, compania, imagen) VALUES ('$id', '$nombre', '$compania', '$imagen')";
echo $sql;

mysqli_set_charset($conexion, "utf8"); //formato de datos utf8
if (mysqli_query($conexion, $sql)) {
      echo "Nuevo personaje añadido correctamente";
} else {
      echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");




?>
