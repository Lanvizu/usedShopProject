package toymay.usedshop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toymay.usedshop.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
