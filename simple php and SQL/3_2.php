<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 3.2</title>
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

$query = "SELECT dept_manager.dept_no, employees.last_name, employees.first_name, departments.dept_name
          From employees
          INNER JOIN dept_manager ON employees.emp_no = dept_manager.emp_no
          INNER JOIN departments ON departments.dept_no = dept_manager.dept_no";

$result = mysqli_query($mysqli, $query);

$key_first_name = "first_name";
$key_last_name = "last_name";
$key_dept_no = "dept_no";
$key_dept_name = "dept_name";

echo "<table style='width: 550px; text-align: left'>";
echo    "<tr><th>department name:</th>
             <th>manager name:</th>
             <th>employee count:</th></tr>";
$counter = 0;
$last_dept_no = "";
foreach ($result as $department){
    if($last_dept_no == $department[$key_dept_no]) continue;
    $last_dept_no = $department[$key_dept_no];

    echo "<tr";
    if ($counter++ % 2 == 0) echo " style='background-color:#e1e1d0;'";
    echo "><th>$department[$key_dept_name]</th>";
    echo "<th>$department[$key_first_name] $department[$key_last_name]</th>";
    echo "<th style='text-align: right'>";
    echo count_emp($department[$key_dept_no],$mysqli);
    echo "</th></tr>";
}
echo "</table>";
//closing MySQL connection
$mysqli->close();

function count_emp($dep_no, $mysqli){
    $query = "SELECT COUNT(*) FROM 
              (employees INNER JOIN dept_emp ON employees.emp_no = dept_emp.emp_no)
              WHERE dept_emp.dept_no = '$dep_no' ";
    return implode(mysqli_fetch_assoc(mysqli_query($mysqli, $query)));
}

?>

</body>
</html>
