<?php
	require_once 'credentials.php';

	$connection = new mysqli($db_hostname, $db_username, $db_password, $db_database);

	if ($connection -> connect_error) {
		die($connection -> connect_error);
	}

	function sanitizeString($var) {
		global $connection;

		$var = strip_tags($var);
		$var = htmlentities($var);
		$var = stripslashes($var);
		return $connection -> real_escape_string($var);
	}

	function queryMySQL($query) {
		global $connection;
		logger("Got connection\n");

		$result = $connection -> query($query);
		logger("ran query!\n");
		if (!$result) {
			logger("no result\n");
			die($connection -> error);
		} else {
			logger("success");
			return $result;
		}
	}

	function logger($message) {
		file_put_contents("log.txt", "\n".$message, FILE_APPEND);
	}
?>
