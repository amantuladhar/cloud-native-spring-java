package dev.babal.edge_service.user;

import java.util.List;

public record User(
    String username,
    String firstName,
    String lastname,
    List<String> roles
) {
}
