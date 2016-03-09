<?php
	require_once '../functions.php';

	logger("Started fetch for rating");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$id = sanitizeString($data->id);

	logger("movie_id=" . $id);

	$query = "SELECT rating FROM ratings WHERE movie_id='$id'";

	$result = queryMySQL($query);

	if (!$result) {
		die($connection -> error);
	}

	$num_ratings = $result->num_rows;

	$totalRating = 0;

	for ($i = 0; $i < $num_ratings; $i++) {
		$result -> data_seek($i);
		$row = $result -> fetch_array(MYSQLI_ASSOC);
		$rating = $row['rating'];
		$totalRating = $totalRating + $rating;
	}

	$returnValue = json_encode(array("total_rating" => $totalRating, "ratings_count" => $num_ratings));

	logger(print_r($returnValue, TRUE));

	echo $returnValue;
?>
