package att.model;

import lombok.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Input {
    private String username;
    private String password;
    private ArrayList<TestModel> testModels;

    public String toString() {
        return "Username: " + username + ", Password: " + password + ", Test Models: " + testModels.stream().map(TestModel::toString).collect(Collectors.joining(", "));
    }

    public boolean isInValid() {
        return username == "" || password == "" || username == null || password == null;
    }
}
