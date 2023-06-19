package toymay.usedshop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toymay.usedshop.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
