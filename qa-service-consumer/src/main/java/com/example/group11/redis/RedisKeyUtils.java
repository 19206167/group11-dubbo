package com.example.group11.redis;

public class RedisKeyUtils {

    static final String MAP_KEY_QUESTION_LIKED = "MAP_KEY_QUESTION_LIKED";
    static final String MAP_KEY_QUESTION_LIKED_COUNT = "MAP_USER_QUESTION_COUNT";

    public static String getLikedKey(String questionId, String userId) {
        StringBuilder builder = new StringBuilder();
        builder.append(questionId);
        builder.append("::");
        builder.append(userId);
        return builder.toString();
    }
}
