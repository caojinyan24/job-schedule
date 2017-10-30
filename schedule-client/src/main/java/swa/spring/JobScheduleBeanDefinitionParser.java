package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import swa.rpc.Server;

/**
 * Created by jinyan on 10/11/17 6:38 PM.
 */
public class JobScheduleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleBeanDefinitionParser.class);

    @Override
    protected Class<?> getBeanClass(Element element) {
        return BeanParser.class;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    public void doParse(Element element, BeanDefinitionBuilder beanDefinitionBuilder) {
        if (element.getAttribute("port") != null && element.getAttribute("port") != "") {
            Integer port = Integer.valueOf(element.getAttribute("port"));
            logger.info("doParse：port-{}",port);
            beanDefinitionBuilder.addPropertyValue("port", port);
            try {
                new Server("127.0.0.1", port).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
