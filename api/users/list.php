<?php
	require_once '../functions.php';

	logger("Started list");

	$result = queryMySQL("SELECT username, password, major, first_name, last_name, banned, locked, admin FROM users");

	logger(print_r($result,TRUE));

	$num_rows = $result -> num_rows;
	$returnVal = array();

	for ($i = 0; $i < $num_rows; $i++) {
		$result -> data_seek($i);
		$row = $result -> fetch_array(MYSQLI_ASSOC);
		$returnVal[] = $row;
	}

	logger(print_r(json_encode($returnVal), TRUE));

	echo json_encode($returnVal);
?>
