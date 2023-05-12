package Order.miniproject.repository;

import Order.miniproject.domain.Order;
import Order.miniproject.domain.OrderStatus;
import Order.miniproject.domain.QMember;
import Order.miniproject.domain.QOrder;
import Order.miniproject.domain.dto.OrderSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static Order.miniproject.domain.QMember.member;
import static Order.miniproject.domain.QOrder.order;

@Repository
public class OrderRepository {
  private final EntityManager em;
  private final JPAQueryFactory query;

  @Autowired
  public OrderRepository(EntityManager em){
    this.em = em;
    this.query = new JPAQueryFactory(em);
  }

  public void save(Order order){
    em.persist(order);
  }

  public Order findById(Long id){
    return em.find(Order.class,id);
  }

  public List<Order> findAll(){  // 동적쿼리로 수정 필요, 일자별,사용자별,상태별 검색조건
    return em.createQuery("select o from Order o", Order.class)
        .getResultList();
  }

  public List<Order> findAllByMemberAndOrderStatus() {

    //1)  id X , status X
    //2) id O , status X
    //3) id O , status O
    //4) id X , status O
    return em.createQuery("select o from Order o where o.id >= :id and o.orderStatus = :status",Order.class)
        .setParameter("id", 5L)
        .setParameter("status", OrderStatus.CANCEL)
        .getResultList();
  }

  public List<Order> findAllByCondition(OrderSearchCondition searchCondition){
    // queryDSL
    // name O.X , status O,X
    return query
              .select(order)
              .from(order)
              .join(order.member, member)
              .where(nameEq(searchCondition.getMemberName()),
                     statusEq(searchCondition.getOrderStatus()))
              .fetch();
  }

  private BooleanExpression statusEq(OrderStatus statusCondition){
    if(statusCondition == null){
      return null;
    }
    return order.orderStatus.eq(statusCondition);
  }

  private BooleanExpression nameEq(String nameCondition){
    if(!StringUtils.hasText(nameCondition)){
      return null;
    }
    return member.name.eq(nameCondition);
  }
}
