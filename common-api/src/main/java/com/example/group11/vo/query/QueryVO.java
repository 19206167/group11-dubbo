package com.example.group11.vo.query;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class QueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNo;

    private Integer pageSize;

}
