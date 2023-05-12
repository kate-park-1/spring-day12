package Order.miniproject.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order" , cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Column(name="order_date")
  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  @Column(name="order_status")
  private OrderStatus orderStatus;

  public void addOrderItem(OrderItem orderItem){
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public static Order createOrder(Member member, OrderItem... orderItems){
    Order order = new Order();
    order.setMember(member);
    for(OrderItem orderItem : orderItems){
      order.addOrderItem(orderItem);
    }
    order.setOrderDate(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.ORDER);
    return order;
  }

  public void cancelOrder(){ // 전체 주문 취소
    this.setOrderStatus(OrderStatus.CANCEL);
    for(OrderItem orderItem : orderItems){
      orderItem.cancel();
    }
  }

}
