<?php
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$plan_id = $_GET['plan_id'];

$sql = "SELECT e.ID, e.ExerciseName, e.BodyPart, e.ExerciseDescription, e.imageURL 
        FROM exercise_plan_exercises pe 
        JOIN exercise e ON pe.exercise_id = e.ID 
        WHERE pe.plan_id = $plan_id";

$result = $conn->query($sql);

$exercises = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $exercises[] = $row;
    }
}

echo json_encode($exercises);

$conn->close();
?>
