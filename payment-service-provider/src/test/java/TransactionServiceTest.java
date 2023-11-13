import com.example.group11.service.qa.QAService;
import com.example.group11.service.transaction.TransactionService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * FileName: TransactionServiceTest.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/11/13 20:55
 */
public class TransactionServiceTest {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.transaction.TransactionService.class)
    private static TransactionService transactionService;

    private static TransactionService getTransactionService(){
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-transaction");
        application.setOwner("owner");

        RegistryConfig registry1 = new RegistryConfig();
        registry1.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<TransactionService> reference = new ReferenceConfig();

        reference.setApplication(application);
        reference.setRegistry(registry1);

        reference.setInterface(TransactionService.class);
        //接口定义的版本号
        reference.setVersion("1.0.0");
        return reference.get();
    }

    @Test
    public void testTopUp() {
        if (transactionService == null)
            transactionService = getTransactionService();
        transactionService.topup(8L, new BigDecimal("1.0"));
    }
}
