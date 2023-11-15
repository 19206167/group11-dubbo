import com.example.group11.model.FollowModel;
import com.example.group11.model.UserModel;
import com.example.group11.service.user.FollowService;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.query.UserQueryVO;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: UserServiceTest.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/11/13 19:28
 */
public class UserProviderTest {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.FollowService.class, timeout = 10000)
    private static FollowService followService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.UserService.class, timeout = 10000)
    private static UserService userService;

    private static FollowService getFollowService(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-follow");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://172.24.84.138:2181");

        ReferenceConfig<FollowService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(FollowService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");
        return reference.get();
    }

    private static UserService getUserService(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-user");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://172.24.84.138:2181");

        ReferenceConfig<UserService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(UserService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");
        return reference.get();
    }

    @Test
    public void testQueryUserIdByLoginName() {
        if (userService == null)
            userService = getUserService();
        Long id = userService.queryUserIdByLoginName("admin66");
        Assert.assertEquals(21L, id.longValue());
    }

    @Test
    public void testQueryUserByLoginName() {
        if (userService == null)
            userService = getUserService();
        Assert.assertNotNull(userService.queryUserByLoginName("admin66"));
    }

    @Test
    public void testQueryFanListByUserId() {
        if(followService == null)
            followService = getFollowService();
        if (userService == null)
            userService = getUserService();
        FollowModel followModel = new FollowModel();
        followModel.setBeFollowedUserId(5L);
        followModel.setFollowingUserId(21L);
        followModel.setId(followService.insertOne(followModel));
        UserModel userModel = userService.findById(21L);
        List<UserModel> userModelList = userService.queryFanListByUserId(5L, new UserQueryVO());
        Assertions.assertTrue(userModelList.contains(userModel));
        followService.deleteById(followModel);
    }

    @Test
    public void testQueryFollowListByUserId() {
        if(followService == null)
            followService = getFollowService();
        if (userService == null)
            userService = getUserService();
        FollowModel followModel = new FollowModel();
        followModel.setBeFollowedUserId(5L);
        followModel.setFollowingUserId(21L);
        followModel.setId(followService.insertOne(followModel));
        UserModel userModel = userService.findById(5L);
        List<UserModel> userModelList = userService.queryFollowListByUserId(21L, new UserQueryVO());
        Assertions.assertTrue(userModelList.contains(userModel));
        followService.deleteById(followModel);
    }

    @Test
    public void testQueryUserListByPage() {
        if (userService == null)
            userService = getUserService();
        UserModel userModel = userService.findById(21L);
        UserQueryVO userQueryVO = new UserQueryVO();
        userQueryVO.setId(21L);
        Page<UserModel> userModelPage = userService.queryUserListByPage(userQueryVO);
        List<UserModel> userModelList = userModelPage.getContent();
        Assertions.assertTrue(userModelList.contains(userModel));
    }

    @Test
    public void testQueryFollowingIdByFollowedId() {
        if(followService == null)
            followService = getFollowService();
        if (userService == null)
            userService = getUserService();
        FollowModel followModel = new FollowModel();
        followModel.setBeFollowedUserId(5L);
        followModel.setFollowingUserId(21L);
        followModel.setId(followService.insertOne(followModel));
        UserModel userModel = userService.findById(21L);
        List<Long> userIdList = followService.queryFollowingIdByFollowedId(5L);
        Assertions.assertTrue(userIdList.contains(userModel.getId()));
        followService.deleteById(followModel);
    }

    @Test
    public void testQueryFollowModelByFollowingIdAndFollowedId() {
        if(followService == null)
            followService = getFollowService();
        FollowModel followModel = new FollowModel();
        followModel.setBeFollowedUserId(5L);
        followModel.setFollowingUserId(21L);
        Long followModelId = followService.insertOne(followModel);
        followModel.setId(followModelId);
        List<FollowModel> followModelList = followService.queryFollowModelByFollowingIdAndFollowedId(21L, 5L);
        List<Long> followIdList = followModelList.stream().map(FollowModel::getId).collect(Collectors.toList());
        Assertions.assertTrue(followIdList.contains(followModelId));
        followService.deleteById(followModel);
    }

}
