package com.std.tothebook.common.resolvers;

import com.std.tothebook.annotation.User;
import com.std.tothebook.exception.JwtAuthenticationException;
import com.std.tothebook.exception.enums.ErrorCode;
import com.std.tothebook.security.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.*;

class LoginUserArgumentResolverTest {

    private LoginUserArgumentResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new LoginUserArgumentResolver();
    }

    public void testMethod(@User LoginUser user) {}

    public void testMethod2(@User com.std.tothebook.entity.User user) {}

    public void testMethod3(LoginUser user) {}

    @DisplayName("supportsParameter 검증 (mock)")
    @Test
    void supportsParameter_mock() {
        MethodParameter mockParameter = mock(MethodParameter.class);

        when(mockParameter.hasParameterAnnotation(User.class))
                .thenReturn(true);
        doReturn(LoginUser.class)
                .when(mockParameter)
                .getParameterType();

        boolean isSupports = resolver.supportsParameter(mockParameter);
        assertThat(isSupports).isTrue();
    }

    @DisplayName("supportsParameter 검증 (생성자)")
    @Test
    void supportsParameter_constructor() throws NoSuchMethodException {
        MethodParameter methodParameter =
                new MethodParameter(getClass().getMethod("testMethod", LoginUser.class), 0);

        boolean isSupports = resolver.supportsParameter(methodParameter);
        assertThat(isSupports).isTrue();
    }

    @DisplayName("supportsParameter 검증 - 지원 X (클래스 오류)")
    @Test
    void supportsParameter_notSupport_class() throws NoSuchMethodException {
        MethodParameter methodParameter =
                new MethodParameter(getClass().getMethod("testMethod2", com.std.tothebook.entity.User.class), 0);

        boolean isSupports = resolver.supportsParameter(methodParameter);
        assertThat(isSupports).isFalse();
    }

    @DisplayName("supportsParameter 검증 - 지원 X (어노테이션)")
    @Test
    void supportsParameter_notSupport_annotation() throws NoSuchMethodException {
        MethodParameter methodParameter =
                new MethodParameter(getClass().getMethod("testMethod3", LoginUser.class), 0);

        boolean isSupports = resolver.supportsParameter(methodParameter);
        assertThat(isSupports).isFalse();
    }

    @DisplayName("resolveArgument 검증 - 로그인 완료 시")
    @Test
    void resolveArgument() throws Exception {
        // given
        LoginUser loginUser = LoginUser.builder()
                .id(0L)
                .email("tester@abc.com")
                .password("1234")
                .nickname("nickname")
                .build();

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Authentication mockAuthentication = mock(Authentication.class);

        when(mockAuthentication.getPrincipal())
                .thenReturn(loginUser);
        when(mockSecurityContext.getAuthentication())
                .thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);

        // when
        Object result = resolver.resolveArgument(null, null, null, null);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(LoginUser.class);

        LoginUser resultUser = (LoginUser) result;
        assertThat(resultUser).isEqualTo(loginUser);
    }

    @DisplayName("resolveArgument 실패 검증 - authentication이 없을 때")
    @Test
    void resolveArgument_fail_null() {
        thenThrownBy(() -> resolver.resolveArgument(null, null, null, null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @DisplayName("resolveArgument 실패 검증 - 익명 사용자일 때")
    @Test
    void resolveArgument_fail_Anonymous() {
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken mockAnonymous = mock(AnonymousAuthenticationToken.class);

        when(mockSecurityContext.getAuthentication())
                .thenReturn(mockAnonymous);
        SecurityContextHolder.setContext(mockSecurityContext);

        assertThatThrownBy(() -> resolver.resolveArgument(null, null, null, null))
                .isExactlyInstanceOf(JwtAuthenticationException.class)
                .hasMessage(ErrorCode.CERTIFICATION_NOT_VALIDATED_USER.getMessage());
    }
}