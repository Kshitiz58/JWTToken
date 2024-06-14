package JWT.Token.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JWT.Token.Model.IUser;
import java.util.Optional;


public interface IUserRepository extends JpaRepository<IUser, Integer>{

	Optional<IUser> findByUsername(String username);
}
