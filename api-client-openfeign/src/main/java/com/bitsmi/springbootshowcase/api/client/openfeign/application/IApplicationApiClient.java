package com.bitsmi.springbootshowcase.api.client.openfeign.application;

import com.bitsmi.springbootshowcase.api.application.IApplicationApiController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "applicationApiClient",
        url = "${web.api.client.url:}",
        path = "/api/application"
)
public interface IApplicationApiClient extends IApplicationApiController
{

}
