<!DOCTYPE html>
<html>

<head>
    <title>INF115 - Liam Kokab (lko015) - Compulsory exercise 3 - 1.3</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        .lan_text:after{
            content: 'Please select your preferred language:';
        }
        .lan_text:hover:after{
            content: 'Vennligst velg ønsket språk:';
        }
    </style>
</head>
<body>


<?php
//this page will send you to 1_2, where i have changed the code to take care of the language.
//language control is done 2 ways, just to show that i can do both.
//here I use form with submit and in 1_2.php, I use the url
//I hope you don't mind me using css for the text.
echo "<form action='1_2.php' method='post'>
        <p class='lan_text'></p>
        <div>
            <input type='radio' name='language' value='English' checked>
            <label for='contactChoice1'>English</label>

            <input type='radio' name='language' value='Norwegian'>
            <label for='contactChoice2'>Norwegian</label>
            </br>
            <input  type='submit' name='len_chosen' value='submit'/></td>
        </div>
    </form>";
?>

</body>
</html>
