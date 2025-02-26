package nextstep.auth.unit;

import nextstep.auth.UnAuthorizedStateException;
import nextstep.auth.authorization.LoginCheckInterceptor;
import nextstep.auth.token.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static nextstep.auth.unit.MockRequest.createMockRequestWithInvalidAccessToken;
import static nextstep.auth.unit.MockRequest.createMockRequestWithValidAccessToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoginCheckInterceptorTest extends AuthTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private LoginCheckInterceptor loginCheckInterceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        super.setUp();
        loginCheckInterceptor = new LoginCheckInterceptor(jwtTokenProvider);
        response = new MockHttpServletResponse();
    }

    @DisplayName("로그인이 되어있는지 확인(Bearer token이 존재하고 유효함)")
    @Test
    void preHandle() throws Exception {
        // given
        request = createMockRequestWithValidAccessToken(jwtTokenProvider);

        // when
        boolean result = loginCheckInterceptor.preHandle(request, response, new Object());

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("로그인이 되어있는지 확인(Bearer token이 존재하고 유효하지않음)")
    @Test
    void preHandle2() {
        // given
        request = createMockRequestWithInvalidAccessToken();

        // when & then
        assertThatThrownBy(() -> loginCheckInterceptor.preHandle(request, response, new Object()))
                .isInstanceOf(UnAuthorizedStateException.class);
    }

    @DisplayName("로그인이 되어있는지 확인(Authorization이 존재하지 않음)")
    @Test
    void preHandle3() {
        // given
        request = new MockHttpServletRequest();

        // when & then
        assertThatThrownBy(() -> loginCheckInterceptor.preHandle(request, response, new Object()))
                .isInstanceOf(UnAuthorizedStateException.class);
    }
}
