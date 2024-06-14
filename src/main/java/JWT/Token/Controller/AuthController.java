package JWT.Token.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import JWT.Token.Model.LoginForm;
import JWT.Token.Service.IUserDetailsService;
import JWT.Token.Web.JWTService;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private IUserDetailsService userService;
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));
		if(auth.isAuthenticated()) {
			return jwtService.generateToken(userService.loadUserByUsername(loginForm.username()));
		}else {
			throw new UsernameNotFoundException("Invalid Credentials.");
		}
		
	}	
	
}
