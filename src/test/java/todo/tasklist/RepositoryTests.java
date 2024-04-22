package todo.tasklist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import todo.tasklist.model.Task;
import todo.tasklist.model.User;
import todo.tasklist.repository.TaskRepository;
import todo.tasklist.repository.UserRepository;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testTaskRepository() {
        Task task = new Task("Test Task", null);
        taskRepository.save(task);

        Task foundTask = taskRepository.findById(task.getId()).orElse(null);
        assertThat(foundTask).isNotNull();

        taskRepository.delete(foundTask);
        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }

    @Test
    public void testUserRepository() {
        User user = new User("test", "password", null);
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("test");
        assertThat(foundUser).isNotNull();

        userRepository.delete(foundUser);
        assertThat(userRepository.findByUsername("test")).isNull();
    }
}
