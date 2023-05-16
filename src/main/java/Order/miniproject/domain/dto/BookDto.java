package Order.miniproject.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor   // 생성자 추가
@NoArgsConstructor  // 2-1. 생성자 추가
@ToString // 메서드 추가
public class BookDto {
  private Long id; /// 필드 추가
  private String name;
  private Integer price;
  private Integer stockQuantity;
  private String author;
  private String isbn;
}
//bookform, memberform 삭제