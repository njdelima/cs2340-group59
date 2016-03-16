<?php
	require_once '../functions.php';

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$password = sanitizeString($data->password);

	logger("Username = " . $username);
	logger("Password = " . $password);

	if ($username !== "" && $password) {
		$response = queryMySQL("INSERT INTO users (username, password) VALUES ('$username', '$password')");
		logger($response);
		echo "Success!";
	}
?>

