package br.com.fiap.epictaskk.task;

import br.com.fiap.epictaskk.user.User;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final RabbitTemplate rabbitTemplate;

    public TaskController(TaskService taskService, RabbitTemplate rabbitTemplate) {
        this.taskService = taskService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal DefaultOAuth2User principal){
        var user = (User) principal;
        var tasks = taskService.findPending();
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        model.addAttribute("avatar", user.getAvatar());
        return "index";
    }

    @GetMapping("/form")
    public String form(Task task){
        return "form";
    }

    @PostMapping("/task")
    public String create(@Valid Task task, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()) return "form";

        taskService.create(task);

        rabbitTemplate.convertAndSend("email-queue", "Tarefa adicionada: " + task.title);

        redirect.addFlashAttribute("message", "Tarefa cadastrada com sucesso");
        return "redirect:/";
    }

    @DeleteMapping("/task/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes redirect){
        taskService.delete(id);
        redirect.addFlashAttribute("message", "Tarefa apagada com sucesso");
        return "redirect:/";
    }

    @PutMapping("/task/catch/{id}")
    public String catchTask(@PathVariable UUID id, @AuthenticationPrincipal DefaultOAuth2User user){
        taskService.catchTask(id, (User) user);
        return "redirect:/";
    }

    @PutMapping("/task/release/{id}")
    public String releaseTask(@PathVariable UUID id, @AuthenticationPrincipal DefaultOAuth2User user){
        taskService.releaseTask(id, (User) user);
        return "redirect:/";
    }

    @PutMapping("/task/inc/{id}")
    public String incTask(@PathVariable UUID id, @AuthenticationPrincipal DefaultOAuth2User user){
        taskService.incTask(id, (User) user);
        return "redirect:/";
    }

    @PutMapping("/task/dec/{id}")
    public String decTask(@PathVariable UUID id, @AuthenticationPrincipal DefaultOAuth2User user){
        taskService.decTask(id, (User) user);
        return "redirect:/";
    }

}
