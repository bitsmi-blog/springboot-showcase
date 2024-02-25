package com.bitsmi.springbootshowcase.application.test.config;

import com.bitsmi.springbootshowcase.domain.common.IUserCommandDomainService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaCommandDomainService;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaQueryDomainService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(IUserCommandDomainService.class)
@MockBean(IItemSchemaQueryDomainService.class)
@MockBean(IItemSchemaCommandDomainService.class)
public class DomainModuleMockConfig
{

}
