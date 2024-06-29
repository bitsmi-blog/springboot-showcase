package com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.PrototypeService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SimplePrototypeServiceImpl implements PrototypeService
{

}
