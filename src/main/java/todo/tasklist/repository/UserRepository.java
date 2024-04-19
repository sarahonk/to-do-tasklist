package todo.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.tasklist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
