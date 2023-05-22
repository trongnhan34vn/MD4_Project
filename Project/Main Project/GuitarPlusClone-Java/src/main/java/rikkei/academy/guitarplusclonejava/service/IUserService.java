package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.User;

import java.util.List;

public interface IUserService extends IGenericService<User> {
    User findUserByEmail(String email);
    User checkLogin(String email, String password);
    List<User> findUserByPage(int page, int countPage);
}
