<?php
header('Content-Type: application/json');
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 오류 체크
if ($conn->connect_error) {
    error_log("Connection failed: " . $conn->connect_error);
    die(json_encode(array("status" => "error", "message" => "Connection failed: " . $conn->connect_error)));
}

// POST 요청 체크
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // POST 데이터에서 필요한 값들을 안전하게 가져옴
    $id = isset($_POST['userId']) ? $_POST['userId'] : '';
    $newName = isset($_POST['newName']) ? $_POST['newName'] : '';
    $newHeight = isset($_POST['newHeight']) ? $_POST['newHeight'] : '';
    $newWeight = isset($_POST['newWeight']) ? $_POST['newWeight'] : '';
    $newPhone = isset($_POST['newPhone']) ? $_POST['newPhone'] : '';
    $newEmail = isset($_POST['newEmail']) ? $_POST['newEmail'] : '';

    // SQL 쿼리 준비 및 실행
    $sql = "UPDATE users SET name=?, height=?, weight=?, phone=?, email=? WHERE id=?";
    $stmt = $conn->prepare($sql);

    // 바인딩할 변수의 타입 문자열과 개수 확인
    if ($stmt) {
        $stmt->bind_param("siisss", $newName, $newHeight, $newWeight, $newPhone, $newEmail, $id);

        // 쿼리 실행
        if ($stmt->execute()) {
            // 업데이트된 사용자 정보를 다시 조회
            $updatedUser = getUserById($conn, $id);
            
            if ($updatedUser !== null) {
                error_log("Updated user info: " . json_encode($updatedUser));
                echo json_encode($updatedUser);
            } else {
                echo json_encode(array("status" => "error", "message" => "Failed to retrieve updated user"));
            }
        } else {
            error_log("Failed to update user: " . $stmt->error);
            echo json_encode(array("status" => "error", "message" => "Failed to update user: " . $stmt->error));
        }

        // 사용한 리소스 정리
        $stmt->close();
    } else {
        error_log("Failed to prepare statement: " . $conn->error);
        echo json_encode(array("status" => "error", "message" => "Failed to prepare statement: " . $conn->error));
    }
} else {
    error_log("Invalid request method: " . $_SERVER['REQUEST_METHOD']);
}

// 데이터베이스 연결 종료
$conn->close();

// 사용자 정보를 ID로 조회하여 반환하는 함수
function getUserById($conn, $id) {
    $sql = "SELECT * FROM users WHERE id=?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();
        return $user;
    } else {
        return null;
    }
}
?>
