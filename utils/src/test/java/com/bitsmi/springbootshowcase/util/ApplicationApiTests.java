package com.bitsmi.springbootshowcase.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("ManualTest")
public class ApplicationApiTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApiTests.class);

    private static final String HOST = "http://localhost:8080";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client;

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

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp()
    {
        client = new OkHttpClient();
    }
}
