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

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['user_id']) && isset($_POST['plan_name']) && isset($_POST['total_time']) && isset($_POST['chest_time']) && isset($_POST['shoulder_time']) && isset($_POST['arm_time']) && isset($_POST['back_time']) && isset($_POST['leg_time'])) {
    $user_id = $_POST['user_id'];
    $plan_name = $_POST['plan_name'];
    $total_time = $_POST['total_time'];
    $chest_time = $_POST['chest_time'];
    $shoulder_time = $_POST['shoulder_time'];
    $arm_time = $_POST['arm_time'];
    $back_time = $_POST['back_time'];
    $leg_time = $_POST['leg_time'];

    // Log received data for debugging
    error_log("Received user_id: " . $user_id);
    error_log("Received plan_name: " . $plan_name);
    error_log("Received total_time: " . $total_time);
    error_log("Received chest_time: " . $chest_time);
    error_log("Received shoulder_time: " . $shoulder_time);
    error_log("Received arm_time: " . $arm_time);
    error_log("Received back_time: " . $back_time);
    error_log("Received leg_time: " . $leg_time);

    // Insert data into user_exercise_data table
    $sql = "INSERT INTO user_exercise_data (user_id, plan_name, total_time, chest_time, shoulder_time, arm_time, back_time, leg_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    if ($stmt) {
        $stmt->bind_param("isiiiiii", $user_id, $plan_name, $total_time, $chest_time, $shoulder_time, $arm_time, $back_time, $leg_time);
        $stmt->execute();
        $stmt->close();
        echo json_encode(["status" => "success", "message" => "Data saved successfully"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Database error: " . $conn->error]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid request"]);
}

$conn->close();
?>
