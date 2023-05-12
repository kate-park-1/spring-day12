package Order.miniproject.Service;

import Order.miniproject.domain.Book;
import Order.miniproject.domain.Item;
import Order.miniproject.domain.dto.BookDto;
import Order.miniproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
  private final ItemRepository itemRepository;

  @Transactional
  public Long saveItem(Item item) {
    itemRepository.save(item);
    return item.getId();
  }

  @Transactional
  public void updateItem(Long id, BookDto bookDto) {
    Book book = (Book)itemRepository.findById(id);
    book.setName(bookDto.getName());
    book.setPrice(bookDto.getPrice());
    book.setStockQuantity(bookDto.getStockQuantity());
    book.setAuthor(bookDto.getAuthor());
    book.setIsbn(bookDto.getIsbn());
    //System.out.println(bookDto.toString());
  }

  public List<Item> findAllItems() {
    return itemRepository.findAll();
  }

  public Item findItem(Long id) {
    return itemRepository.findById(id);
  }
}
