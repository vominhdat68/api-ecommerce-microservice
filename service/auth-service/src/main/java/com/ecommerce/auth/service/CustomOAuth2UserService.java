//package com.ecommerce.auth.service;
//
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
////import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
////import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
////import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
////import org.springframework.security.oauth2.core.user.OAuth2User;
////import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {
//
//    private final PasswordEncoder encoder;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//
//        // info provider
//        System.err.println(">>>>>>>>>>>>>>>>>>>>");
//        System.err.println(">>>>>>   "+oAuth2User.getAuthorities());
//
//        return null;
//    }
//}
