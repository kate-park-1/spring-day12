package Order.miniproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor   // 생성자 추가
@ToString // 메서드 추가
public class BookDto {
  private Long id; /// 필드 추가
  private String name;
  private int price;
  private int stockQuantity;
  private String author;
  private String isbn;
}
//bookform, memberform 삭제