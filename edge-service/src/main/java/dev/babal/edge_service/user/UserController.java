package dev.babal.edge_service.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    public Mono<User> getUser(@AuthenticationPrincipal OidcUser user) {
        return Mono.just(new User(
            user.getPreferredUsername(),
            user.getGivenName(),
            user.getFamilyName(),
            user.getClaimAsStringList("roles")
        ));
    }

//    @GetMapping
//    public Mono<User> getUser() {
//        return ReactiveSecurityContextHolder.getContext()
//            .map(SecurityContext::getAuthentication)
//            .map(authentication -> (OidcUser) authentication.getPrincipal())
//            .map(oidcUser -> new User(
//                oidcUser.getPreferredUsername(),
//                oidcUser.getGivenName(),
//                oidcUser.getFamilyName(),
//                oidcUser.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .toList()
//            ));
//    }
}
