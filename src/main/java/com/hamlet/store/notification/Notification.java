package com.hamlet.store.notification;

import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification extends BaseEntity {

    private String message;
    private String receiver;
    private NotificationLevel level;
    private NotificationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
