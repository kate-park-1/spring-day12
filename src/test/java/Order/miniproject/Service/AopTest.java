package Order.miniproject.Service;

import Order.miniproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class AopTest {
  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;

  @Test
  void aopTest() {
    log.info("isAopProxy, memberService = {}", AopUtils.isAopProxy(memberService));
    log.info("isAopProxy, memberRepositoService = {}", AopUtils.isAopProxy(memberRepository));
  }

  @Test
  void success() {
    memberService.findOneMember(1L);
  }

  @Test
  void exception(){
    Assertions.assertThatThrownBy(()->memberService.findOneMember(10L));
  }
}
