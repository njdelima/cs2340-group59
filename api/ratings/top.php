<?php
	require_once '../functions.php';

	logger("Started top ratings fetch");

	$data = json_decode(file_get_contents("php://input"));

	logger(print_r($data, TRUE));

	$major = sanitizeString($data->major);

	logger("major=" . $major);

	$query  = "SELECT users.major, ratings.movie_id, ratings.RATING ";
	$query .= "FROM users ";
	$query .= "INNER JOIN ratings ";
	$query .= "ON users.username = ratings.username ";

	if ($major == "All" || $major == "all") {
	} else {
		$query .= "WHERE users.major='$major'";
	}

	logger("query = " . $query);

	$result = queryMySQL($query);

	if (!$result) {
		die($connection -> error);
	}

	logger(print_r($result, TRUE));

	$num_ratings = $result->num_rows;

	$movies = [];

	for ($i = 0; $i < $num_ratings; $i++) {
		$result -> data_seek($i);
		$row = $result -> fetch_array(MYSQLI_ASSOC);

		logger(print_r($row, TRUE));

		$movie_id = $row['movie_id'];
		$rating = $row['RATING'];

		logger("movie_id = " . $movie_id);
		logger("rating = " . $rating);

		$title = getTitle($movie_id);
		logger("title= " . $title);

		if (!array_key_exists($movie_id, $movies)) {
			$movies[$movie_id]['total_rating'] = $rating;
			$movies[$movie_id]['rating_count'] = 1;
			$movies[$movie_id]['title'] = $title;
		} else {
			$movies[$movie_id]['total_rating'] += $rating;
			$movies[$movie_id]['rating_count']++;
		}
	}

	logger(print_r($movies, TRUE));

	$returnValue = json_encode($movies);

	logger("RETURN VALUE = " . print_r($returnValue, TRUE));

	echo $returnValue;

	function getTitle($id) {
		$url = "http://www.omdbapi.com/?i=" . $id;
		logger("url = " . $url);

		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

		$result = json_decode(curl_exec($ch));

		logger("RESULT OF CURL = " . print_r($result, TRUE));

		return $result -> Title;
	}
?>
