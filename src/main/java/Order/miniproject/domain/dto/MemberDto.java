package Order.miniproject.domain.dto;

import Order.miniproject.domain.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto { // 변경
  Long id;
  String name;
  String loginId;
  String password;
  Address address;
}
