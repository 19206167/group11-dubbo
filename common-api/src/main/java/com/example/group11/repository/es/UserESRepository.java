package com.example.group11.repository.es;

import com.example.group11.entity.es.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserESRepository extends ElasticsearchRepository<UserES, String> {

}
