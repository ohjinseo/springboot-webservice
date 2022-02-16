package com.jin.springboot.web;

import com.jin.springboot.config.auth.LoginUser;
import com.jin.springboot.config.auth.dto.SessionUser;
import com.jin.springboot.web.dto.PostsResponseDto;
import com.jin.springboot.web.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        // 아래와 같이 CustomOAuth2UserService에서 로그인 성공 시 세션에 저장한 유저 불러옴 (이러한 방식은 코드 중복과 유지보수가 어려워짐)
        // HandlerMethodArgumentResolver와 커스텀 어노테이션을 통해 매개변수로 간단히 불러올 수 있음
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }


        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
