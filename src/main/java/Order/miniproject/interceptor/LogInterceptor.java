package Order.miniproject.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String uuid = UUID.randomUUID().toString();
    String requestUri = request.getRequestURI();
    request.setAttribute("log", uuid);
    log.info("LogInterceptor 시작 : [{}], [{}] , [{}]",requestUri,uuid,handler);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    String uuid = (String)request.getAttribute("log");
    String requestUri = request.getRequestURI();
    log.info("LogInterceptor 종료 : [{}], [{}] , [{}]",requestUri,uuid,handler);
    if(ex != null){
      log.error("LogInterceptor exception : [{}]",ex);
    }
  }
}
