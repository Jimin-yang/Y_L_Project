<?php
header('Content-Type: application/json');
$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "planvoice_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(array("status" => "error", "message" => "Connection failed: " . $conn->connect_error)));
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $inputUsername = $_POST['username'];
    $inputPassword = $_POST['password'];

    $sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $inputUsername, $inputPassword);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();
        echo json_encode(array("status" => "success", "message" => "Login successful", "user" => $user));
    } else {
        echo json_encode(array("status" => "error", "message" => "Invalid username or password"));
    }

    $stmt->close();
}

$conn->close();
?>
