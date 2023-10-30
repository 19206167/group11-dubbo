package com.example.group11.redis;

public interface RedisService {

    Boolean likeStatus(String questionId, String userId);

    String saveLiked2Redis(String questionId, String userId);

    String unlikeFromRedis(String questionId, String userId);

    String queryLike(String questionId);

    void incrementLikedCount(String questionId);

    void decrementLikedCount(String questionId);

}
