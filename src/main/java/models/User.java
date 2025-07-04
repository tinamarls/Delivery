package models;

import lombok.*;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    Long id;
    String loginOfUSer;
    String password;
    String role;

}
