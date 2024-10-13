package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class GetCategoryOperation
{
    public static final String ENDPOINT_PATH = "/api/categories/{id}";

    private final RestClient restClient;

    private final Long id;

    GetCategoryOperation(RestClient restClient, Long id)
    {
        this.restClient = restClient;
        this.id = id;
    }

    /**
     * @return @link {@link Optional}&lt;{@link Category}&gt; -
     */
    public Optional<Category> get()
    {
        validateRequest();

        return Optional.ofNullable(
            restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(Category.class)
        );
    }

    private void validateRequest()
    {
        Objects.requireNonNull(id);
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        return uriBuilder.build(id);
    }
}
