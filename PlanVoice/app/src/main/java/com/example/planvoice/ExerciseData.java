package com.example.planvoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseData {
    private static final List<String> ALL_EXERCISES = new ArrayList<>();

    static {
        ALL_EXERCISES.add("벤치 프레스");
        ALL_EXERCISES.add("바이셉 컬");
        ALL_EXERCISES.add("랫 풀다운");
        ALL_EXERCISES.add("스쿼트");
        ALL_EXERCISES.add("인클라인 벤치 프레스");
        ALL_EXERCISES.add("해머 컬");
        ALL_EXERCISES.add("원암 덤벨 로우");
        ALL_EXERCISES.add("데드리프트");
        ALL_EXERCISES.add("페이스 풀");
        ALL_EXERCISES.add("프런트 덤벨 레이즈");
        ALL_EXERCISES.add("레그 프레스");
        ALL_EXERCISES.add("크런치");
        ALL_EXERCISES.add("플랭크");
        ALL_EXERCISES.add("디클라인 벤치 프레스");
        ALL_EXERCISES.add("행잉 레그 레이즈");
        ALL_EXERCISES.add("프리처 컬");
        ALL_EXERCISES.add("오버헤드 프레스");
        ALL_EXERCISES.add("덤벨 플라이");
        ALL_EXERCISES.add("크로스오버");
        // 더 많은 운동을 추가하세요...
    }

    public static Map<String, List<String>> getExercisePlans() {
        Map<String, List<String>> exercisePlans = new HashMap<>();

        exercisePlans.put("취약 부위 보완 플랜", getRandomExercises(5));
        exercisePlans.put("근육량 증가 추천 플랜 (초급)", getRandomExercises(5));
        exercisePlans.put("근육량 증가 추천 플랜 (입문)", getRandomExercises(5));
        exercisePlans.put("근육량 증가 추천 플랜 (중급)", getRandomExercises(5));
        exercisePlans.put("근육량 증가 추천 플랜 (고급)", getRandomExercises(5));
        exercisePlans.put("체지방 감소 추천 플랜 (입문)", getRandomExercises(5));
        exercisePlans.put("체지방 감소 추천 플랜 (초급)", getRandomExercises(5));
        exercisePlans.put("체지방 감소 추천 플랜 (중급)", getRandomExercises(5));
        exercisePlans.put("체지방 감소 추천 플랜 (고급)", getRandomExercises(5));
        exercisePlans.put("현재 상태 유지 플랜 (입문)", getRandomExercises(5));
        exercisePlans.put("현재 상태 유지 플랜 (초급)", getRandomExercises(5));
        exercisePlans.put("현재 상태 유지 플랜 (중급)", getRandomExercises(5));
        exercisePlans.put("현재 상태 유지 플랜 (고급)", getRandomExercises(5));

        return exercisePlans;
    }

    private static List<String> getRandomExercises(int count) {
        List<String> randomExercises = new ArrayList<>(ALL_EXERCISES);
        Collections.shuffle(randomExercises);
        return randomExercises.subList(0, Math.min(count, randomExercises.size()));
    }
}
