package com.bitsmi.springbootshowcase.web.admin;

import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "admin.api")
public class AdminApiController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminApiController.class);

    @RequestMapping(value = { "", "/" }, produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasRole('admin.authority1')")
    public String getAdmin()
    {
        LOGGER.info("[getAdmin] Admin request");
        return "Response from Admin endpoint";
    }
}
