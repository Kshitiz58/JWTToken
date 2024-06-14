package JWT.Token.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import JWT.Token.Model.IUser;
import JWT.Token.Repository.IUserRepository;

@Controller
public class LoginSignupController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserRepository userRepo;
	
	@GetMapping("/signup")
	public String getSignup() {
		return "Signup";
	}
	
	@GetMapping({"/","/login"})
	public String getLogin() {
		return "Login";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute IUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return "redirect:/";
	}
}
