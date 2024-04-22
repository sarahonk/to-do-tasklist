package todo.tasklist;

import java.time.LocalDate;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import todo.tasklist.model.Task;
import todo.tasklist.model.User;
import todo.tasklist.repository.TaskRepository;
import todo.tasklist.repository.UserRepository;

@SpringBootApplication
public class TasklistApplication {
	private static final Logger log = LoggerFactory.getLogger(TasklistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TasklistApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TaskRepository taskRepository, UserRepository userRepository) {
		return (args) -> {

			Task task1 = new Task("Complete research paper on renewable energy.", LocalDate.of(2024, 4, 14));
			Task task2 = new Task("Have a picnic in the park with friends or family.", LocalDate.of(2024, 5, 16));
			Task task3 = new Task("Buy groceries for the week.", LocalDate.of(2024, 4, 26));
			Task task4 = new Task("Prepare presentation slides for team meeting.", LocalDate.of(2024, 5, 20));

			taskRepository.save(task1);
			taskRepository.save(task2);
			taskRepository.save(task3);
			taskRepository.save(task4);

			User user1 = new User("user", "$2a$10$4kWiiL7c188te3sqTZpKiOJMC8AJR2X4EAH8mKF5OOq/u1LOixz8e", "USER");
			userRepository.save(user1);

			for (Task task : taskRepository.findAll()) {
				log.info(task.toString());
			}

			for (User user : userRepository.findAll()) {
				log.info(user.toString());
			}

		};
	}
}
