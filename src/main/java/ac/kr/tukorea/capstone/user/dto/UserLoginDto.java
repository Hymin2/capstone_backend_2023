package ac.kr.tukorea.capstone.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @NotEmpty
    @Length(min = 3, max = 20)
    private String username;
    @NotEmpty
    @Length(min = 6, max = 20)
    private String password;
}
