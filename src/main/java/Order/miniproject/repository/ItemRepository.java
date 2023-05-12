package Order.miniproject.repository;

import Order.miniproject.domain.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em ;

  public void save(Item item) {
    em.persist(item);
  }

  public Item findById(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findByName(String name) {
    return em.createQuery("select i from Item i where i.name = :name", Item.class)
        .setParameter("name", name)
        .getResultList();
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
        .getResultList();
  }
}
