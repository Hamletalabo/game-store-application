package com.hamlet.store.game;

import com.hamlet.store.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game extends BaseEntity {

    private String title;

    @Enumerated (EnumType.STRING)
    private SupportedPlatforms supportedPlatforms; //PC, XBOX, PLAYSTATION, NINTENDO, ETC.

    private String coverPicture;
}
