package com.example.group11.commons.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public interface BaseService<MType, IdType> {

    MType findById(IdType id);

    Page<MType> findAll(Specification specification, Pageable pageable);

    List<MType> findAll();

    List<MType> findAllById(Iterable<IdType> ids);

    Page<MType> findPageByExample(MType example, Pageable pageable);

    long countByExample(MType example);

    IdType insertOne(MType model) throws Group11Exception;

    List<IdType> insertInBatch(Collection<MType> models) throws Group11Exception;

    void deleteById(MType model);

    void deleteInBatch(Collection<MType> models) throws Group11Exception;

    void updateById(MType model) throws Group11Exception;

    void updateInBatch(Collection<MType> models) throws Group11Exception;

    long deleteLogicallyByIds(Collection<IdType> ids, String updateBy) throws Group11Exception;

    long deleteHardByIds(Collection<IdType> ids) throws Group11Exception;

    List<IdType> upsertInBatch(Collection<MType> models) throws Group11Exception;

    int deleteAllHardly();
}

