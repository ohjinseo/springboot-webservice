package com.jin.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");
    //스프링 시큐리티에선 권한 코드에 ROLE이 앞에 있어야만 됨

    private final String key;
    private final String title;
}
