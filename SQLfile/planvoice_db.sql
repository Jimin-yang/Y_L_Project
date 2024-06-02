-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 24-06-02 16:41
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
(1, '벤치 프레스', '가슴', '가슴근육을 강화하는 운동입니다.', 'bench_press'),
(2, '바이셉 컬', '팔', '상완 이두근을 강화하는 운동입니다.', 'bicep_curl'),
(3, '체스트 프레스 머신', '가슴', '가슴근육을 강화하는 운동입니다.', 'chest_press_machine'),
(4, '클로즈 그립 벤치 프레스', '가슴', '좁은 그립으로 가슴근육을 강화하는 운동입니다.', 'close_grip_bench_press'),
(5, '컨센트레이션 컬', '팔', '상완 이두근을 강화하는 운동입니다.', 'concentration_curl'),
(6, '크로스오버', '가슴', '가슴근육을 강화하는 운동입니다.', 'crossovers'),
(7, '크런치', '복부', '복부근육을 강화하는 운동입니다.', 'crunches'),
(8, '데드리프트', '하체', '대퇴사두근, 승모근, 허벅지 후면 등을 강화하는 운동입니다.', 'deadlift'),
(9, '디클라인 벤치 프레스', '가슴', '아랫가슴근육을 강화하는 운동입니다.', 'decline_bench_press'),
(10, '딥스', '가슴', '가슴근육을 강화하는 운동입니다.', 'dips'),
(11, '덤벨 플라이', '가슴', '가슴근육을 강화하는 운동입니다.', 'dumbbell_fly'),
(12, '페이스 풀', '어깨', '어깨근육을 강화하는 운동입니다.', 'face_pull'),
(13, '프런트 덤벨 레이즈', '어깨', '전면 어깨근을 강화하는 운동입니다.', 'front_dumbbell_raise'),
(14, '해머 컬', '팔', '상완 이두근을 강화하는 운동입니다.', 'hammer_curl'),
(15, '행잉 레그 레이즈', '복부', '복부근육을 강화하는 운동입니다.', 'hanging_leg_raise'),
(16, '인클라인 벤치 프레스', '가슴', '윗가슴근육을 강화하는 운동입니다.', 'incline_bench_press'),
(17, '랫 풀다운', '등', '넓은 등을 강화하는 운동입니다.', 'lat_pulldown'),
(18, '레그 컬', '하체', '허벅지 후면의 근육을 강화하는 운동입니다.', 'leg_curl'),
(19, '레그 익스텐션', '하체', '대퇴사두근을 강화하는 운동입니다.', 'leg_extension'),
(20, '레그 프레스', '하체', '대퇴사두근, 승모근, 종아리 등을 강화하는 운동입니다.', 'leg_press'),
(21, '레그 레이즈', '복부', '복부근육을 강화하는 운동입니다.', 'leg_raises'),
(22, '원암 덤벨 로우', '등', '넓은 등을 강화하는 운동입니다.', 'one_arm_dumbbell_row'),
(23, '오버헤드 프레스', '어깨', '어깨근육을 강화하는 운동입니다.', 'overhead_press'),
(24, '펙 데크 플라이', '가슴', '가슴근육을 강화하는 운동입니다.', 'pec_deck_fly'),
(25, '플랭크', '복부', '복부근육을 강화하는 운동입니다.', 'plank'),
(26, '프리처 컬', '팔', '상완 이두근을 강화하는 운동입니다.', 'preacher_curl'),
(27, '턱걸이', '등', '넓은 등을 강화하는 운동입니다.', 'pull_up'),
(28, '푸쉬업', '가슴', '가슴근육을 강화하는 운동입니다.', 'push_up'),
(29, '리어 델트 플라이', '어깨', '어깨근육을 강화하는 운동입니다.', 'rear_delt_fly'),
(30, '리버스 크런치', '복부', '복부근육을 강화하는 운동입니다.', 'reverse_crunch'),
(31, '리버스 플라이', '등', '넓은 등을 강화하는 운동입니다.', 'reverse_fly'),
(32, '러시안 트위스트', '복부', '복부근육을 강화하는 운동입니다.', 'russian_twist'),
(33, '시티드 로우', '등', '넓은 등을 강화하는 운동입니다.', 'seated_row'),
(34, '사이드 레터럴 레이즈', '어깨', '측면 어깨근을 강화하는 운동입니다.', 'side_lateral_raise'),
(36, '스컬 크러셔', '팔', '삼두근을 강화하는 운동입니다.', 'skull_crusher'),
(37, '스쿼트', '하체', '대퇴사두근, 종아리, 승모근을 강화하는 운동입니다.', 'squat'),
(38, '스탠딩 카프 레이즈', '하체', '종아리를 강화하는 운동입니다.', 'standing_calf_raise'),
(39, '티바 로우', '등', '넓은 등을 강화하는 운동입니다.', 't_bar_row'),
(40, '트라이셉스 푸시 다운', '팔', '삼두근을 강화하는 운동입니다.', 'tricep_pushdown'),
(41, '업라이트 로우', '어깨', '어깨근육을 강화하는 운동입니다.', 'upright_row');

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
