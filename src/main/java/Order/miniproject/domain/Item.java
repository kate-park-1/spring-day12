package Order.miniproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="item_id")
  private Long id;
  @Column(length = 125, nullable = false)
  private String name;
  private int price;
  @Column(name="stock_quantity")
  private int stockQuantity;

  public void addStock(int quantity){
    this.stockQuantity += quantity; // 재고증가 => 주문 취소시 취소 수량만큼 증가
  }

  public void decreaseStock(int quantity){
    int remainderStock = this.stockQuantity - quantity;
    if(remainderStock >= 0){
      this.stockQuantity = remainderStock ; // 재고감소 => 주문시 감소, 재고수량보다 작거나 같은 경우에만 감소
    } else {
      throw new RuntimeException("not enough stock to decrease");
    }
  }
}
