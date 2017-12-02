package com.song.samples.proxy;

/**
 * static proxy
 */
public class UserDaoProxy implements IUserDao {
    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    public void save() {
        System.out.println("start transaction ...");
        target.save();
        System.out.println("end transaction ...");
    }

    public static void main(String[] args) {
        IUserDao target = new UserDao();

        IUserDao proxy = new UserDaoProxy(target);

        proxy.save();
    }
}
