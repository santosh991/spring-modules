/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springmodules.validation.bean.conf.namespace;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springmodules.validation.util.LibraryUtils;

/**
 * A namespace handler for the <code>validation</code> namepsace. This namespace contains the following elements:
 * <ul>
 * <li>class - represents a class validation configuration</li>
 * <li>
 * validator - represents bean validator that is configured with the validation rules as defined by the
 * <code>class</code> element</li>
 * </ul>
 * 
 * @author Uri Boness
 */
public class ValidatorNamespaceHandler extends NamespaceHandlerSupport {

    /**
     * @see org.springframework.beans.factory.xml.NamespaceHandlerSupport#init()
     */
    public void init() {

        registerBeanDefinitionParser(ValangConditionParserDefinitionParser.ELEMENT_NAME,
                new ValangConditionParserDefinitionParser());

        registerBeanDefinitionParser(XmlBasedValidatorBeanDefinitionParser.ELEMENT_NAME,
                new XmlBasedValidatorBeanDefinitionParser());

        if (LibraryUtils.JDK_ANNOTATIONS_SUPPORTED) {

            /*
             * Attempts to register the jdk15 annotation handler. If its not on the classpath, will fail silently.
             */
            try {
                Class forName = Class
                        .forName("org.springmodules.validation.bean.conf.namespace.AnnotationBasedValidatorBeanDefinitionParser");

                BeanDefinitionParser annotationBVBDP = (BeanDefinitionParser) forName.newInstance();
                String name = (String) forName.getDeclaredField("ELEMENT_NAME").get(null);

                registerBeanDefinitionParser(name, annotationBVBDP);
            } catch (Exception e) {
                // do nothing, jdk15 must not be on classpath

            }

        }
    }

}
