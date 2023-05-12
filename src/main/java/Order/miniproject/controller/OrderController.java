package Order.miniproject.controller;

import Order.miniproject.Service.ItemService;
import Order.miniproject.Service.MemberService;
import Order.miniproject.Service.OrderService;
import Order.miniproject.domain.dto.OrderSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  @GetMapping("/orderList")
  public String orderList(@ModelAttribute OrderSearchCondition orderSearch,
                          Model model){
    model.addAttribute("orders", orderService.getOrderbySearch(orderSearch));
    return "orders/orderList";
  }

  @GetMapping("/addOrder")
  public String addOrder(Model model){
    model.addAttribute("members", memberService.findAllMembers());
    model.addAttribute("items", itemService.findAllItems());
    return "orders/addOrder";
  }

  @PostMapping("/addOrder")
  public String addOrderProcess(@RequestParam Long memberId,
                                @RequestParam Long itemId,
                                @RequestParam int orderQuantity){
    orderService.saveOrder(memberId, itemId, orderQuantity);
    return "redirect:/orders/orderList";
  }

  @PostMapping("/cancel/{id}")
  public String cancelOrder(@PathVariable("id") Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders/orderList";
  }
}
