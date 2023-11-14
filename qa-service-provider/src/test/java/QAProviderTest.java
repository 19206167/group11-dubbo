import com.example.group11.entity.sql.Question;
import com.example.group11.model.QuestionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.query.QuestionQueryVO;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;

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
//        qaService初始化
        if (qaService == null) {
            qaService = getQAService();
        }
        QuestionModel questionModel = qaService.queryQuestionById(1);
        Assert.assertNotNull(questionModel);
        System.out.println(questionModel);
    }

//    @Test
//    public void testLikeAndCancelLike(){
//        //        qaService初始化
//        if (qaService == null) {
//            qaService = getQAService();
//        }
//        int num = qaService.getLikeNum(14);
//        qaService.like(8L, 14);
//        Assert.assertEquals(num + 1, qaService.getLikeNum(14).intValue());
//        qaService.cancelLike(8L, 14);
//        Assert.assertEquals(num, qaService.getLikeNum(14).intValue());
//    }

    @Test
    public void testComment() {
        //        qaService初始化
        if (qaService == null) {
            qaService = getQAService();
        }
//        int num = qaService.getCommentNum(14);
        int index = qaService.comment(8L, 14, "Junit test");
//        Assert.assertEquals(num + 1, qaService.getCommentNum(14).intValue());
        qaService.deleteComment(8L, 14, index);
//        Assert.assertEquals(num, qaService.getCommentNum(14).intValue());
    }

    @Test
    public void testGetHottestQuestion(){
        //        qaService初始化
        if (qaService == null) {
            qaService = getQAService();
        }

        System.out.println(qaService.getHottestQuestionsByPage(null, null));
    }

    @Test
    public void testQueryUserQuestions(){
        //        qaService初始化
        if (qaService == null) {
            qaService = getQAService();
        }
        QuestionQueryVO questionQueryVO = new QuestionQueryVO();
        questionQueryVO.setAskerId(8L);
        System.out.println(qaService.getUserQuestionsByPage(questionQueryVO));
    }


}
