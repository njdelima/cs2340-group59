<?php
	require_once '../functions.php';

	logger("Started add rating");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$username = sanitizeString($data->username);
	$id = sanitizeString($data->id);
	$rating = sanitizeString($data->rating);

	$query = "INSERT INTO ratings (username, movie_id, rating) VALUES ('$username', '$id', '$rating')";

	$response = queryMySQL($query);

	logger($response);

	echo "Success";
?>
