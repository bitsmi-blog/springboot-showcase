package com.bitsmi.springbootshowcase.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag("ManualTest")
public class SetupTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SetupTests.class);

    private static final String HOST = "http://localhost:8080";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client;

    @Test
    public void createAdminUser() throws Exception
    {
        String payload = """
                    {
                        "username": "admin",
                        "password": "test"
                    }
                """;

        RequestBody body = RequestBody.create(payload, JSON);
        Request request = new Request.Builder()
                .url(HOST + "/api/setup/user")
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute()) {
            var responseBody = response.body()!=null ? response.body().string() : null;
            LOGGER.info("status: {}; message: {}", response.code(), responseBody);
        }
    }

    @Test
    public void applicationHello() throws Exception
    {
        Request request = new Request.Builder()
                .url(HOST + "/api/application/hello")
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            var responseBody = response.body()!=null ? response.body().string() : null;
            LOGGER.info("status: {}; message: {}", response.code(), responseBody);
        }
    }

    @Test
    public void auth() throws Exception
    {
        var basicAuth = new String(Base64.getEncoder().encode("admin:test".getBytes(StandardCharsets.UTF_8)));

        Request request = new Request.Builder()
                .url(HOST + "/auth")
                .header("Authorization", "Basic " + basicAuth)
                .post(RequestBody.create(null, ""))
                .build();

        try(Response response = client.newCall(request).execute()) {
            var responseBody = response.body()!=null ? response.body().string() : null;
            LOGGER.info("status: {}; message: {}", response.code(), responseBody);
        }
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp()
    {
        client = new OkHttpClient();
    }
}
