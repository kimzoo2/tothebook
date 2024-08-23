package com.std.tothebook.common.resolvers;

import com.std.tothebook.annotation.User;
import com.std.tothebook.exception.JwtAuthenticationException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.security.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasUserAnnotation = parameter.hasParameterAnnotation(User.class);
        boolean hasLoginUserType = LoginUser.class.isAssignableFrom(parameter.getParameterType());

        return hasUserAnnotation && hasLoginUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw JwtAuthenticationException.create(ErrorCode.CERTIFICATION_NOT_VALIDATED_USER.getMessage());
        }
        return authentication.getPrincipal();
    }
}
