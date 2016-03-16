<?php
	require_once '../functions.php';

	logger("************* Started lock task *************");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$locked = sanitizeString($data->locked);
	logger("username = " . $username . " ... lockedset = " . $locked);

	$query = "UPDATE users SET locked='$locked' WHERE username='$username'";

	$response = queryMySQL($query);

	logger($response);

	echo "Success";
?>
