package Order.miniproject.repository;

import Order.miniproject.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em;

  public void save(Member member){
    em.persist(member);
  }

  public Member findById(Long id){
    return em.find(Member.class, id);
  }

  public List<Member> findByName(String name){
    return em.createQuery("select m from Member m where m.name =:name", Member.class)
        .setParameter("name", name)
        .getResultList();
  }

  public Optional<Member> findByLoginId(String loginId){
//  1. 로그인id가 같은 것만 리스트 가져와서 가장 처음것을 찾는 방법
//    Optional<Member> member = em.createQuery("select m from Member m where m.loginId =:loginId", Member.class)
//                                .setParameter("loginId", loginId)
//                                .getResultList()
//                                .stream()
//                                .findFirst();
//    return member;
// 1 이나 2 중 아무것이나 해도 동일한 결과임.
// 2. 전부 가져온 다음 스트림에서 로그인id가 같은것만 찾는 방법
    Optional<Member> member = em.createQuery("select m from Member m ", Member.class)
        .getResultList()
        .stream()
        .filter(m -> m.getLoginId().equals(loginId))
        .findAny();
    return member;
  }

  public List<Member> findAll(){
    return em.createQuery("select m from Member m", Member.class)
        .getResultList();
  }
}
