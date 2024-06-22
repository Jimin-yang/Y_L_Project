package com.example.planvoice;

import android.app.Application;

import com.example.planvoice.network.User;

public class MyApplication extends Application {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
