package com.bitsmi.springbootshowcase.springmvc.apiversioning.web.headersstrategy;

import com.bitsmi.springbootshowcase.springmvc.apiversioning.web.WebPackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc

@Tag("IntegrationTest")
class SampleApiIntTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @DisplayName("get sample should return V1 result when API V1 version header is provider")
    void getSampleTest1() throws Exception {
        this.mockMvc.perform(get("/api/headers-strategy/sample")
                        .header("X-Showcase-Api-Version", "V1")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("SampleV1"));
    }

    @Test
    @DisplayName("get sample should return V2 result when API V2 version header is provider")
    void getSampleTest2() throws Exception {
        this.mockMvc.perform(get("/api/headers-strategy/sample")
                        .header("X-Showcase-Api-Version", "V2")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("SampleV2"));
    }

    @Test
    @DisplayName("get sample should return bad request error when API version header is not provided")
    void getSampleTest3() throws Exception {
        this.mockMvc.perform(get("/api/headers-strategy/sample"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("detail").value("Missing API version selector header (X-Showcase-Api-Version)"));
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = { WebPackage.class },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    static class TestConfig {

    }
}
