<?php
	require_once '../functions.php';

	logger("************* Started ban task *************");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$banned = sanitizeString($data->banned);
	logger("username = " . $username . " ... bannedset = " . $banned);


	$query = "UPDATE users SET banned='$banned' WHERE username='$username'";

	$response = queryMySQL($query);

	logger($response);

	echo "Success";
?>
