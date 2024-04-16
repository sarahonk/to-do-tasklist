package todo.tasklist.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.tasklist.model.Task;
import todo.tasklist.service.TaskService;

import java.util.List;

@Controller
public class TasklistController {

    private final TaskService taskService;

    public TasklistController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String taskList(Model model) {
        List<Task> tasks = taskService.getAllTasksSortedByDueDate();
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new Task());
        return "task-list";
    }

    @GetMapping("/tasks/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("newTask", new Task());
        return "add-task";
    }

    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute("newTask") Task task) {
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
}
