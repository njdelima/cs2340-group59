<?php
	require_once '../functions.php';

	logger("Started update");
	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$new_username = sanitizeString($data->new_username);
	$new_password = sanitizeString($data->new_password);
	$new_first_name = sanitizeString($data->new_first_name);
	$new_last_name = sanitizeString($data->new_last_name);
	$new_major = sanitizeString($data->new_major);

	$query = "UPDATE users SET ";
	$query = $query . "username='$new_username', password='$new_password', first_name='$new_first_name', last_name='$new_last_name', major='$new_major' ";
	$query = $query . "WHERE username='$username'";

	logger("QUERY = " . $query);

	$result = queryMySQL($query);

	logger(print_r($result,TRUE));

	echo "Success!";
?>
