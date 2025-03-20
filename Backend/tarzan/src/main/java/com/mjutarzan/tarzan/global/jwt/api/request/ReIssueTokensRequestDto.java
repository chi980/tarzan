package com.mjutarzan.tarzan.global.jwt.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReIssueTokensRequestDto {

    private String email;
}
