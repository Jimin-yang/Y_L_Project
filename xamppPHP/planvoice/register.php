<?php
header('Content-Type: application/json');
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(array("status" => "error", "message" => "Connection failed: " . $conn->connect_error)));
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = $_POST['id'];
    $password = $_POST['password'];
    $name = $_POST['name'];
    $age = $_POST['age'];
    $height = $_POST['height'];
    $weight = $_POST['weight'];
    $gender = $_POST['gender'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];

    $sql = "INSERT INTO users (username, password, name, age, height, weight, gender, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssssss", $id, $password, $name, $age, $height, $weight, $gender, $email, $phone);

    if ($stmt->execute()) {
        echo json_encode(array("status" => "success", "message" => "User registered successfully"));
    } else {
        echo json_encode(array("status" => "error", "message" => "Failed to register user"));
    }

    $stmt->close();
}

$conn->close();
?>
