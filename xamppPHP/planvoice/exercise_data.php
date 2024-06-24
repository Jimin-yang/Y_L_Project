<?php
// 데이터베이스 연결 설정
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

// MySQL 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 확인
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 최근 1주일간 각 부위별 운동 시간 조회 쿼리
// 최근 1주일간 각 부위별 운동 시간 조회 쿼리
$sql = "SELECT chest_time, shoulder_time, arm_time, back_time, leg_time, timestamp 
        FROM user_exercise_data 
        WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 6 DAY)";

$result = $conn->query($sql);

$exerciseByBodyPart = array(
    'chest' => 0,
    'shoulder' => 0,
    'arm' => 0,
    'back' => 0,
    'leg' => 0
);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $exerciseByBodyPart['chest'] += $row['chest_time'];
        $exerciseByBodyPart['shoulder'] += $row['shoulder_time'];
        $exerciseByBodyPart['arm'] += $row['arm_time'];
        $exerciseByBodyPart['back'] += $row['back_time'];
        $exerciseByBodyPart['leg'] += $row['leg_time'];
    }
}


// 최근 1주일간 날짜별 총 운동 시간 조회 쿼리
$sqlTotal = "SELECT DATE(timestamp) AS exercise_date, SUM(total_time) AS total_time 
             FROM user_exercise_data 
             WHERE timestamp >= DATE_SUB(NOW(), INTERVAL 6 DAY) 
             GROUP BY exercise_date 
             ORDER BY exercise_date";
$resultTotal = $conn->query($sqlTotal);

$exerciseByDate = array();

if ($resultTotal->num_rows > 0) {
    while($row = $resultTotal->fetch_assoc()) {
        $exerciseByDate[$row['exercise_date']] = $row['total_time'];
    }
}

// JSON 형식으로 데이터 전송
$data = array(
    'exerciseByBodyPart' => $exerciseByBodyPart,
    'exerciseByDate' => $exerciseByDate
);

header('Content-Type: application/json');
echo json_encode($data);

$conn->close();
?>
