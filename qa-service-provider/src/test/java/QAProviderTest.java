import com.example.group11.service.qa.QAService;
import org.junit.jupiter.api.Test;
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


@RunWith(SpringRunner.class)
@SpringBootTest
public class QAProviderTest {

    @Autowired
    private QAService qaService;

    @Test
    public void getHotQuestion() {
        qaService.getHottestQuestionsByPage(1, 5);
    }
}
