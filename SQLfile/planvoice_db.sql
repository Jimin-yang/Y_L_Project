-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 24-05-30 20:53
-- 서버 버전: 10.4.32-MariaDB
-- PHP 버전: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `planvoice_db`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `exercise`
--

CREATE TABLE `exercise` (
  `ID` int(11) NOT NULL,
  `ExerciseName` varchar(100) DEFAULT NULL,
  `BodyPart` varchar(50) DEFAULT NULL,
  `ExerciseDescription` text DEFAULT NULL,
  `imageURL` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 테이블의 덤프 데이터 `exercise`
--

INSERT INTO `exercise` (`ID`, `ExerciseName`, `BodyPart`, `ExerciseDescription`, `imageURL`) VALUES
(1, '벤치 프레스', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/bench_press.png'),
(2, '바이셉 컬', '팔', '상완 이두근을 강화하는 운동입니다.', '@drawable/bicep_curl.png'),
(3, '체스트 프레스 머신', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/chest_press_machine.png'),
(4, '클로즈 그립 벤치 프레스', '가슴', '좁은 그립으로 가슴근육을 강화하는 운동입니다.', '@drawable/close_grip_bench_press.png'),
(5, '컨센트레이션 컬', '팔', '상완 이두근을 강화하는 운동입니다.', '@drawable/concentration_curl.png'),
(6, '크로스오버', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/crossovers.png'),
(7, '크런치', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/crunches.png'),
(8, '데드리프트', '하체', '대퇴사두근, 승모근, 허벅지 후면 등을 강화하는 운동입니다.', '@drawable/deadlift.png'),
(9, '디클라인 벤치 프레스', '가슴', '아랫가슴근육을 강화하는 운동입니다.', '@drawable/decline_bench_press.png'),
(10, '딥스', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/dips.png'),
(11, '덤벨 플라이', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/dumbbell_fly.png'),
(12, '페이스 풀', '어깨', '어깨근육을 강화하는 운동입니다.', '@drawable/face_pull.png'),
(13, '프런트 덤벨 레이즈', '어깨', '전면 어깨근을 강화하는 운동입니다.', '@drawable/front_dumbbell_raise.png'),
(14, '해머 컬', '팔', '상완 이두근을 강화하는 운동입니다.', '@drawable/hammer_curl.png'),
(15, '행잉 레그 레이즈', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/hanging_leg_raise.png'),
(16, '인클라인 벤치 프레스', '가슴', '윗가슴근육을 강화하는 운동입니다.', '@drawable/incline_bench_press.png'),
(17, '랫 풀다운', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/lat_pulldown.png'),
(18, '레그 컬', '하체', '허벅지 후면의 근육을 강화하는 운동입니다.', '@drawable/leg_curl.png'),
(19, '레그 익스텐션', '하체', '대퇴사두근을 강화하는 운동입니다.', '@drawable/leg_extension.png'),
(20, '레그 프레스', '하체', '대퇴사두근, 승모근, 종아리 등을 강화하는 운동입니다.', '@drawable/leg_press.png'),
(21, '레그 레이즈', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/leg_raises.png'),
(22, '원암 덤벨 로우', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/one_arm_dumbbell_row.png'),
(23, '오버헤드 프레스', '어깨', '어깨근육을 강화하는 운동입니다.', '@drawable/overhead_press.png'),
(24, '펙 데크 플라이', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/pec_deck_fly.png'),
(25, '플랭크', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/plank.png'),
(26, '프리처 컬', '팔', '상완 이두근을 강화하는 운동입니다.', '@drawable/preacher_curl.png'),
(27, '턱걸이', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/pull_up.png'),
(28, '푸쉬업', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/push_up.png'),
(29, '리어 델트 플라이', '어깨', '어깨근육을 강화하는 운동입니다.', '@drawable/rear_delt_fly.png'),
(30, '리버스 크런치', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/reverse_crunch.png'),
(31, '리버스 플라이', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/reverse_fly.png'),
(32, '러시안 트위스트', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/russian_twist.png'),
(33, '시티드 로우', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/seated_row.png'),
(34, '사이드 레터럴 레이즈', '어깨', '측면 어깨근을 강화하는 운동입니다.', '@drawable/side_lateral_raise.png'),
(35, '사이드 플랭크', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/side_plank.png'),
(36, '스컬 크러셔', '팔', '삼두근을 강화하는 운동입니다.', '@drawable/skull_crusher.png'),
(37, '스쿼트', '하체', '대퇴사두근, 종아리, 승모근을 강화하는 운동입니다.', '@drawable/squat.png'),
(38, '스탠딩 카프 레이즈', '하체', '종아리를 강화하는 운동입니다.', '@drawable/standing_calf_raise.png'),
(39, '티바 로우', '등', '넓은 등을 강화하는 운동입니다.', '@drawable/t_bar_row.png'),
(40, '트라이셉스 푸시 다운', '팔', '삼두근을 강화하는 운동입니다.', '@drawable/tricep_pushdown.png'),
(41, '업라이트 로우', '어깨', '어깨근육을 강화하는 운동입니다.', '@drawable/upright_row.png'),
(42, '어드벤스드 버전 플랭크', '복부', '복부근육을 강화하는 운동입니다.', '@drawable/advanced_plank.png'),
(43, '워킹 런지', '하체', '대퇴사두근, 종아리, 승모근을 강화하는 운동입니다.', '@drawable/walking_lunge.png'),
(44, '워터 바이블', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/water_bottle.png'),
(45, '제이 스컬터', '가슴', '가슴근육을 강화하는 운동입니다.', '@drawable/jay_cutler.png');

-- --------------------------------------------------------

--
-- 테이블 구조 `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 테이블의 덤프 데이터 `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `age`, `height`, `weight`, `gender`, `email`, `phone`) VALUES
(1, 'testuser', 'testpassword', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'testtest', 'testtoto', 'james', 24, 177, 76, '남자', 'james@naver.com', '01043434343'),
(3, 'test1', 'test11', 'Amy', 20, 170, 60, '남자', 'Amy@gmail.com', '01012345678'),
(4, '1111', '1111', 'wwww', 24, 156, 156, '여자', 'ww@ww.com', '01011112222');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`ID`);

--
-- 테이블의 인덱스 `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
