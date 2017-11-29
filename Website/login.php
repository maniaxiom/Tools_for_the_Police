<?php

include 'constants.php';
	
	$username = $password = "";
	if($_SERVER["REQUEST_METHOD"]=="POST"){
		$username = test_input($_POST['username']);
		$pass = test_input($_POST['password']);
		$password=md5($pass);
		
		$sql="SELECT * FROM login WHERE username = '$username' and password = '$password'";	
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
		    while($row = $result->fetch_assoc()) {
		    	echo $row['email'];
		    	session_start();
				$_SESSION['login_user']= $username;
				header("Location: new.php");
		        echo "Welcome ".$_SESSION['login_user']."<br>";
		        echo "<script type='text/javascript'>show();</script>";
		    }
		} else {
		    echo "Wrong username/password combination.";
		}
	}
	
	$db->close();

	function test_input($data){
		$data=trim($data);
		$data=stripcslashes($data);
		$data=htmlspecialchars($data);
		return $data;
	}
?>