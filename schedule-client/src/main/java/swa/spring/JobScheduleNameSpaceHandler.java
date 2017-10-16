package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 解析ｘｍｌ中的
 * Created by jinyan on 10/11/17 6:37 PM.
 */
public class JobScheduleNameSpaceHandler extends NamespaceHandlerSupport{
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleNameSpaceHandler.class);

    public void init() {
        registerBeanDefinitionParser("config",new JobScheduleBeanDefinitionParser());
    }
}
