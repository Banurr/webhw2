package kz.kbtu.banurr.webhw2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    private String code;
    private String name;
    private Boolean isActive;
}
