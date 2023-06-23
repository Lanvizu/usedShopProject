package toymay.usedshop.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toymay.usedshop.common.file.FileDto;
import toymay.usedshop.common.file.FileManager;
import toymay.usedshop.member.controller.dto.LoginMemberDto;
import toymay.usedshop.member.service.MemberService;
import toymay.usedshop.order.controller.dto.BidPriceForm;
import toymay.usedshop.order.controller.dto.OrderIdDto;
import toymay.usedshop.order.service.OrderDto;
import toymay.usedshop.order.service.OrderService;
import toymay.usedshop.post.controller.dto.PostSaveForm;
import toymay.usedshop.post.service.PostDto;
import toymay.usedshop.post.service.PostIdDto;
import toymay.usedshop.post.service.PostService;
import toymay.usedshop.product.entity.ProductStatus;
import toymay.usedshop.product.service.ProductDto;
import toymay.usedshop.product.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderService orderService;
    private final FileManager fileManager;

    @GetMapping("/mainPage")
    public String postPage(Model model) {
        Long memberId = memberService.findIdByAuthentication();
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);

        List<PostIdDto> postIdDtos = postService.getPostListByIdDesc2();
        model.addAttribute("posts", postIdDtos);

        return ("post/mainPage");
    }

    @GetMapping("/post/write")
    public String writePostPage(Model model) {
        Long memberId = memberService.findIdByAuthentication();
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);

        model.addAttribute("PostSaveForm", new PostSaveForm());
        return ("post/writePost");
    }

    @PostMapping("/post/write")
    public String writePost(@ModelAttribute PostSaveForm form, BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasFieldErrors()) {
            List<FileDto> fileDtos = fileManager.storeImageFiles(form.getFiles());

            Long memberId = memberService.findIdByAuthentication();
            Long postId = postService.writePost(new PostDto(memberId, fileDtos, form.getText()));

            //product price, name 저장 service 생성
            ProductDto productDto = new ProductDto(form.getName(), form.getRegistrationPrice(), postId , ProductStatus.OnSale);
            productService.saveNameAndPrice(productDto);

            return "redirect:/mainPage";
        }
        return "redirect:/post/write";
    }

    @GetMapping("/post/{postId}")
    public String privatePostPage(@PathVariable("postId") Long postId, Model model) {
        //본인 정보
        Long memberId = memberService.findIdByAuthentication();
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);
        //post 주인 정보
        Long memberId1 = postService.getPostDto(postId).getMemberId();
        PostIdDto postIdDto = postService.getPostIdDto(postId);
        model.addAttribute("post", postIdDto);
        //product 상태 확인 -> 판매완료/판매중
        ProductStatus status = postIdDto.getProductDto().getProductStatus();
        if (status == ProductStatus.SoldOut) {
            int salePrice = productService.findSalePriceByPostId(postId);
            model.addAttribute("salePrice", salePrice);
            return ("post/soldOutPage");
        }
        else {
            Long productId = productService.findProductIdByPostId(postId);
            List<OrderIdDto> orderIdDtos = orderService.findOrderDtosByProductId(productId);
            model.addAttribute("orders", orderIdDtos);
            //post 주인 확인 -> 주인/유저
            if (memberId == memberId1) {
                return ("post/privatePostPage");
            }
            else {
                Long orderId = null;
                boolean hasMatchingMemberId = false;
                for (OrderIdDto orderIdDto : orderIdDtos) {
                    if (orderIdDto.getMemberId().equals(memberId)) {
                        // 특정 id와 memberId 값이 같을 경우
                        hasMatchingMemberId = true;
                        orderId = orderIdDto.getId();
                        break;
                    }
                }
                if (hasMatchingMemberId) {
                    // memberId 값이 특정 id와 같은 경우 처리할 로직
                    model.addAttribute("orderId", orderId);
                    int bidPrice = orderService.findOrderDtoById(orderId).getBidPrice();
                    model.addAttribute("bidPrice",bidPrice);
                    model.addAttribute("status", true);
                } else {
                    // memberId 값이 특정 id와 다른 경우 처리할 로직
                    model.addAttribute("status", false);

                }
                model.addAttribute("bidPriceForm", new BidPriceForm());
                return ("post/auctionPage");
            }
        }
    }

    @PostMapping("/post/{postId}")
    public String auction(@PathVariable("postId") Long postId,
                          @ModelAttribute BidPriceForm form ,BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasFieldErrors()) {
            Long productId = productService.findProductIdByPostId(postId);//productId 추출
            Long memberId = memberService.findIdByAuthentication();//memberId 추출
            OrderDto orderDto = new OrderDto(productId, memberId, form.getBidPrice());
            orderService.saveOrder(orderDto);

            return ("redirect:/post/{postId}");
        }
        return ("redirect:/post/{postId}");
    }


    @GetMapping("post/{postId}/update")
//    @PreAuthorize("#postId == authentication.principal.id")
    public String updatePostPage(@PathVariable("postId") Long postId, Model model,
                                 RedirectAttributes redirectAttributes) {

        Long memberId = memberService.findIdByAuthentication();
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);

        Long postMemberId = postService.getPostDto(postId).getMemberId();
        PostIdDto postIdDto = postService.getPostIdDto(postId);
        model.addAttribute("post", postIdDto);

        //post 주인만 수정 가능
        if (memberId != postMemberId) {
            redirectAttributes.addAttribute("status", true);
            return ("redirect:/post/{postId}");
        }

        model.addAttribute("PostSaveForm", new PostSaveForm());

        return ("post/updatePost");
    }

    @PostMapping("post/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostSaveForm form,
                             @RequestParam String action) {
        if (action.equals("modify")) {
            Long productId = productService.findProductIdByPostId(postId);
            productService.changeNameAndPrice(productId, form.getName(), form.getRegistrationPrice());
            postService.modifyPostText(postId, form.getText());
        } else if (action.equals("delete")) {
            postService.deletePost(postId);
        }
        return ("redirect:/mainPage");
    }
}
