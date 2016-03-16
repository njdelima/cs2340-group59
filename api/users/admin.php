<?php
	require_once '../functions.php';

	logger("************* Started admin task *************");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$admin = sanitizeString($data->admin);
	logger("username = " . $username . " ... adminset = " . $admin);


	$query = "UPDATE users SET admin='$admin' WHERE username='$username'";

	$response = queryMySQL($query);

	logger($response);

	echo "Success";
?>
