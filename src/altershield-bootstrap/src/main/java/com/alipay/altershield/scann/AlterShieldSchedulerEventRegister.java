/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.scann;

import com.alipay.altershield.shared.schedule.event.AlterShieldSchedulerEvent;
import com.alipay.altershield.schedule.event.AlterShieldSchedulerEventHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The type Ops cloud scheduler event register.
 *
 * @author yuanji
 * @version : OpsCloudSchedulerEventRegister.java, v 0.1 2022年08月15日 19:26 yuanji Exp $
 */
public class AlterShieldSchedulerEventRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware,
        EnvironmentAware {

    private Environment    environment;
    private ResourceLoader resourceLoader;
    private static final Logger logger = LoggerFactory.getLogger(AlterShieldSchedulerEventRegister.class);


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        if (!annotationMetadata.hasAnnotation(AlterShieldSchedulerEventScan.class.getName())) {
            return;
        }
        Map<String, Object> annotationAttributesMap = annotationMetadata.getAnnotationAttributes(AlterShieldSchedulerEventScan.class.getName());
        AnnotationAttributes annotationAttributes = Optional.ofNullable(AnnotationAttributes.fromMap(annotationAttributesMap)).orElseGet(
                AnnotationAttributes::new);
        // Get the package to be scanned
        String[] packages = retrievePackagesName(annotationMetadata, annotationAttributes);
        // useDefaultFilters = false, that is, the second parameter means that the classes marked with @ Component, @ ManagedBean and @
        // Named annotations are not scanned
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false, environment, resourceLoader);
        // Add our custom annotation scan
        scanner.addIncludeFilter(new AnnotationTypeFilter(com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent.class));
        // Scan package
        for (String needScanPackage : packages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(needScanPackage);
            try {
                registerCandidateComponents(candidateComponents);
            } catch (ClassNotFoundException e) {
                logger.error("register schedulerEvent fail", e);
            }
        }
    }

    /**
     * Get the package to be scanned
     */
    private String[] retrievePackagesName(AnnotationMetadata annotationMetadata, AnnotationAttributes annotationAttributes) {
        String[] packages = annotationAttributes.getStringArray("basePackages");
        if (packages.length > 0) {
            return packages;
        }
        String className = annotationMetadata.getClassName();
        int lastDot = className.lastIndexOf('.');
        return new String[] {className.substring(0, lastDot)};
    }

    /**
     * Register BeanDefinition
     */
    private void registerCandidateComponents(Set<BeanDefinition> candidateComponents)
            throws ClassNotFoundException {
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                Map<String, Object> customImportAnnotationAttributesMap = annotationMetadata.getAnnotationAttributes(
                        com.alipay.altershield.shared.schedule.event.annotations.SchedulerEvent.class.getName());
                AnnotationAttributes customImportAnnotationAttributes = Optional.ofNullable(
                        AnnotationAttributes.fromMap(customImportAnnotationAttributesMap)).orElseGet(AnnotationAttributes::new);
                String beanName = customImportAnnotationAttributes.getString("value");
                String className = annotationMetadata.getClassName();
                Class<? extends AlterShieldSchedulerEvent> clazz = (Class<? extends AlterShieldSchedulerEvent>) Class.forName(className);
                logger.info("register schedulerEvent: {}, type={}", beanName,className);
                AlterShieldSchedulerEventHolder.addEventType(beanName, clazz);
            }
        }
    }

}