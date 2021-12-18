package pl.sda.xmasgifts.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private UUID id;

    private String username;

    private String email;

    @OneToMany
    private Set<Wish> wishes;

    @OneToMany
    private Set<Wish> gifts;
}
