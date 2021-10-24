package com.fengxudong.api.router;

import java.net.URL;
import java.util.List;

public interface HttpEndpointRouter {

    URL route(List<URL> endpoints);
    
    // Load Balance
    // Random
    // RoundRibbon 
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50
    
}
