package todo.tasklist.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import todo.tasklist.model.Task;
import todo.tasklist.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasksSortedByDueDate() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "dueDate"));
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
}
