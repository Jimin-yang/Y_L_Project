<?php
header('Content-Type: application/json');

$bodyPart = isset($_GET['bodyPart']) ? $_GET['bodyPart'] : 'all';

// 데이터베이스 연결 설정
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

// SQL 쿼리
if ($bodyPart == 'all') {
    $sql = "SELECT * FROM exercise";
} else {
    $sql = "SELECT * FROM exercise WHERE BodyPart = '$bodyPart'";
}

$result = $conn->query($sql);

$exercises = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $exercises[] = $row;
    }
} else {
    echo json_encode(array());
    exit();
}

$conn->close();

// JSON으로 반환
echo json_encode($exercises);
?>
