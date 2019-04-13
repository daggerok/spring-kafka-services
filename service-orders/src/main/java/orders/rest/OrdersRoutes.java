package orders.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class OrdersRoutes {

  @Bean
  RouterFunction<ServerResponse> routes(OrdersHandlers handlers) {
    return route().nest(path("/api/v1/product-infos"), builder -> builder.GET("/{aggregateId}", handlers::getOne)
                                                                         .GET("/**", handlers::getAll)
                                                                         .build())
                  .GET("/**", handlers::info)
                  .build();
  }
}
