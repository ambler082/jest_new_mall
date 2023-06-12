package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	//새로운 유저 생성하고 비밀번호 암호화하여 리턴
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //비밀번호 암호화 객체 (빈으로 등록하여 주입받자)
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        user.setPassword(passEncoder.encode(password));
        this.userRepo.save(user);
        return user;
    }
    
    //유저이름으로 유저 객체를 리턴
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepo.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
  
}
