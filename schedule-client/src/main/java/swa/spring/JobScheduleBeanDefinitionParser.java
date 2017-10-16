package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by jinyan on 10/11/17 6:38 PM.
 */
public class JobScheduleBeanDefinitionParser implements BeanDefinitionParser {
private static final Logger logger= LoggerFactory.getLogger(JobScheduleBeanDefinitionParser.class);

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}
