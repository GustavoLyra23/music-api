package com.gustavolyra.music_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ItunesClientConfig {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        WebClient builder = WebClient.builder().baseUrl("https://itunes.apple.com").build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(builder)).build();
    }

    @Bean
    public ItunesClient itunesClient(HttpServiceProxyFactory factory) {
        return factory.createClient(ItunesClient.class);
    }

}
