package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class WebTestBase {

    /*-----------------------*
     * APPLICATION MODULE
     *-----------------------*/
    @MockitoBean
    protected UserRetrievalApplicationService userRetrievalApplicationServiceMock;
    @MockitoBean
    protected UserRegistryApplicationService userRegistryApplicationServiceMock;

    /*-----------------------*
     * DOMAIN MODULE
     *-----------------------*/
    @MockitoBean
    protected UserDomainRepository userDomainRepositoryMock;
}
