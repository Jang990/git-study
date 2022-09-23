package com.example.inflearnJWT.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//클라이언트에게 보낼 토큰을 담은 DTO
public class TokenDto {
    private String token;
}
