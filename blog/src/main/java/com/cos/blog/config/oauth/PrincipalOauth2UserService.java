package com.cos.blog.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistraion : " + userRequest.getClientRegistration());
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		System.out.println("getAttirutes : " + oauth2User.getAttributes());
		//로그인버튼 클릭 -> 로그인창 -> 로그인 완료 -> code를 리턴(OAuth-client라이브러리) -> AccessToken요청
		// userRequest정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다
		
		//회원가입을 강제로 진행해볼 예정
		String provider = userRequest.getClientRegistration().getClientId(); //구글은 google, 페이스북은 facebook 찍힘
		String providerId = oauth2User.getAttribute("sub"); //pk값(고유한값) 구글의경우 "sub", 페이스북의 경우 "id"적어주면됨
		String username = provider+"_"+providerId;//google_123456 , facebook_456465 이런식으로 아이디로 쓸거
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			System.out.println("구글 로그인이 최초입니다");
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
				userRepository.save(userEntity);
		}else {
			System.out.println("구글 로그인 이미 한적이 있습니다.");
		}
		
		
		return super.loadUser(userRequest);
	}
}
