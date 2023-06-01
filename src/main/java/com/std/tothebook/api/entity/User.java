package com.std.tothebook.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    private long id;

    private String email;

    private String password;

    private String nickname;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "leave_date")
    private LocalDate leaveDate;

    @Column(name = "created_by")
    private LocalDateTime createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_temporary_password")
    private boolean isTemporaryPassword;
}
