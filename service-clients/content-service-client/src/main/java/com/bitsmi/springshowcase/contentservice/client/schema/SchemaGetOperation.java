package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class SchemaGetOperation
{
    public static final String ENDPOINT_PATH = "/api/schema/{id}";

    private final RestClient restClient;

    private final Long id;

    SchemaGetOperation(RestClient restClient, Long id)
    {
        this.restClient = restClient;
        this.id = id;
    }

    /**
     * @return @link {@link Optional}&lt;{@link ItemSchema}&gt; -
     */
    public Optional<ItemSchema> get()
    {
        validateRequest();

        return Optional.ofNullable(
            restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(ItemSchema.class)
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
