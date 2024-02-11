package com.AuthService.config;

import com.AuthService.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGenerator {

public Map<String, String> generateToken(UserInfo user) {
	
	Map<String, Object> userdata = new HashMap<>();
	
	userdata.put("id", user.getUseremail());
	userdata.put("password", user.getPassword());
	
	String jwtToken = "";
	
	jwtToken = Jwts.builder().setClaims(userdata).setIssuedAt(new Date())
			           .signWith(SignatureAlgorithm.HS256, "secret").compact();
	
	Map<String, String> jwtTokenMap = new HashMap<>();
	jwtTokenMap.put("token", jwtToken);
	jwtTokenMap.put("userId", user.getUseremail());
	return jwtTokenMap;
}


	public String getUserIdByToken (String token) {
	 String jwt = token.substring(7);
		Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).getBody();
		String username = claims.get("id", String.class);
		return username;
	}

}
