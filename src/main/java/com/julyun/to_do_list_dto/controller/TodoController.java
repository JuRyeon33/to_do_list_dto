package com.julyun.to_do_list_dto.controller;

import com.julyun.to_do_list_dto.dto.TodoDto;
import com.julyun.to_do_list_dto.model.Todo;
import com.julyun.to_do_list_dto.model.User;
import com.julyun.to_do_list_dto.repository.TodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    private User getCurrentUser(HttpSession session) {
        return  (User) session.getAttribute("user");
    }

    @GetMapping
    public String list(HttpSession httpSession) {
        System.out.println(getCurrentUser(httpSession));
        User user = getCurrentUser(httpSession);

        if(user == null) {
            return "redirect:/login";
        }

        return "todo-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession httpSession, Model model) {
        if (getCurrentUser(httpSession) == null) return "redirect:/login";

        model.addAttribute("todoDto", new TodoDto());

        return "todo-form";
    }

    @PostMapping("/add")
    public String add (
        @Valid @ModelAttribute TodoDto todoDto,
        BindingResult bindingResult,
        HttpSession httpSession
    ) {
        if (bindingResult.hasErrors()) return "todo-form;";

        User user = getCurrentUser(httpSession);

        Todo todo = Todo.builder()
                .userId(user.getId())
                .title(todoDto.getTitle())
                .completed(todoDto.isCompleted())
                .build();

        todoRepository.save(todo);

        return "redirect:/todos";
    }
}
