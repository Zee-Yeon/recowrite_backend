package com.write.reco.domain;

import com.write.reco.domain.constant.Status;
import com.write.reco.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String groups;

    @Enumerated(EnumType.STRING)
    @Setter
    private Status status;

    @OneToMany(mappedBy = "user")
    private List<Image> imageList = new ArrayList<>();
}
