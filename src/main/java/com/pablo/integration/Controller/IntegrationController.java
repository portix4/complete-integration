package com.pablo.integration.Controller;

import com.pablo.integration.Service.IntegrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationController {

    private final IntegrationService service;

    public IntegrationController(IntegrationService service) {
        this.service = service;
    }

    @PostMapping("/integration")
    public String integrationController(){
        return service.integrationService();
    }
}
