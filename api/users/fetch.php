<?php
	require_once '../functions.php';

	logger("Started fetch");
	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);

	logger("Username = " . $username);

	$result = queryMySQL("SELECT username, password, major, first_name, last_name FROM users WHERE username='$username'");

	logger(print_r($result,TRUE));

	$result -> data_seek(0);

	$row = $result -> fetch_array(MYSQLI_ASSOC);
	$row = json_encode($row);
	logger(print_r($row, TRUE));
	logger("Final JSON");
	logger(print_r(json_encode($row), TRUE));

	echo $row;
?>
