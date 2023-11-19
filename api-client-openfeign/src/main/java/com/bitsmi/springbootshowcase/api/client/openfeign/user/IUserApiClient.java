package com.bitsmi.springbootshowcase.api.client.openfeign.user;

import com.bitsmi.springbootshowcase.api.user.IUserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userApiClient",
        url = "${web.api.client.url:}",
        path = "/api/user"
)
public interface IUserApiClient extends IUserApi
{

}
