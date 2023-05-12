package Order.miniproject.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("logFilter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest =  (HttpServletRequest)request;
    String requestURI = httpRequest.getRequestURI();

    String uuid = UUID.randomUUID().toString();

    log.info("REQUEST: [{}], [{}]", uuid, requestURI);
    chain.doFilter(request,response);
    log.info("RESPONSE: [{}], [{}]", uuid, requestURI);
  }

  @Override
  public void destroy() {
    log.info("logFilter destroy");
  }
}
