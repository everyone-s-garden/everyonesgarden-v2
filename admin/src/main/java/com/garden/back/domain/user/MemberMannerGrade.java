package com.garden.back.domain.user;

import lombok.Getter;

import java.util.NavigableMap;
import java.util.TreeMap;

@Getter
public enum MemberMannerGrade {
    SEED(-Float.MAX_VALUE),
    SPROUT(10.0f),
    STEM(30.0f),
    FRUIT(60.0f),
    HARVEST(80.0f),
    FARMER(95.0f);

    private final float minMannerScore;
    private static final NavigableMap<Float, MemberMannerGrade> scoreToGradeMap = new TreeMap<>();

    static {
        for (MemberMannerGrade grade : MemberMannerGrade.values()) {
            scoreToGradeMap.put(grade.getMinMannerScore(), grade);
        }
    }

    MemberMannerGrade(float minMannerScore) {
        this.minMannerScore = minMannerScore;
    }

    public static MemberMannerGrade getGradeByScore(Float score) {
        return scoreToGradeMap.floorEntry(score).getValue();
    }
}
