package com.mywings.waterqualitymonitoringsystem.model;

public class UserInfoHolder {


    private User user;
    private Location location;

    public static UserInfoHolder getInstance() {
        return UserInfoHelper.INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private static class UserInfoHelper {
        static final UserInfoHolder INSTANCE = new UserInfoHolder();
    }
}
