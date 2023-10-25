package com.example.group11.service.search.impl;


import com.example.group11.commons.utils.CheckUtil;
import com.example.group11.entity.es.QaES;
import com.example.group11.entity.es.UserES;
import com.example.group11.repository.es.QaESRepository;
import com.example.group11.repository.es.UserESRepository;
import com.example.group11.service.search.SearchService;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * FileName: SearchServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:22
 */


@DubboService(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class)
public class SearchServiceImpl implements SearchService {

    @Autowired
    private UserESRepository userESRepository;

    @Autowired
    private QaESRepository QuestionAndAnswerESRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Boolean saveUser(UserES... userES) {
        userESRepository.saveAll(Arrays.asList(userES));
        return true;
    }

    @Override
    public Boolean saveQa(QaES... qaES) {
        QuestionAndAnswerESRepository.saveAll(Arrays.asList(qaES));
        return true;
    }

    @Override
    public Boolean deleteUser(String id) {
        userESRepository.deleteById(id);
        return null;
    }

    @Override
    public UserES getUserById(String id) {
        Optional<UserES> byId = userESRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public List<UserES> getAllUser() {
        List<UserES> list = new ArrayList<>();
        userESRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<UserES> queryUser(Integer page, Integer size, String keyword) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (CheckUtil.isNotEmpty(keyword)) {
            queryBuilder.must(QueryBuilders.matchQuery("description", keyword));
        }
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(fuzzyQuery)
//                .build();
        //多条件查询
        /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("description", keyword);
        FuzzyQueryBuilder fuzzyQuery2 = QueryBuilders.fuzzyQuery("productName", keyword);
        //或者 or
        boolQueryBuilder.should(fuzzyQuery);
        boolQueryBuilder.should(fuzzyQuery2);
        //并且 and
        boolQueryBuilder.must(fuzzyQuery);
        boolQueryBuilder.must(fuzzyQuery2);
        // 构建查询
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();*/
        //精准查询 =
//        TermQueryBuilder termQuery = QueryBuilders.termQuery("productName", keyword);
//        //精准查询 多个条件 in
////        TermsQueryBuilder termQuery = QueryBuilders.termsQuery("productName", keyword, keyword);
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(termQuery)
//                .build();

        //范围查询
//        RangeQueryBuilder price = new RangeQueryBuilder("price").gt(3000).lt(3200);
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(price)
//                .build();

        //分页，页码从0开始
        PageRequest of = PageRequest.of(page, size);
        Query searchQuery = new NativeSearchQueryBuilder()
//                条件
                .withQuery(queryBuilder)
                //分页
                .withPageable(of)
                .build();
        SearchHits<UserES> searchHits = this.elasticsearchRestTemplate.search(searchQuery, UserES.class);
        AggregatedPage<UserES> aggregatedPage = (AggregatedPage) SearchHitSupport.unwrapSearchHits(SearchHitSupport.
                page(searchHits, searchQuery.getPageable()));

//        List<UserES> list = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(aggregatedPage.getContent(), of, aggregatedPage.getTotalElements());
    }

    @Override
    public Page<QaES> queryQa(Integer page, Integer size, String questionContent, String answerContent) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (CheckUtil.isNotEmpty(questionContent)) {
            queryBuilder.must(QueryBuilders.matchQuery("questionContent", questionContent));
        }
        if (CheckUtil.isNotEmpty(answerContent)) {
            queryBuilder.must(QueryBuilders.matchQuery("answerContent", answerContent));
        }
        //分页，页码从0开始
        PageRequest of = PageRequest.of(page, size);
        Query searchQuery = new NativeSearchQueryBuilder()
//                条件
                .withQuery(queryBuilder)
                //分页
                .withPageable(of)
                .build();
        SearchHits<QaES> searchHits = this.elasticsearchRestTemplate.search(searchQuery, QaES.class);
        AggregatedPage<QaES> aggregatedPage = (AggregatedPage) SearchHitSupport.unwrapSearchHits(SearchHitSupport.
                page(searchHits, searchQuery.getPageable()));

//        List<UserES> list = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(aggregatedPage.getContent(), of, aggregatedPage.getTotalElements());
    }
}
