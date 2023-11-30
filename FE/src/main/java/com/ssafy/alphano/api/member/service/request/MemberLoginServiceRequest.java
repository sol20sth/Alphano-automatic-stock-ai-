package com.ssafy.alphano.api.member.service.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@NoArgsConstructor
public class MemberLoginServiceRequest {

    String username;

    String password;

    @Builder
    public MemberLoginServiceRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }

}
