package Order.miniproject.controller;

import Order.miniproject.Service.MemberService;
import Order.miniproject.domain.Member;
import Order.miniproject.domain.dto.LoginDto;
import Order.miniproject.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/addMember")
  public String addMember(Model model){
    MemberDto member = new MemberDto();
    model.addAttribute("member", member);
    return "members/addMember";
  }

  @PostMapping("/addMember")
  public String addMembersProcess(@ModelAttribute("member") MemberDto memberDto){
    Long id = memberService.join(memberDto);
    System.out.println(memberDto);
    return "redirect:/members/login";
  }

  @GetMapping("/login")
  public String login(Model model){
    LoginDto login = new LoginDto();
    log.info("loginDto : " + login);
    model.addAttribute("login", login);
    return "members/login";
  }

  @PostMapping("/login")
  public String loginProcess(@ModelAttribute("login") LoginDto loginDto,
                             HttpServletResponse resp){
    Member loginMember = memberService.login(loginDto);
    if(loginMember == null){
      //로그인 실패
      log.info("==== 로그인 post ==== 실패");
      return "members/login";
    } else {
      // 로그인 성공
      // 쿠키를 응답에 담아서 클라이언트로 전송
      log.info("==== 로그인 post ==== 성공");
      Cookie cookie = new Cookie("memberId2", String.valueOf(loginMember.getId()));
      cookie.setPath("/");
      resp.addCookie(cookie);
      return "redirect:/home";
    }
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest req, HttpServletResponse resp) {
    Cookie[] cookies = req.getCookies();
    for(Cookie cookie : cookies){
      if(cookie.getName().equals("memberId2")){
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
      }
    }
    return "redirect:/";
  }
}
