package com.fengxudong.api.router;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

/**
 * 轮询
 */
@Component
public class RoundRobinEndpointRouter implements HttpEndpointRouter {
    private int index = 0;

    @Override
    public synchronized URL route(List<URL> endpoints) {
        int size = endpoints.size();
        int next = (index + 1) % size;
        index = next;
        return endpoints.get(index);
    }

}
