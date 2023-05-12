package Order.miniproject.controller;

import Order.miniproject.Service.ItemService;
import Order.miniproject.domain.Book;
import Order.miniproject.domain.Item;
import Order.miniproject.domain.dto.BookDto;
import Order.miniproject.domain.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/addItem")
  /// 로그인 여부 체크 로직
  public String addItem(){
    return "items/addItem";
  }

  @PostMapping("/addItem")
  public String addItemProcess(@ModelAttribute BookDto bookDto){
    /// 로그인 여부 체크 로직
    Book book = new Book();
    book.setName(bookDto.getName());
    book.setPrice(bookDto.getPrice());
    book.setStockQuantity(bookDto.getStockQuantity());
    book.setAuthor(bookDto.getAuthor());
    book.setIsbn(bookDto.getIsbn());
    itemService.saveItem(book);
    return "redirect:/items/itemList";
  }

  @GetMapping("/itemList")
  public String itemList(
      HttpServletRequest request,
      Model model) {

    HttpSession session = request.getSession(false);
    if(session == null){
      return "redirect:/home";
    }
    // 세션이 존재하면
    SessionMember findMember = (SessionMember)session.getAttribute("loginMember");
    if(findMember == null) {
      return "redirect:/home";
    } else {
      model.addAttribute("items", itemService.findAllItems());
      return "items/itemList";
    }
  }

  @GetMapping("/itemInfo/{id}")
  public String itemInfo(@PathVariable Long id,
                         Model model) {

    /// 로그인 여부 체크 로직
    Book item = (Book)itemService.findItem(id);

    BookDto bookDto = new BookDto(item.getId(), item.getName(), item.getPrice(),item.getStockQuantity(),
                                  item.getAuthor(),item.getIsbn());
    model.addAttribute("item", bookDto);
    return "items/itemInfo";
  }

  @GetMapping("/updateItem/{id}")
  public String updateItem(@PathVariable Long id, Model model){
    /// 로그인 여부 체크 로직
    Book item = (Book)itemService.findItem(id);
    BookDto bookDto = new BookDto(item.getId(),item.getName(),item.getPrice(),item.getStockQuantity(),
                                  item.getAuthor(),item.getIsbn());
    model.addAttribute("item",bookDto);
    return "items/updateItem";    
  }

  @PostMapping("/updateItem/{id}")
  /// 로그인 여부 체크 로직
  public String updateItemProcess(@PathVariable Long id,
                                  @ModelAttribute BookDto bookDto){
    itemService.updateItem(id, bookDto);
    return "redirect:/items/itemList";
  }
}
