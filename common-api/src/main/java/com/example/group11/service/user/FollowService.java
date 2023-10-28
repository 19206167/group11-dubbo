package com.example.group11.service.user;

import com.example.group11.commons.utils.BaseService;
import com.example.group11.model.FollowModel;

import java.util.List;

public interface FollowService extends BaseService<FollowModel, Long> {

    List<Long> queryFollowingIdByFollowedId(Long FollowedId);

    List<FollowModel> queryFollowModelByFollowingIdAndFollowedId(Long FollowingId, Long FollowedId);
}
