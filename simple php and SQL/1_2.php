<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 1.2</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>

<?php
if(isset($_POST['len_chosen'])){
    $eng = 'English' == $_POST['language'];
    $language_set = true;
}elseif(isset($_GET["language"])){
    $eng = 'English' == htmlspecialchars($_GET["language"]);
}else $eng = true; // fallback

//English
$users_name_text =          ($eng)? "Name"                                      : "Navn";
$users_name_error_text =    ($eng)? "Name, field is empty "                     : "Navne felt er tom";
$year_of_birth_text =       ($eng)? "Year of birth"                             : "Fødsels år";
$year_of_birth_error_text = ($eng)? "Year of birth, field is empty"             : "fødsels år felt er tom";
$current_age_text =         ($eng)? "current age"                               : "alder";
$current_age_error_text =   ($eng)? "Current age, field is empty "              : "alder felt er tom";
$year_conflict_error_text = ($eng)? "Year of birth and current age don't match" : "fødsels år og alder passer ikke sammen";
$good_job_text =            ($eng)? "Good Job!"                                 : "Bra jobbet";
$submit_text =              ($eng)? "Submit"                                    : "sende inn";
//reset works in norwegian as well ;)
/**
 if(eng{
    ... eng stuff
 }else{
    ... nor stuff
 }
 would work as well,
 */

$done = false;
//runs on Submit button.
$usersName = "";
$year_of_birth = "";
$current_age = "";
if(isset($_POST['Submit'])) {
    $usersName = $_POST['user_name'];
    $year_of_birth = $_POST['year_of_birth'];
    $current_age = $_POST['current_age'];

    $this_year = (int)date("Y");
    $error = "";
    $have_year = false;
    if(strlen($usersName) < 1) $error .= "$users_name_error_text</br>";
    if(strlen($year_of_birth) < 1 && !is_numeric($year_of_birth)) $error .= "$year_of_birth_error_text</br>";
    else $have_year = true;
    if(strlen($current_age) < 1 && !is_numeric($current_age)) $error .= "$current_age_error_text</br>";
    else if($have_year && !($year_of_birth > $current_age &&($this_year - $year_of_birth == $current_age || $this_year - $year_of_birth == $current_age + 1)))
        $error .= "$year_conflict_error_text</br>";



    //if any error massage is exist, show it.
    if(strlen($error) > 0){
        echo "<p style='color:red;'>$error</p>";
    }else{//if no error, we are done.
        $done = true;
        echo "<p style='color:green;'>$good_job_text</p></br>";
    }
}

//shows the form if we are not done, on first load we are not done.
if(!$done){
    $current_language = ($eng) ? 'English' : 'Norwegian';
    echo "<form action='?language=$current_language' method='post'>

    <table>
        <tr>
            <td width='150px'>$users_name_text: </td>
            <td><input type='text' name='user_name' value='$usersName'/></td>
        </tr>
        <tr>
            <td width='150px'>$year_of_birth_text: </td>
            <td><input type='number' name='year_of_birth' value='$year_of_birth'/></td>
        </tr>

        <tr>
            <td width='150px'>$current_age_text: </td>
            <td><input type='number' name='current_age' value='$current_age'/></td>
        </tr>
        <tr>
            <td width='150px'></td>
            <td><input width='50px' type='reset' value='Reset'/>
                <input  type='submit' name='Submit' value='$submit_text'/></td>
        </tr>
    </table>
</form>";
}
?>

</body>
</html>
