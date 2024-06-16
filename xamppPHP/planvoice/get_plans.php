<?php
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT ep.id as plan_id, ep.plan_name, e.ID as exercise_id, e.ExerciseName, e.BodyPart, e.ExerciseDescription, e.imageURL 
        FROM exercise_plans ep
        LEFT JOIN exercise_plan_exercises epe ON ep.id = epe.plan_id
        LEFT JOIN exercise e ON epe.exercise_id = e.ID";

$result = $conn->query($sql);

$plans = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $plan_id = $row["plan_id"];
        if (!isset($plans[$plan_id])) {
            $plans[$plan_id] = array(
                "planName" => $row["plan_name"],
                "exercises" => array()
            );
        }
        $exercise = array(
            "ID" => $row["exercise_id"],
            "ExerciseName" => $row["ExerciseName"],
            "BodyPart" => $row["BodyPart"],
            "ExerciseDescription" => $row["ExerciseDescription"],
            "imageURL" => $row["imageURL"]
        );
        $plans[$plan_id]["exercises"][] = $exercise;
    }
}

echo json_encode(array_values($plans));

$conn->close();
?>
