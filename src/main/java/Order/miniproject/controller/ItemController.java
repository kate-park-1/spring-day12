package Order.miniproject.controller;

import Order.miniproject.Service.ItemService;
import Order.miniproject.domain.Book;
import Order.miniproject.domain.Item;
import Order.miniproject.domain.dto.BookDto;
import Order.miniproject.domain.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/addItem")
  /// 로그인 여부 체크 로직
  public String addItem(Model model){ // 1. Model 추가
    BookDto bookDto = new BookDto(); // 2-2.
    model.addAttribute("item", bookDto); // 2-3.
    return "items/addItem";
  }

  @PostMapping("/addItem")
  public String addItemProcess(@ModelAttribute("item") BookDto bookDto,
                               BindingResult bindingResult,
                               Model model){
    // 유효성 검사 - validation
//    Map<String, String> errors = new HashMap<>();

    log.info("bookDto -> {}", bookDto);

    if (!StringUtils.hasText(bookDto.getName())){
      bindingResult.addError(new FieldError("item", "name",  "상품명은 필수입니다."));
    }
    if(bookDto.getPrice() == null || bookDto.getPrice() < 1000 || bookDto.getPrice() > 1000000){
      bindingResult.addError(new FieldError("item", "price","상품가격은 1000원보다 크고, 1000000보다 작아야 합니다."));
    }
    if(bookDto.getStockQuantity() == null || bookDto.getStockQuantity() < 1 || bookDto.getStockQuantity() > 10000){
      bindingResult.addError(new FieldError("item","stockQuantity","상품수량은 1보다 크고, 10000보다 작아야 합니다."));
    }

    if(bookDto.getStockQuantity() != null && bookDto.getPrice() != null){
      // 여러개의 오브젝트 에러 추가할 수 있음.
      if(bookDto.getStockQuantity() * bookDto.getPrice() < 10000) {
        bindingResult.addError(new ObjectError("item", "가격*수량은 10,000원 이상이어야 합니다."));
      }
    }

    if(bindingResult.hasErrors()){
      model.addAttribute("item",bookDto);
      return "items/addItem";
    }

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
  public String updateItemProcess(@PathVariable Long id,
                                  @ModelAttribute BookDto bookDto,
                                  Model model){

    // 유효성 검사 - validation
    Map<String, String> errors = new HashMap<>();
    if (!StringUtils.hasText(bookDto.getName())){
      errors.put("name","상품명은 필수입니다.");
    }
    if(bookDto.getPrice() == null || bookDto.getPrice() < 1000 || bookDto.getPrice() > 1000000){
      errors.put("price","상품가격은 1000원보다 크고, 1000000보다 작아야 합니다.");
    }
    if(bookDto.getStockQuantity() == null || bookDto.getStockQuantity() < 1 || bookDto.getStockQuantity() > 10000){
      errors.put("stockQuantity","상품수량은 1보다 크고, 10000보다 작아야 합니다.");
    }

    if(bookDto.getStockQuantity() != null && bookDto.getPrice() != null){
      if(bookDto.getStockQuantity() * bookDto.getPrice() < 10000) {
        errors.put("globalError","가격*수량은 10,000원 이상이어야 합니다.");
      }
    }

    if(!errors.isEmpty()){
      model.addAttribute("errors",errors);
      return "items/addItem";
    }
    itemService.updateItem(id, bookDto);
    return "redirect:/items/itemList";
  }
}
