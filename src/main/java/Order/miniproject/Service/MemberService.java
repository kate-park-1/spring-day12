package Order.miniproject.Service;

import Order.miniproject.domain.Member;
import Order.miniproject.domain.dto.LoginDto;
import Order.miniproject.domain.dto.MemberDto;
import Order.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long join(MemberDto memberDto){
    Member member = new Member();
    member.setName(memberDto.getName());
    member.setLoginId(memberDto.getLoginId());
    member.setPassword(memberDto.getPassword());
    member.setAddress(memberDto.getAddress());
    validateDuplicateMember(memberDto);
    memberRepository.save(member);
    return member.getId();
  }

  private void validateDuplicateMember(MemberDto memberDto){
    Optional<Member> findLoginId = memberRepository.findByLoginId(memberDto.getLoginId());
    System.out.println(findLoginId);
  }

  public List<Member> findAllMembers(){
    return memberRepository.findAll();
  }

  public Member findOneMember(Long id){
    return memberRepository.findById(id);
  }

  public Member login(LoginDto loginDto){
    Optional<Member> member = memberRepository.findByLoginId(loginDto.getLoginId());
    log.info("Optional<Member> member  ==> " + member);
    if(!member.isEmpty() && member.get().getPassword().equals(loginDto.getPassword())){
      return member.get();  // 로그인아이디 일치 && 패스워드 일치 => member,
    }
    return null; // 로그인아이디가 없거나, 패스워드가 일치하지 않으면 => 로그인 실패
  }
}
