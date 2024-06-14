package JWT.Token.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import JWT.Token.Model.IUser;
import JWT.Token.Repository.IUserRepository;

@Service
public class IUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		IUser user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found:" +username));
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(getRoles(user))
				.build();
	}

	private String [] getRoles(IUser user) {
		if(user.getRole() == null || user.getRole().isEmpty()) {
			return new String [] {"USER"};
		}
		return user.getRole().split(",");
	}
	
	

}
