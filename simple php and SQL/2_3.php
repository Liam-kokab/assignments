<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 2.3</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>


<?php

$mysqli = new mysqli('localhost', 'root', '', 'employees');

//in case of connection error
if ($mysqli->connect_error) {
    die('Connect Error (' . $mysqli->connect_errno . ') '
        . $mysqli->connect_error);
}
//it's good to have, but the assignment doesn't ask.
//echo '<p>Connection OK '. $mysqli->host_info.'</p>';
//echo '<p>Server '.$mysqli->server_info.'</p>';

$query = "SHOW TABLES";
$result = mysqli_query($mysqli, $query, MYSQLI_USE_RESULT);

echo "<table style='text-align: left'>";
echo    "<tr><th>Table names:</th></tr>";
$counter = 0;
foreach ($result as $table_name){
    echo "<tr";
    //every other table name will have gray background to assist reading
    if($counter++%2 == 0) echo " style='background-color:#e1e1d0;'";
    echo  "><th>";
    echo implode($table_name);
    echo"</th></tr>";
}

//closing MySQL connection
$mysqli->close();
?>

</body>
</html>
