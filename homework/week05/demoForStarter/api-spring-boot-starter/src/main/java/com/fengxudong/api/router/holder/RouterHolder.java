package com.fengxudong.api.router.holder;

import com.fengxudong.api.router.HttpEndpointRouter;

import java.net.URL;
import java.util.List;

public class RouterHolder {

    private static HttpEndpointRouter ROUTER;

    public RouterHolder(HttpEndpointRouter router){
        RouterHolder.ROUTER = router;
    }

    public static URL route(List<URL> endpoints){
        return ROUTER.route(endpoints);
    }

    public static HttpEndpointRouter getROUTER() {
        return ROUTER;
    }
}
