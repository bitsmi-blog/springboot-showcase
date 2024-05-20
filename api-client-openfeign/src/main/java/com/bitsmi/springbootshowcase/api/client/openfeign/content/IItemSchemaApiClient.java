package com.bitsmi.springbootshowcase.api.client.openfeign.content;

import com.bitsmi.springbootshowcase.api.client.openfeign.config.FeignConfig;
import com.bitsmi.springbootshowcase.api.common.request.PaginatedRequest;
import com.bitsmi.springbootshowcase.api.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.api.content.IItemSchemaApi;
import com.bitsmi.springbootshowcase.api.content.response.ItemSchema;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "itemSchemaApiClient",
        url = "${web.api.client.url:}",
        path = "/api/content/schema",
        configuration = FeignConfig.class
)
public interface IItemSchemaApiClient extends IItemSchemaApi
{
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    PaginatedResponse<ItemSchema> getSchemas(@SpringQueryMap final PaginatedRequest paginatedRequest);
}
