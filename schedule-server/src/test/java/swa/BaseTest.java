package swa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jinyan on 10/30/17 6:22 PM.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-test.xml")
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Test
    public void init() {

    }

}
