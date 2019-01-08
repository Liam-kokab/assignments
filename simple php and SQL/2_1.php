<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 2.1</title>
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

$key_first_name = "first_name";
$key_last_name = "last_name";

//getting first name and last name, order by last name then first name,
//assignment asked only to order by last name, but I included the second part
$query = "SELECT $key_first_name, $key_last_name FROM employees ORDER by $key_last_name, $key_first_name";
$result = mysqli_query($mysqli, $query, MYSQLI_USE_RESULT); // run the query and assign the result to $result

echo "<table style='width:400px;text-align: left'>";
echo    "<tr>
            <th style='width:200px'>Firstname:</th>
            <th style='width:200px'>Lastname:</th> 
        </tr>";
$counter = 0;
foreach ($result as $employee){
    echo "<tr";
    //just a bit of nice looking, every other employee will have gray background
    if($counter++%2 == 0) echo " style='background-color:#e1e1d0;'";
    echo  "><th>$employee[$key_first_name]</th>
            <th>$employee[$key_last_name]</th>
          </tr>";
}

//closing MySQL connection
$mysqli->close();
?>

</body>
</html>
