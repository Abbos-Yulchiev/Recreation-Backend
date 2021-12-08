package com.epam.recreation_module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

/*    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Permission> permissionList;*/

    @Column(columnDefinition = "text", length = 500)
    private String description;


    @Override
    public String getAuthority() {
        return name;
    }
}
