package Order.miniproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="member_id")
  private Long id;
  @Column(nullable = false, length = 45)
  private String name;
  @Column(name="login_id", nullable = false, length = 10, unique = true)
  private String loginId;
  @Column(nullable = false, length = 10)
  private String password;

  @Embedded
  private Address address;

  @Override
  public String toString() {
    return "Member : id("+ id + ")" +
            " ,loginId : " +  loginId +
            " ,name : " + name ;
  }
}
