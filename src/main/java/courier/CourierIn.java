package courier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourierIn {
    private String login;
    private String password;
    private String firstName;

    public CourierIn(String login, String password) {
        this.login = login;
        this.password = password;
    }


}
