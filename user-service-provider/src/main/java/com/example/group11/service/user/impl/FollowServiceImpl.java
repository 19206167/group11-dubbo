package com.example.group11.service.user.impl;

import com.example.group11.commons.utils.Group11Exception;
import com.example.group11.model.FollowModel;
import com.example.group11.service.user.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

/**
 * FileName: FollowServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:20
 */
public class FollowServiceImpl implements FollowService {
    @Override
    public FollowModel findById(Long id) {
        return null;
    }

    @Override
    public Page<FollowModel> findAll(Specification specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<FollowModel> findAll() {
        return null;
    }

    @Override
    public List<FollowModel> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public Page<FollowModel> findPageByExample(FollowModel example, Pageable pageable) {
        return null;
    }

    @Override
    public long countByExample(FollowModel example) {
        return 0;
    }

    @Override
    public Long insertOne(FollowModel model) throws Group11Exception {
        return null;
    }

    @Override
    public List<Long> insertInBatch(Collection<FollowModel> models) throws Group11Exception {
        return null;
    }

    @Override
    public void deleteById(FollowModel model) {

    }

    @Override
    public void deleteInBatch(Collection<FollowModel> models) throws Group11Exception {

    }

    @Override
    public void updateById(FollowModel model) throws Group11Exception {

    }

    @Override
    public void updateInBatch(Collection<FollowModel> models) throws Group11Exception {

    }

    @Override
    public long deleteLogicallyByIds(Collection<Long> ids, String updateBy) throws Group11Exception {
        return 0;
    }

    @Override
    public long deleteHardByIds(Collection<Long> ids) throws Group11Exception {
        return 0;
    }

    @Override
    public List<Long> upsertInBatch(Collection<FollowModel> models) throws Group11Exception {
        return null;
    }

    @Override
    public int deleteAllHardly() {
        return 0;
    }
}
