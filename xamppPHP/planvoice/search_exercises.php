<?php
header('Content-Type: application/json');

$searchText = isset($_GET['searchText']) ? $_GET['searchText'] : '';

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
$sql = "SELECT * FROM exercise WHERE ExerciseName LIKE '%$searchText%'";

$result = $conn->query($sql);

$exercises = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $exercises[] = $row;
    }
}

$conn->close();

// JSON으로 반환
echo json_encode($exercises);
?>
