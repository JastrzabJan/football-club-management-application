package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserInfo")
public class User extends AbstractEntity {


    @OneToOne(mappedBy = "user")
    private Player player;

    @OneToOne(mappedBy = "user")
    private Coach coach;

    @NotEmpty
    @Email
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 4, max = 255)
    private String passwordHash;

    @NotNull(message = "User role cannot be null")
    @Enumerated(EnumType.STRING)
    private Role role;


}

