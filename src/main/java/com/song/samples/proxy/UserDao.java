package com.song.samples.proxy;

public class UserDao implements IUserDao {
    public void save() {
        System.out.println("save data ...");
    }
}
