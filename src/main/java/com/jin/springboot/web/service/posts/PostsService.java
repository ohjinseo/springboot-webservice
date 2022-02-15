package com.jin.springboot.web.service.posts;

import com.jin.springboot.domain.posts.Posts;
import com.jin.springboot.domain.posts.PostsRepository;
import com.jin.springboot.web.dto.PostsListResponseDto;
import com.jin.springboot.web.dto.PostsResponseDto;
import com.jin.springboot.web.dto.PostsSaveRequestDto;
import com.jin.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service    //Service는 트랜잭션, 도메인 간 순서 보장의 역할만 함
public class PostsService {
    private final PostsRepository postsRepository;
    private final EntityManager em;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        System.out.println(">>>>> is Containing : " + em.contains(posts));  //??

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalIdentifierException("해당 게시글이 없습니다. id=" + id)
        );

        //어차피 쓰기 지연안되서 Session 소멸?

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(IllegalAccessError::new);

        System.out.println(">>>>> is Containing : " + em.contains(posts));  //??
        postsRepository.delete(posts);
    }
}
