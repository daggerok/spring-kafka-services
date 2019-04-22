package catalog.rest;

import catalog.domain.Product;
import catalog.domain.Products;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CatalogHandlers {

  private final Products products;

  public ServerResponse info(ServerRequest request) {
    var requests = Map.of("get all products: GET", url(request, "/api/v1/products"),
                          "find one product: GET", url(request, "/api/v1/products/{aggregateId}"),
                          "post new product: POST", url(request, "/api/v1/products name={productName} qty={amount}"));
    return ServerResponse.ok().body(requests);
  }

  private static String url(ServerRequest request, String suffix) {
    return format("%s://%s%s", request.uri().getScheme(), request.uri().getAuthority(), suffix);
  }

  public ServerResponse getAll(ServerRequest request) {
    var page = parseFrom(request, "page", "0");
    var size = parseFrom(request, "size", "20");
    var pagination = PageRequest.of(page, size);
    return ServerResponse.ok().body(products.findAll(pagination));
  }

  private static Integer parseFrom(ServerRequest request, String name, String defaultValue) {
    var params = request.params();
    var namedParams = params.get(name);
    var param = namedParams.stream().findFirst().orElse(defaultValue);
    return Integer.parseInt(param);
  }

  @SneakyThrows
  public ServerResponse post(ServerRequest request) {
    var type = new ParameterizedTypeReference<Map<String, String>>() {};
    var map = request.body(type);
    var name = map.getOrDefault("name", null);
    if (Objects.isNull(name)) return ServerResponse.badRequest().body(Map.of("error", "name is required"));
    var quantity = map.getOrDefault("qty", null);
    if (Objects.isNull(quantity)) return ServerResponse.badRequest().body(Map.of("error", "qty is required"));
    var qty = Long.parseLong(quantity);
    var product = Product.of(name, qty);
    return ServerResponse.ok().body(products.save(product.toCreateProduct()));
  }

  public ServerResponse getOne(ServerRequest request) {
    String aggregateId = request.pathVariable("aggregateId");
    UUID id = UUID.fromString(aggregateId);
    Optional<Product> maybeProducts = products.findById(id);
    return maybeProducts.isEmpty()
        ? ServerResponse.notFound().build()
        : ServerResponse.ok().body(maybeProducts.get());
  }
}
