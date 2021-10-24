package com.fengxudong.api.router;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Random;

@Component
public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public URL route(List<URL> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
