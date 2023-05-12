package Order.miniproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @Column(name = "order_price")
  private int orderPrice;  // 주문금액

  @Column(name = "order_quantity")
  private int orderQuantity; // 주문수량

  public void cancel(){
    //주문수량 => 재고(+)반영
    item.addStock(this.orderQuantity);
  }

  public static OrderItem createOrderItem(Item item, int orderPrice, int orderQuantity){
    OrderItem orderItem = new OrderItem();
    orderItem.item = item;
    orderItem.orderPrice = orderPrice;
    orderItem.orderQuantity = orderQuantity;
    item.decreaseStock(orderQuantity);
    return orderItem;
  }

}
