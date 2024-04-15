package att.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Input {
    private String username;
    private String password;
    private TestModel requiredTest;
    private TestModel optionalTest;

    public String toString() {
        return "Username: " + username + ", Password: " + password + ", Required Test: " + requiredTest + ", Optional Test: " + optionalTest;
    }
}
