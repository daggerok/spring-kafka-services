package h2;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Log4j2
@Service
class TcpServer {

  private Server tcpServer;

  @SneakyThrows
  @PostConstruct
  public void postConstruct() {
    tcpServer = Server.createTcpServer("-tcp", "-tcpPort", "9091", "-tcpAllowOthers",
                                       //"-pg", "-pgPort", "5435", "-pgAllowOthers",
                                       "-ifNotExists", "-baseDir", "./target")
                      .start();
    log.info("{} {}", tcpServer.getStatus(), tcpServer.getURL());
  }

  @PreDestroy
  public void preDestroy() {
    if (Objects.nonNull(tcpServer) && tcpServer.isRunning(true))
      tcpServer.shutdown();
  }
}

@Log4j2
@Service
class WebServer {

  private Server webServer;

  @SneakyThrows
  @PostConstruct
  public void postConstruct() {
    webServer = Server.createWebServer("-web", "-webPort", "9080", "-webAllowOthers", "-ifNotExists")
                      .start();
    log.info("{} {}", webServer.getStatus(), webServer.getURL());
  }

  @PreDestroy
  public void preDestroy() {
    if (Objects.nonNull(webServer) && webServer.isRunning(true))
      webServer.shutdown();
  }
}

@SpringBootApplication
public class H2App {

  public static void main(String[] args) {
    var applicationContext = SpringApplication.run(H2App.class, args);
    applicationContext.registerShutdownHook();
  }
}
