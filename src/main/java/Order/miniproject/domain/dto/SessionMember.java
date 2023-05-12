package Order.miniproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // setter test
public class SessionMember {
  private Long id;
  private String loginId;
  private String name;
}
