package com.xiaomo.gateway.handler;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {


    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    //@Autowired(required = false)
    //private UiConfiguration uiConfiguration;

}
