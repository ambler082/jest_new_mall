package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserSecurityService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//유저이름으로 DB검색해서 사죵자 정보  리턴
		Optional<SiteUser> _siteUser = this.userRepo.findByusername(username);
		if(_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}
		SiteUser siteUser = _siteUser.get(); //있을경우 유저객체를 받기
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		//유저네임이 admin과 같으면 관리자role를 주고 아니면 일반 유저 role를 입력
		
		if ("admin".equals(username)) {
	            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
	        } else {
	            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
	        }
	        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}



}
