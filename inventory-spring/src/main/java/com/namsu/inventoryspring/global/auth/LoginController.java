package com.namsu.inventoryspring.global.auth;

import com.namsu.inventoryspring.domain.member.dao.MemberRepository;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.global.auth.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호를 확인해 주세요.");
        }
        return "auth/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm(@ModelAttribute("joinRequest") JoinRequest joinRequest) {
        return "auth/joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("joinRequest") JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/joinForm";
        }

        if (!joinRequest.getPassword().equals(joinRequest.getPasswordConfirm())) {
            bindingResult.reject("passwordNotMatch", "패스워드가 일치하지 않습니다.");
            return "auth/joinForm";
        }

        memberRepository.save(Member.builder()
                .username(joinRequest.getUsername())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .build());

        return "redirect:/login-form";
    }
}
