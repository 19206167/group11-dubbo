package com.example.group11.commons.utils;

public interface BaseEntity<IdType> {
    IdType getId();

    void setId(IdType id);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
