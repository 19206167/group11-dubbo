package com.example.group11.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean likeStatus(String questionId, String userId) {
        String key = RedisKeyUtils.getLikedKey(questionId, userId);
        return redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_QUESTION_LIKED, key);
    }

    @Override
    public String saveLiked2Redis(String questionId, String userId) {
        String key = RedisKeyUtils.getLikedKey(questionId, userId);
        if (this.likeStatus(questionId, userId)) {
            return "不能重复点赞";
        } else {
            redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_QUESTION_LIKED, key, LikedStatusEnum.LIKE.getCode());
            this.incrementLikedCount(questionId);
            return "点赞成功";
        }
    }

    @Override
    public String unlikeFromRedis(String questionId, String userId) {
        String key = RedisKeyUtils.getLikedKey(questionId, userId);
        if (!this.likeStatus(questionId, userId)) {
            return "还没有点赞，不能取消";
        } else {
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_QUESTION_LIKED, key);
            this.decrementLikedCount(questionId);
            return "取消成功";
        }
    }

    @Override
    public String queryLike(String questionId) {
        return redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, questionId) ? redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, questionId).toString() : "0";
    }

    @Override
    public void incrementLikedCount(String questionId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, questionId, 1);
    }

    @Override
    public void decrementLikedCount(String questionId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_QUESTION_LIKED_COUNT, questionId, -1);
    }

}
