package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.User;

public interface IUserService extends IGenericService<User> {
    User findUserByEmail(String email);
    User checkLogin(String email, String password);

}
