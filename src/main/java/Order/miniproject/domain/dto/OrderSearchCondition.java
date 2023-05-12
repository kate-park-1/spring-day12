package Order.miniproject.domain.dto;

import Order.miniproject.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearchCondition {
  private String memberName;
  private OrderStatus orderStatus;
}
