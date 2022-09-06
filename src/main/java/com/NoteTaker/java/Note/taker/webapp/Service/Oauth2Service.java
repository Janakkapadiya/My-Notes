package com.NoteTaker.java.Note.taker.webapp.Service;

import com.NoteTaker.java.Note.taker.webapp.Configue.CustomerOauth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2Service extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        return new CustomerOauth2User(super.loadUser(oAuth2UserRequest));
    }
}
