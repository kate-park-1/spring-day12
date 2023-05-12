package Order.miniproject.Service;

import Order.miniproject.domain.Address;
import Order.miniproject.domain.Member;
import Order.miniproject.domain.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

  private final MemberService memberService;
  @Autowired
  public MemberServiceTest(MemberService memberService){
    this.memberService = memberService;
  }

  @Test
  void join() {
    MemberDto member = new MemberDto();
    member.setName("test");
    Address address = new Address("seoul", "doksan", "11111");
    member.setAddress(address);
    // when
    Long joinId = memberService.join(member);
    // then
    Member oneMember = memberService.findOneMember(joinId);
    assertThat(joinId).isEqualTo(oneMember.getId());
  }

  @Test
  void findAllMembers() {
    MemberDto m1 = new MemberDto();
    m1.setName("test");
    Address address = new Address("seoul", "doksan", "11111");
    m1.setAddress(address);

    MemberDto m2 = new MemberDto();
    m2.setName("test");
    Address address1 = new Address("seoul", "kasan", "11112");
    m2.setAddress(address1);
    // when
    Long joinId1 = memberService.join(m1);
    Long joinId2 = memberService.join(m2);
    // then
    List<Member> members = memberService.findAllMembers();
    assertThat(2).isEqualTo(members.size());
  }
}