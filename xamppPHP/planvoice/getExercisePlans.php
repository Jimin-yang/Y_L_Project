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

// SQL query to get exercise plans and their related exercises
$sql = "
    SELECT ep.plan_name, e.ID, e.ExerciseName, e.BodyPart, e.ExerciseDescription, e.imageURL
    FROM exercise_plans ep
    JOIN exercise_plan_exercises epe ON ep.id = epe.plan_id
    JOIN exercise e ON epe.exercise_id = e.ID
";
$result = $conn->query($sql);

if (!$result) {
    die("Query failed: " . $conn->error);
}

$plans = [];
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $plans[$row['plan_name']][] = [
            'ID' => $row['ID'],
            'ExerciseName' => $row['ExerciseName'],
            'BodyPart' => $row['BodyPart'],
            'ExerciseDescription' => $row['ExerciseDescription'],
            'imageURL' => $row['imageURL']
        ];
    }
}

$response = [];
foreach ($plans as $planName => $exercises) {
    $response[] = [
        'planName' => $planName,
        'exercises' => $exercises
    ];
}

header('Content-Type: application/json');
echo json_encode($response);

$conn->close();
?>
