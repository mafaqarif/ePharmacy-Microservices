package com.example.apigateway.Filters;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;


@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/authenticate/register",
            "/authenticate/token",
            "/eureka"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
