import com.example.group11.service.user.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;

/**
 * FileName: UserServiceTest.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/11/13 19:28
 */
public class UserServiceTest {

    @Test
    public void test(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-user");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<UserService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(UserService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");

        UserService userService = reference.get();
        System.out.println(reference.get());
        System.out.println(userService.queryUserIdByLoginName("lzj")  + "*****");
    }
}
