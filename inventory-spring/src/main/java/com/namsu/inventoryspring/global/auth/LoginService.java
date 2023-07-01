package com.namsu.inventoryspring.global.auth;

import com.namsu.inventoryspring.domain.member.dao.MemberRepository;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.global.auth.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long join(JoinRequest joinRequest) {
        validationDuplicate(joinRequest.getUsername());

        Member member = memberRepository.save(Member.builder()
                .username(joinRequest.getUsername())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .build());

        return member.getId();
    }

    private void validationDuplicate(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
    }
}
