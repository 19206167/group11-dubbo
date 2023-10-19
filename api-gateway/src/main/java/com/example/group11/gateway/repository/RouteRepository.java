package com.example.group11.gateway.repository;


import com.example.group11.gateway.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Router,String> {
}
