package JWT.Token.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/home")
	public String getHome() {
		return "Home";
	}
	@GetMapping("/admin/home")
	public String getAdminHome() {
		return "Admin";
	}
	@GetMapping("/user/home")
	public String getUserHome() {
		return "User";
	}
}
