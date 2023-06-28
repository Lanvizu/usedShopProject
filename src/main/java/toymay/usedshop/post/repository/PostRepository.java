package toymay.usedshop.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;
import toymay.usedshop.post.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    //역순 배열
    List<Post> findAllByOrderByIdDesc();
}
