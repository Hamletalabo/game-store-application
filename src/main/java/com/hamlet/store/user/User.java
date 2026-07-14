package com.hamlet.store.user;

import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.gameRequest.GameRequest;
import com.hamlet.store.notification.Notification;
import com.hamlet.store.wishlist.Wishlist;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstname;
    private String lastname;
    private String email;
    private String profilePicUrl;

    @OneToOne (mappedBy = "user")
    private Wishlist wishlist;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<GameRequest> gameRequests;
}
