<?php 
//constants.php

define('DBHOST', 'localhost');
define('DBUSER', 'root');
define('DBPASS', 'root');
define('DBNAME', 'wardi');

$conn = new mysqli(DBHOST, DBUSER, DBPASS,DBNAME);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}/*else{
	echo "Connected successfully";
}*/

?>