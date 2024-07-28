package com.hafidtech.api_gunungcondongcom.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hafidtech.api_gunungcondongcom.model.audit.DateAudit;
import com.hafidtech.api_gunungcondongcom.model.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username"}),
        @UniqueConstraint(columnNames = { "email"})
})
@EntityListeners(AuditingEntityListener.class)
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min=3, max = 20)
    @Column(length = 20)
    private String firstName;

    @NotBlank
    @Size(min=3, max = 20)
    @Column(length = 20)
    private String lastName;

    @NotBlank
    @Size(min=5, max = 20)
    @Column(length = 20)
    private String username;

    @NotBlank
    @Size(min=10, max = 50)
    @Email
    @Column(length = 50)
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min=5, max = 255)
    private String password;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address;

    @Size(min = 10, max = 15)
    @Column(length = 15)
    private String phone;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> role;


    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public List<Role> getRole() {
        return role == null ? null : new ArrayList<>(role);
    }
    public void setRoles(List<Role> roles) {

        if (roles == null) {
            this.role = null;
        } else {
            this.role = Collections.unmodifiableList(roles);
        }
    }
}