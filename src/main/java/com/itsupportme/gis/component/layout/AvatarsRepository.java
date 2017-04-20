package com.itsupportme.gis.component.layout;


import java.util.HashMap;

public class AvatarsRepository {

    public static final Integer AVATAR_1    = 0;
    public static final Integer AVATAR_2    = 1;
    public static final Integer AVATAR_3    = 2;
    public static final Integer AVATAR_4    = 3;
    public static final Integer AVATAR_5    = 4;
    public static final Integer AVATAR_6    = 5;

    public static HashMap<Integer, String> getAvatar(){

        HashMap<Integer, String> avatars = new HashMap<>();

        avatars.put(AVATAR_1, "avatar5.png");
        avatars.put(AVATAR_2, "avatar3.png");
        avatars.put(AVATAR_3, "avatar1.png");
        avatars.put(AVATAR_4, "avatar6.png");
        avatars.put(AVATAR_5, "avatar4.png");
        avatars.put(AVATAR_6, "avatar7.png");

        return avatars;
    }

}
