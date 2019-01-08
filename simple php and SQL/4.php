<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 4</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

</head>
<body>


<?php
$year_ok = true;
$mode_ok = true;
$have_request = false;
if(isset($_POST['Submit'])) {
    $year_ok = control_year($year = $_POST['year']);
    $mode_ok = control_mode($mode = $_POST['mode']);
    $have_request = $year_ok && $mode_ok;
}else {
    $year = "";
    $mode = "empty";
}
echo "<form action='#' method='post'>
        <p class='lan_text'></p>
        <div style='background-color: #d4d4d4; padding: 7px; box-shadow: -3px 5px 10px #515151; margin: 7px '>
            <a>Year:  </a>
            <input style=' width: 110px; ";
//making year input box red in case of fail!
if(!$year_ok) echo " background-color: #FF0000' ";

 echo "' type='number' name='year' value='$year' />
            <a style='margin-left: 20px;'>Mode: </a>
            <select name='mode' style='width: 110px; ";
//making mode drop-down red in case of fail!
 if(!$mode_ok) echo "background-color: #FF0000 ";

 echo " '><option value='empty'></option>
         <option value='average'";
 //set selected option to last selected
 if($mode_ok && $mode == "average")echo " selected";

echo " >average salary</option>
     
     <option value='total'";
//set selected option to last selected
if($mode_ok && $mode == "total")echo " selected";
echo " >total salary</option>
            </select>
            <input  type='submit' name='Submit' value='submit' style='margin-left: 20px; width: 110px'/></td>
        </div>
    </form>";

// no need to remove the form after correct input, in case user wish to make another request.
if(!$have_request){
    echo "<p style='padding-left:10px;'>";
    if(!$year_ok) echo "please insert correct year,";
    if(!$mode_ok) echo " please select a mode";
    echo "</p>";
    return; //under here will only run if we have a good request.
}

$mysqli = new mysqli('localhost', 'root', '', 'employees');

//in case of connection error
if ($mysqli->connect_error) {
    die('Connect Error (' . $mysqli->connect_errno . ') '
        . $mysqli->connect_error);
}

$query = "SELECT salaries.salary,
          EXTRACT(DAY FROM salaries.from_date) AS day,
          EXTRACT(MONTH FROM salaries.from_date) AS month
          FROM salaries WHERE EXTRACT(YEAR FROM salaries.from_date) = $year";
$result = mysqli_query($mysqli, $query);
$salary_sum = 0;
foreach ($result as $res){
    $salary_sum += get_salary_for_days( $res['day'], $res['month'], $year, $res['salary'],true);
}

$query = "SELECT salaries.salary,
          EXTRACT(DAY FROM salaries.to_date) AS day,
          EXTRACT(MONTH FROM salaries.to_date) AS month
          FROM salaries WHERE EXTRACT(YEAR FROM salaries.to_date) = $year";
$result = mysqli_query($mysqli, $query);
foreach ($result as $res){
    $salary_sum += get_salary_for_days(  $res['day'], $res['month'], $year, $res['salary'],false);
}
echo "<p style='padding-left: 10px;'>";
//print if user ask for total
if($mode == 'total') {
    echo "total salary for year $year is: ";
    printf ("%.0f" , $salary_sum);

//print if user ask for average
}elseif($mode == 'average'){
    $query = "SELECT COUNT(DISTINCT salaries.emp_no) FROM salaries 
                WHERE EXTRACT(YEAR FROM salaries.to_date) = $year 
                   || EXTRACT(YEAR FROM salaries.from_date) = $year";
    $count =  implode(mysqli_fetch_assoc(mysqli_query($mysqli, $query)));
    echo "average salary for year $year is: ";
    printf( "%.0f",(($count != 0) ? $salary_sum/$count : 0));
}
echo "</p>";

/**
 * @param $day
 * @param $month
 * @param $year
 * @param $salary
 * @param $star_to_date
 * @return float|int relevant part of salary for this employee
 */
function get_salary_for_days($day, $month, $year, $salary, $star_to_date){
    $days = date("z", mktime(0,0,0,$month,$day,$year))+1;
    return ($star_to_date)? ($salary /365 * $days) : ($salary / 365 * (366 - $days));
}

/**
 * control year input
 * @param $year
 * @return bool
 */
function control_year($year){
    return is_numeric($year) && $year < date("Y") && $year > 1900;
}
/**
 * control mode input
 * @param $mode
 * @return bool
 */
function control_mode($mode){
    return (($mode == 'average') || ($mode == 'total'));
}
?>

</body>
</html>
