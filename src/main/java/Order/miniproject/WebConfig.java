package Order.miniproject;

import Order.miniproject.filter.LogFilter;
import Order.miniproject.filter.LoginFilter;
import Order.miniproject.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");
  }

  //@Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> fRBean =
        new FilterRegistrationBean<>();
    fRBean.setFilter(new LogFilter());
    fRBean.setOrder(1);
    fRBean.addUrlPatterns("/*");
    return fRBean;
  }

  //@Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean<Filter> fRBean = new FilterRegistrationBean<>();
    fRBean.setFilter(new LoginFilter());
    fRBean.setOrder(2);
    fRBean.addUrlPatterns("/*");
    return fRBean;
  }
}