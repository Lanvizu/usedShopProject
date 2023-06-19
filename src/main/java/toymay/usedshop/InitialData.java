package toymay.usedshop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;
import toymay.usedshop.member.entity.Privacy;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.order.entity.Order;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.entity.PostFile;
import toymay.usedshop.product.entity.Product;
import toymay.usedshop.product.entity.ProductStatus;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//@Profile("local")
@Component
@RequiredArgsConstructor
public class InitialData {

    private final InitCreateService initCreateService;

    @PostConstruct
    public void init() {
        initCreateService.init();
    }

    @Component
    @Transactional
    static class InitCreateService {

        @PersistenceContext
        EntityManager em;

        private static BCryptPasswordEncoder bCryptPasswordEncoder;

        public InitCreateService(BCryptPasswordEncoder bCryptPasswordEncoder) {
            InitCreateService.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        public void init() {
            List<Member> testMembers = createMembers();
            List<Post> testPosts = createPosts(testMembers);
            List<Product> testProducts = createProducts(testPosts);
            List<Order> testOrders = createOrders(testMembers, testProducts);
//            List<Comment> testComments = createComments(testMembers, testPosts);
//            createReplies(testMembers, testPosts, testComments);
            em.flush();
            em.clear();
        }

        private List<Member> createMembers() {
            List<Member> members = new ArrayList<>();

            Member member1 = new Member(
                    Privacy.create(bCryptPasswordEncoder.encode("1234"), "01011111111",
                            "ghzm888@gmail.com"), "이준호", "qwer", "ROLE_USER");
            member1.changeProfileImage(MemberImage.createBasicImage());
            em.persist(member1);
            members.add(member1);

            Member member2 = new Member(
                    Privacy.create(bCryptPasswordEncoder.encode("1234"), "01012345678",
                            "ghzm888@naver.com"), "이주노", "qq", "ROLE_USER");
            member2.changeProfileImage(MemberImage.createBasicImage());
            em.persist(member2);
            members.add(member2);

            Member member3 = new Member(
                    Privacy.create(bCryptPasswordEncoder.encode("1234"), "01022222222",
                            "ghzm111@naver.com"), "리주노", "ww", "ROLE_USER");
            member3.changeProfileImage(MemberImage.createBasicImage());
            em.persist(member3);
            members.add(member3);

            Member member4 = new Member(
                    Privacy.create(bCryptPasswordEncoder.encode("1234"), "01033333333",
                            "ghzm222@naver.com"), "테스트", "ee", "ROLE_USER");
            member4.changeProfileImage(MemberImage.createBasicImage());
            em.persist(member4);
            members.add(member4);

            return members;
        }

        private List<Post> createPosts(List<Member> members) {
            List<Post> posts = new ArrayList<>();

            Post post1 = new Post(members.get(0), "오랜만에 향수 하나 샀는데 저랑 안맞아서 판매합니다 오렌지 비누향에요..'^'");
            post1.addPostFile(new PostFile("test1Img", "test1Img", "png"));
            em.persist(post1);
            posts.add(post1);

            Post post2 = new Post(members.get(1), "향 체크만 하려고 10뿌 정도만 했어요~");
            post2.addPostFile(new PostFile("test2Img", "test2Img", "png"));
            em.persist(post2);
            posts.add(post2);

            Post post3 = new Post(members.get(2), "신발 사이즈는 270 입니다. 실물보고 제 스타일이 아니라 판매합니다~ 아직 실착안했어요!!");
            post3.addPostFile(new PostFile("test3Img", "test3Img", "png"));
            em.persist(post3);
            posts.add(post3);

            Post post4 = new Post(members.get(3), "아페세 가방 판매합니다. 2번 정도 사용했는데 저한테 너무 커서 사용하기 어렵네요...");
            post4.addPostFile(new PostFile("test4Img", "test4Img", "png"));
            em.persist(post4);
            posts.add(post4);

            return posts;
        }

        private List<Product> createProducts(List<Post> posts) {
            List<Product> products = new ArrayList<>();

            Product product1 = new Product(posts.get(0), "딥디크 오데썽", 170000);
            product1.setStatus(ProductStatus.OnSale);
            product1.setSalePrice(product1.getRegistrationPrice());
            em.persist(product1);
            products.add(product1);

            Product product2 = new Product(posts.get(1), "바이레도 블랑쉬", 150000);
            product2.setStatus(ProductStatus.OnSale);
            product2.setSalePrice(product2.getRegistrationPrice());
            em.persist(product2);
            products.add(product2);

            Product product3 = new Product(posts.get(2), "뉴발란스 990 v1", 250000);
            product3.setStatus(ProductStatus.OnSale);
            product3.setSalePrice(product3.getRegistrationPrice());
            em.persist(product3);
            products.add(product3);

            Product product4 = new Product(posts.get(3), "아페쎄 에코백", 60000);
            product4.setStatus(ProductStatus.OnSale);
            product4.setSalePrice(product4.getRegistrationPrice());
            em.persist(product4);
            products.add(product4);

            return products;
        }

        private List<Order> createOrders(List<Member> members, List<Product> products) {
            List<Order> orders = new ArrayList<>();

            Order order1 = new Order(members.get(0), products.get(2), 230000);
            em.persist(order1);
            orders.add(order1);

            Order order2 = new Order(members.get(1), products.get(0), 150000);
            em.persist(order2);
            orders.add(order2);

            Order order3 = new Order(members.get(2), products.get(1), 130000);
            em.persist(order3);
            orders.add(order3);

            Order order4 = new Order(members.get(2), products.get(3), 55000);
            em.persist(order4);
            orders.add(order4);

            Order order5 = new Order(members.get(0), products.get(3), 56000);
            em.persist(order5);
            orders.add(order5);

            Order order6 = new Order(members.get(0), products.get(1), 131000);
            em.persist(order6);
            orders.add(order6);

            Order order7 = new Order(members.get(1), products.get(0), 152000);
            em.persist(order7);
            orders.add(order7);

            return orders;
        }
    }
}
