<?php
	session_start();
?>

<?php
include 'constants.php';
	
	$sql="SELECT * FROM login";	
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
	    while($row = $result->fetch_assoc()) {
	    	echo $row['username'];
	    	echo "\n";
	    	echo $row['email'];
	    	echo "\n";
	    }
	} else {
	    echo "Wrong username/password combination.";
	}

	$db->close();
?>