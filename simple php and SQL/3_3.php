<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 3.3</title>
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


echo "<table style='width: 550px; text-align: left'>";
echo    "<tr><th>decade:</th>
             <th>female count:</th>
             <th>male count:</th></tr>";
$counter = 0;
$year = 1940;
//including decades with no employee from
while($year < 1980){
    echo "<tr";
    if ($counter++ % 2 == 0) echo " style='background-color:#e1e1d0;'";
    echo "><th>$year's:</th><th>";
    echo count_emp($year, $year+10, "F", $mysqli);
    echo "</th><th>";
    echo count_emp($year, $year+10, "M", $mysqli);
    echo "</th></tr>";
    $year = $year + 10;
}
echo "</table>";
//closing MySQL connection
$mysqli->close();

function count_emp($decade_staret, $decade_end, $gender, $mysqli){
    $query = "SELECT COUNT(*) FROM employees
              WHERE EXTRACT(YEAR FROM employees.birth_date) >= $decade_staret AND
	                EXTRACT(YEAR FROM employees.birth_date) < $decade_end AND
                    employees.gender = '$gender'; ";
    return implode(mysqli_fetch_assoc(mysqli_query($mysqli, $query)));
}

?>

</body>
</html>
