package com.example.group11.gateway.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 *  定义路由实体
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "route")
public class Router {

	/**
	 * 路由ID
	 **/
	@Id
	private String id;

	/**
	 * 路由断言
	 **/
	private String predicates;
	/**
	 * 过滤器
	 **/
	private String filters;
	/**
	 * 跳转地址uri
	 **/
	private String uri;
	/**
	 * 路由顺序
	 **/
	@Column(name = "route_order")
	private int routeOrder;

	private Date createTime;

	private Date updateTime;

}