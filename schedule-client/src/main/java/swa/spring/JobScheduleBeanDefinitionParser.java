package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import swa.rpc.Server;
import swa.tools.ApplicationManager;

/**
 * Created by jinyan on 10/11/17 6:38 PM.
 */
public class JobScheduleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleBeanDefinitionParser.class);
    private static String QSCHEDULE_ANNOTATION = "QSCHEDULE_ANNOTATION";

    @Override
    protected Class<?> getBeanClass(Element element) {
        return BeanParser.class;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    public void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder beanDefinitionBuilder) {
        if (element.getAttribute("port") != null && element.getAttribute("port") != "") {
            final Integer port = Integer.valueOf(element.getAttribute("port"));
            logger.info("doParse：port-{}", port);

            if (!parserContext.getRegistry().containsBeanDefinition(QSCHEDULE_ANNOTATION)) {
                RootBeanDefinition annotation = new RootBeanDefinition(ApplicationManager.class);
                logger.info("Application:{}", ApplicationManager.getApplicationContext());
                parserContext.getRegistry().registerBeanDefinition(QSCHEDULE_ANNOTATION, annotation);
            }
            logger.info("register done");

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        new Server("127.0.0.1", port).start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            logger.info("start done");


        }
    }
}
