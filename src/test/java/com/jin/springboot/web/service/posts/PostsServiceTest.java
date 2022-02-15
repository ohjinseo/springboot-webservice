package com.jin.springboot.web.service.posts;

import com.jin.springboot.domain.posts.Posts;
import com.jin.springboot.domain.posts.PostsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PostsServiceTest  {
    @Autowired
    PostsRepository postsRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void checkEntityManagerLifeCycle() {
        String title = "title";
        String content = "content";

        String title2 = "title2";
        String content2 = "content2";

        Posts savedPosts = postsRepository.save(Posts.builder().title(title).content(content).build());
        Long id = savedPosts.getId();


        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        assertThat(em.contains(posts)).isTrue();

        posts.update(title2, content2);
    }
}