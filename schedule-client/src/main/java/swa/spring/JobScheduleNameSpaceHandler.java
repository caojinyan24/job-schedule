package swa.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 解析ｘｍｌ中的
 * Created by jinyan on 10/11/17 6:37 PM.
 */
public class JobScheduleNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("config", new JobScheduleBeanDefinitionParser());
    }


}
