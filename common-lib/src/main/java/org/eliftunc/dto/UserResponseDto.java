package org.eliftunc.dto;

import lombok.Getter;
import lombok.Setter;
import org.eliftunc.enums.ActiveStatus;
import org.eliftunc.enums.Gender;
import org.eliftunc.enums.Role;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String password;
    private ActiveStatus activeStatus;
    private Role role;
}