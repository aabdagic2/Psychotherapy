package ba.unsa.etf.pnwt.apigateway.authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ValidateTokenRequestDto {
    private List<String> roles;

    public ValidateTokenRequestDto(List<String> roles) {
        this.roles = roles;
    }
}
