package com.example.planvoice;

public class PlanConfig {
    public static class PlanDetails {
        public int exerciseCount;
        public int estimatedTime;

        public PlanDetails(int exerciseCount, int estimatedTime) {
            this.exerciseCount = exerciseCount;
            this.estimatedTime = estimatedTime;
        }
    }

    public static PlanDetails getPlanDetails(String planName) {
        switch (planName) {
            case "근육량 증가 추천 플랜 (초급)":
                return new PlanDetails(3, 30);
            case "근육량 증가 추천 플랜 (입문)":
                return new PlanDetails(3, 30);
            case "근육량 증가 추천 플랜 (중급)":
                return new PlanDetails(4, 40);
            case "근육량 증가 추천 플랜 (고급)":
                return new PlanDetails(5, 50);
            case "체지방 감소 추천 플랜 (입문)":
                return new PlanDetails(3, 30);
            case "체지방 감소 추천 플랜 (중급)":
                return new PlanDetails(4, 40);
            case "체지방 감소 추천 플랜 (고급)":
                return new PlanDetails(5, 50);
            case "현재 상태 유지 플랜 (입문)":
                return new PlanDetails(3, 30);
            case "현재 상태 유지 플랜 (중급)":
                return new PlanDetails(4, 40);
            case "현재 상태 유지 플랜 (고급)":
                return new PlanDetails(5, 50);
            default:
                return new PlanDetails(3, 30); // 기본값 설정
        }
    }
}
