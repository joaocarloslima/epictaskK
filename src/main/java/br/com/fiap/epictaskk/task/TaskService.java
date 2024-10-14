package br.com.fiap.epictaskk.task;

import br.com.fiap.epictaskk.user.User;
import br.com.fiap.epictaskk.user.UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public void create(Task task) {
        taskRepository.save(task);
    }

    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }

    public void catchTask(UUID id, User user) {
        var task = getTask(id);
        if (task.getUser() != null) return;
        task.setUser(user);
        taskRepository.save(task);
    }

    public void releaseTask(UUID id, User user) {
        var task = getTask(id);
        task.setUser(null);
        taskRepository.save(task);
    }

    public void incTask(UUID id, User user) {
        var task = getTask(id);
        if (task.getStatus() == 100) return;
        task.setStatus(task.getStatus() +10);
        if (task.getStatus() == 100){
            userService.addScore(user, task.getScore());
        }
        taskRepository.save(task);
    }

    public void decTask(UUID id, User user) {
        var task = getTask(id);
        if (task.getStatus() == 0) return;
        task.setStatus(task.getStatus() - 10);
        taskRepository.save(task);
    }

    private Task getTask(UUID id) {
        var task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada")
        );
        return task;
    }

    public List<Task> findPending() {
        return taskRepository.findByStatusLessThan(100);
    }
}
