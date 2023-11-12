import com.example.group11.commons.utils.OssFileUpload;
import org.junit.Test;


/**
 * FileName: OssFileUploadTest.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/11/11 15:39
 */
public class OssFileUploadTest {

    @Test
    public void fileUploadTest(){
        OssFileUpload ossFileUpload = new OssFileUpload();
        System.out.println(ossFileUpload.getEndpoint());
    }
}
