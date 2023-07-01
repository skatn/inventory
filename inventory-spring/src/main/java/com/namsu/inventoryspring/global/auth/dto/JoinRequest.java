package com.namsu.inventoryspring.global.auth.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class JoinRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 영문, 숫자로 5~20자로 구성할 수 있습니다.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{5,20}$", message = "영문, 숫자, 특수문자로 5~20자로 구성할 수 있습니다.")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{5,20}$", message = "영문, 숫자, 특수문자로 5~20자로 구성할 수 있습니다.")
    private String passwordConfirm;

}
