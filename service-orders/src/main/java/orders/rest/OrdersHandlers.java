package orders.rest;

import lombok.RequiredArgsConstructor;
import orders.domain.ProductInfo;
import orders.domain.ProductsInfos;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrdersHandlers {

  private final ProductsInfos productsInfos;

  public ServerResponse info(ServerRequest request) {
    return ServerResponse.ok()
                         .body(Map.of("get all productsInfos: GET", url(request, "/api/v1/product-infos"),
                                      "get one productsInfo: GET", url(request, "/api/v1/product-infos/{aggregateId}")));
  }

  private static String url(ServerRequest request, String suffix) {
    return format("%s://%s%s", request.uri().getScheme(), request.uri().getAuthority(), suffix);
  }

  public ServerResponse getAll(ServerRequest request) {
    var page = parseFrom(request, "page", "0");
    var size = parseFrom(request, "size", "20");
    var pagination = PageRequest.of(page, size);
    return ServerResponse.ok().body(productsInfos.findAll(pagination));
  }

  private static Integer parseFrom(ServerRequest request, String name, String defaultValue) {
    var params = request.params();
    var namedParams = params.get(name);
    var param = namedParams.stream().findFirst().orElse(defaultValue);
    return Integer.parseInt(param);
  }

  public ServerResponse getOne(ServerRequest request) {
    String aggregateId = request.pathVariable("aggregateId");
    UUID id = UUID.fromString(aggregateId);
    Optional<ProductInfo> maybeProducts = productsInfos.findById(id);
    return maybeProducts.isEmpty()
        ? ServerResponse.notFound().build()
        : ServerResponse.ok().body(maybeProducts.get());
  }
}
