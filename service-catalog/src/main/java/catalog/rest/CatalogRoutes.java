package catalog.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class CatalogRoutes {

  @Bean
  RouterFunction<ServerResponse> routes(CatalogHandlers handlers) {
    return route().nest(path("/api/v1/products"), builder -> builder.POST("/**", handlers::post)
                                                                    .GET("/{aggregateId}", handlers::getOne)
                                                                    .GET("/**", handlers::getAll)
                                                                    .build())
                  .GET("/**", handlers::info)
                  .build();
  }
}
