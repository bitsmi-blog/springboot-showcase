package com.bitsmi.springbootshowcase.clients.openfeign.client.application;

import com.bitsmi.springbootshowcase.clients.openfeign.api.application.ApplicationApi;
import com.bitsmi.springbootshowcase.clients.openfeign.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "applicationApiClient",
        url = "${web.api.client.url:}",
        path = "/api/application",
        // Optional
        configuration = FeignConfig.class
)
public interface ApplicationApiClient extends ApplicationApi
{

}
