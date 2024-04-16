package todo.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
