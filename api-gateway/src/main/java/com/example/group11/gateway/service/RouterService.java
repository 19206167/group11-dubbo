package com.example.group11.gateway.service;

import com.example.group11.gateway.entity.Router;
import com.example.group11.gateway.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RouterService {

	@Autowired
	private RouteRepository routeRepository;
	/**
	 * 获取数据库中所有的route数据
	 * @return
	 */
	public List<Router> listAll() {
		return  routeRepository.findAll();
	}

	/**
	 * 新增
	 * @param router
	 */
	@Transactional
	public void add(Router router) {
		router.setCreateTime(new Date());
		router.setUpdateTime(new Date());
		routeRepository.save(router);
	}
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public void delete(String id) {
		routeRepository.deleteById(id);
	}
}
