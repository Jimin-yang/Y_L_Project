<?php
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 확인
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 플랜 목록과 각 플랜에 포함된 운동 목록 가져오기
$sql = "SELECT ep.id as plan_id, ep.plan_name, e.*
        FROM exercise_plans ep
        JOIN exercise_plan_exercises epe ON ep.id = epe.plan_id
        JOIN exercise e ON epe.exercise_id = e.ID";
$result = $conn->query($sql);

$plans = [];
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $plan_id = $row['plan_id'];
        if (!isset($plans[$plan_id])) {
            $plans[$plan_id] = [
                'planName' => $row['plan_name'],
                'exercises' => []
            ];
        }
        $plans[$plan_id]['exercises'][] = [
            'ID' => $row['ID'],
            'ExerciseName' => $row['ExerciseName'],
            'BodyPart' => $row['BodyPart'],
            'ExerciseDescription' => $row['ExerciseDescription'],
            'imageURL' => $row['imageURL']
        ];
    }
}

header('Content-Type: application/json');
echo json_encode(array_values($plans));

$conn->close();
?>
