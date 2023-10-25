package com.example.group11.repository.es;

import com.example.group11.entity.es.QaES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QaESRepository extends ElasticsearchRepository<QaES, String> {

}
