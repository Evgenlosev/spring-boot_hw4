package com.geekbrains.spring.web.cart.configs;

import com.geekbrains.spring.web.cart.properties.ProductServiceIntegrationProperties;
import com.geekbrains.spring.web.cart.properties.ProductServiceIntegrationTimeouts;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({ProductServiceIntegrationProperties.class, ProductServiceIntegrationTimeouts.class})
@RequiredArgsConstructor
public class AppConfig {
    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;
    private final ProductServiceIntegrationTimeouts productServiceIntegrationTimeouts;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationTimeouts.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationTimeouts.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationTimeouts.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(productServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
