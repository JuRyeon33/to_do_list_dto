package com.julyun.to_do_list_dto.controller;

import com.julyun.to_do_list_dto.dto.SignupDto;
import com.julyun.to_do_list_dto.model.User;
import com.julyun.to_do_list_dto.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute("signupDto") SignupDto signupDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (userRepository.findByUsername(signupDto.getUsername()) != null) {
            model.addAttribute("error", "이미 사용중인 아이디입니다.");

            return "signup";
        }

        User user = User.builder()
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}