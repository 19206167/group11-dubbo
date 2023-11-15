package com.example.group11;

import com.example.group11.entity.es.QaES;
import com.example.group11.entity.es.UserES;
import com.example.group11.service.search.SearchService;
import com.example.group11.service.user.FollowService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Page;

import java.util.List;

public class SearchProviderTest {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class, check = false, timeout = 10000)
    private SearchService searchService;

    private static SearchService getSearchService(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-search");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://172.24.84.138:2181");

        ReferenceConfig<SearchService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(SearchService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");
        return reference.get();
    }

    @Test
    public void testSaveUser() {
        if (searchService == null)
            searchService = getSearchService();
        UserES userES = new UserES();
        userES.setId("testSaveUser");
        searchService.saveUser(userES);
        Assert.assertNotNull(searchService.getUserById("testSaveUser"));
        searchService.deleteUser("testSaveUser");
    }

    @Test
    public void testSaveQa() {
        if (searchService == null)
            searchService = getSearchService();
        QaES qaES = new QaES();
        qaES.setId("testSaveQa");
        searchService.saveQa(qaES);
        Assert.assertNotNull(searchService.getQaById("testSaveQa"));
        searchService.deleteQa("testSaveQa");
    }

    @Test
    public void testGetQaById() {
        if (searchService == null)
            searchService = getSearchService();
        QaES qaES = new QaES();
        qaES.setId("testGetQaById");
        searchService.saveQa(qaES);
        Assert.assertNotNull(searchService.getQaById("testGetQaById"));
        searchService.deleteQa("testGetQaById");
    }

    @Test
    public void testDeleteUser() {
        if (searchService == null)
            searchService = getSearchService();
        UserES userES = new UserES();
        userES.setId("testDeleteUser");
        searchService.saveUser(userES);
        searchService.deleteUser("testDeleteUser");
        Assert.assertNull(searchService.getUserById("testDeleteUser"));
    }

    @Test
    public void testDeleteQa() {
        if (searchService == null)
            searchService = getSearchService();
        QaES qaES = new QaES();
        qaES.setId("testDeleteQa");
        searchService.saveQa(qaES);
        searchService.deleteQa("testDeleteQa");
        Assert.assertNull(searchService.getQaById("testDeleteQa"));
    }

    @Test
    public void testGetUserById() {
        if (searchService == null)
            searchService = getSearchService();
        UserES userES = new UserES();
        userES.setId("testGetUserById");
        searchService.saveUser(userES);
        Assert.assertNotNull(searchService.getUserById("testGetUserById"));
        searchService.deleteUser("testGetUserById");
    }

    @Test
    public void testQueryUser() {
        if (searchService == null)
            searchService = getSearchService();
        UserES userES = new UserES();
        userES.setId("testQueryUser");
        userES.setDescription("testQueryUser");
        searchService.saveUser(userES);
        UserES testUserES = searchService.getUserById("testQueryUser");
        Page<UserES> userESPage = searchService.queryUser(0,90, "testQueryUser");
        List<UserES> userESList = userESPage.getContent();
        Assertions.assertTrue(userESList.contains(testUserES));
        searchService.deleteUser("testQueryUser");
    }

    @Test
    public void testQueryQa() {
        if (searchService == null)
            searchService = getSearchService();
        QaES qaES = new QaES();
        qaES.setId("testQueryQa");
        qaES.setAnswerContent("testQueryQa");
        searchService.saveQa(qaES);
        QaES testQaES = searchService.getQaById("testQueryQa");
        Page<QaES> qaESPage = searchService.queryQa(0,90, null, "testQueryQa", null);
        List<QaES> qaEsList = qaESPage.getContent();
        Assertions.assertTrue(qaEsList.contains(testQaES));
        searchService.deleteQa("testQueryQa");
    }


}
