package com.pablo.integration.Service;

import com.pablo.integration.Model.Objeto;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    public Objeto integrationService() {

        Objeto obj = new Objeto("Pablo");
        return obj;
    }
}
