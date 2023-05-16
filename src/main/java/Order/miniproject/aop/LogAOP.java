//package Order.miniproject.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Component
//@Aspect
//@Slf4j
//public class LogAOP {
//  /* Order.miniproject.controller.ItemController 아래의 모든 메서드에 대해 적용한다.*/
//  @Around("execution(* Order.miniproject.controller.ItemController.*(..))")
//  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
//    Object result = joinPoint.proceed();
//    log.info("LogAop Root : [{}] ", joinPoint.getSignature().getDeclaringTypeName());
//    log.info("LogAop Method : [{}] ", joinPoint.getSignature().getName());
//    return result;
//  }
//}
