package services;

import dao.UserDao;
import models.User;

public class AuthenticationService {
    private UserDao userDao = new UserDao();

    public boolean registerUser(String username, String password) {
        return userDao.registerUser(username, password);
    }
    public User loginUser(String username, String password){
        return  userDao.loginUser(username,password);
    }
}
