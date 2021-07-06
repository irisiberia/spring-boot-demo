package com.duapps.affair.demo.bean;


public class ThreadLocals {

    private static ThreadLocal<Long> VERSION_TL = new ThreadLocal<>();
    private static ThreadLocal<UserInfo> USER_TL = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        USER_TL.set(user);
    }

    public static UserInfo getUser() {
        return USER_TL.get();
    }


    public static void setVersion(long v) {
        VERSION_TL.set(v);
    }

    public static Long getVersion() {
        return VERSION_TL.get();
    }

    public static void clear() {
        VERSION_TL.remove();
    }
}
