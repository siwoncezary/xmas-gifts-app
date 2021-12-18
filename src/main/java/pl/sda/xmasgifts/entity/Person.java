package pl.sda.xmasgifts.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    @GeneratedValue
    private UUID id;

    private String username;

    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Wish> wishes = new HashSet<>();

    @OneToMany(mappedBy = "gifter")
    private Set<Wish> gifts = new HashSet<>();
}
