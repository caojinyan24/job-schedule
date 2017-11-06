package swa.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import swa.job.ApplicationLoader;
import swa.job.schedule.Server;


/**
 * Created by jinyan on 10/11/17 6:38 PM.
 */
public class JobScheduleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
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
        if (element.getAttribute("port") == null || element.getAttribute("port") == "") {
            throw new IllegalArgumentException("port must be specified!");
        }
        if (element.getAttribute("appName") == null || element.getAttribute("appName") == "") {
            throw new IllegalArgumentException("application name must be specified!");
        }
        BeanParser.setAppName(element.getAttribute("appName"));

        if (!parserContext.getRegistry().containsBeanDefinition(QSCHEDULE_ANNOTATION)) {
            RootBeanDefinition applicationLoader = new RootBeanDefinition(ApplicationLoader.class);
            parserContext.getRegistry().registerBeanDefinition(QSCHEDULE_ANNOTATION, applicationLoader);
        }
        final Integer port = Integer.valueOf(element.getAttribute("port"));
        BeanParser.setPORT(port);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    new Server("127.0.0.1", port).start();//todo：填入schedule服务器地址，后期考虑动态获取
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}

