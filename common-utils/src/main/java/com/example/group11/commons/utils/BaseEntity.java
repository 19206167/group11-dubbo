package com.example.group11.commons.utils;

import java.util.Date;

public interface BaseEntity<IdType> {
    IdType getId();

    void setId(IdType id);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
