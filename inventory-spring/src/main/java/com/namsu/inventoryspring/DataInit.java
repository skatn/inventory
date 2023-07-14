package com.namsu.inventoryspring;

import com.namsu.inventoryspring.global.auth.LoginService;
import com.namsu.inventoryspring.global.auth.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final InitClass initClass;

    @PostConstruct
    public void init() {
        initClass.join();
    }

    @Component
    @RequiredArgsConstructor
    public static class InitClass {

        private final LoginService loginService;

        public void join() {
            JoinRequest joinRequest = new JoinRequest();
            joinRequest.setUsername("test1");
            joinRequest.setPassword("test1!");
            joinRequest.setPasswordConfirm("test1!");
            loginService.join(joinRequest);
        }
    }

}
