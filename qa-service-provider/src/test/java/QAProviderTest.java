import com.example.group11.entity.sql.Question;
import com.example.group11.model.QuestionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.user.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * FileName: QAProviderTest.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/11/5 19:15
 */


public class QAProviderTest {
    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    private static QAService qaService;

    private static QAService getQAService(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-question");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<QAService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(QAService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");
        return reference.get();
    }

    @Test
    public void testQueryQuestionById(){
        if (qaService == null) {
            qaService = getQAService();
        }
        QuestionModel questionModel = qaService.queryQuestionById(1);
        Assert.assertNotNull(questionModel);
        System.out.println(questionModel);
    }
}
