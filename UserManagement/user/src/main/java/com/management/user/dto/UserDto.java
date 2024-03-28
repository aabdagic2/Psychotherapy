package com.management.user.dto;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    //private String userId;
    private String type;
    private String name;
    private String email;
    private String passwordHash;
}
