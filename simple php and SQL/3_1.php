<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 3.1</title>
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
$result = mysqli_query($mysqli, $query);

echo "<table style='width: 400px; text-align: left'>";
echo    "<tr><th>Table names:</th>
             <th>Attribute names:</th></tr>";
$counter = 0;
foreach ($result as $table_name){
    $table_name = implode($table_name);
    $query = "DESCRIBE $table_name";
    $attributes =  mysqli_query($mysqli, $query);
    foreach ($attributes as $attribute_name) {
        echo "<tr";
        //every other item name will have gray background to assist reading
        if ($counter++ % 2 == 0) echo " style='background-color:#e1e1d0;'";
        echo "><th>$table_name</th><th>";
        echo $attribute_name['Field'];
        echo "</th></tr>";
    }
    echo "<tr><th style='color: white'>.</th></tr>";// space
    $counter = 0;
}

//closing MySQL connection
$mysqli->close();
?>

</body>
</html>
