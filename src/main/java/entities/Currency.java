package entities;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor

public class Currency {
    private Long id;
    private String code;
    private String fullName;
    private String sign;
}
