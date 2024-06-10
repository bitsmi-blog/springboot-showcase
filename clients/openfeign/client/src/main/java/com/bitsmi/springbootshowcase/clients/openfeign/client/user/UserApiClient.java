package com.bitsmi.springbootshowcase.clients.openfeign.client.user;

import com.bitsmi.springbootshowcase.clients.openfeign.api.user.UserApi;
import com.bitsmi.springbootshowcase.clients.openfeign.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userApiClient",
        url = "${web.api.client.url:}",
        path = "/api/user",
        // Optional
        configuration = FeignConfig.class
)
public interface UserApiClient extends UserApi
{

}
