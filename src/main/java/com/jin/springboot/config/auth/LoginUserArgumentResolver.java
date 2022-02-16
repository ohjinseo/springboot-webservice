package com.jin.springboot.config.auth;

import com.jin.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

// HandlerMethodArgumentResolver
// 조건에 맞는 경우 메서드가 있다면 HandlerMethodArgumentResolver의 구현체가
// 지정한 값으로 해당 메서드의 파라미터로 넘길 수 있음

// >>> 끝으로 스프링에서 Handler...Resolver를 인식하도록 WebMvcConfigurer 별도 설정 필요

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        //  파라미터에 @LoginUser이 붙어 있는지와
        //  클래스 타입이 SessionUser.class인 경우 TRUE 반환
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체 생성
        return httpSession.getAttribute("user");
    }
}
