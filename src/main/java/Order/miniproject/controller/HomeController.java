package Order.miniproject.controller;

import Order.miniproject.domain.Member;
import Order.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final MemberRepository memberRepository;
  @GetMapping("/")    // 로그인 안한 사용자는 home, 로그인 한 사용자는 loginHome 화면 보여지게 함.
  public String root(@CookieValue(name="memberId2",required = false) Long memberId,
                     Model model) {
    if(memberId == null) {
      return "home";
    }
    // cookie 값이 있음
    Member findMember = memberRepository.findById(memberId);
    if(findMember == null) {
      return "home";
    } else {
      model.addAttribute("name", findMember.getName());
      return "loginHome";
    }
  }

  @GetMapping("/home")
  public String home() {
    return "redirect:/";
  }
}
