package com.korea.triplocation.security.oauth2;

import com.korea.triplocation.domain.user.entity.User;
import com.korea.triplocation.repository.UserRepository;
import com.korea.triplocation.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        User userEntity = userRepository.searchUserByEmail(email);


        if(userEntity == null) {
            String registerToken = jwtTokenProvider.generateOAuth2RegisterToken(authentication);
            String name = oAuth2User.getAttribute("name");
            response.sendRedirect(
                    "http://localhost:3000/auth/oauth2/signup"
                            + "?registerToken=" + registerToken
                            + "&email=" + email
                            + "&name=" + URLEncoder.encode(name, "UTF-8")
                            + "&provider=" + provider);

        }else {
            if(StringUtils.hasText(userEntity.getProvider())) {
                // oauth2 로 회원가입을 진행하여 provider 가 있는 상태

                if (!userEntity.getProvider().contains(provider)) {
                    // 로그인된 oauth2 의 provider 는 들고있지 않은 상태
                    response.sendRedirect(
                            "http://localhost:3000/auth/oauth2/merge"
                                    + "?provider=" + provider
                                    + "&email=" + email

                    );
                    return;
                }
                response.sendRedirect("http://localhost:3000/auth/oauth2/login"
                        + "?accessToken=" + jwtTokenProvider.generateAccessToken(authentication));
            }else {
                // 자체 회원가입으로 가입하여 provider 가 없는 상태
                response.sendRedirect(
                        "http://localhost:3000/auth/oauth2/merge"
                                + "?provider=" + provider
                                + "&email=" + email
                );
            }
        }
    }

}
