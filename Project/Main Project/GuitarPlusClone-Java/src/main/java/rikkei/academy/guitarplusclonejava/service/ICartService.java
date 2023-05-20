package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Cart;
import rikkei.academy.guitarplusclonejava.model.Product;

import java.util.List;

public interface ICartService extends IGenericService<Cart> {
    List<Cart> findCartByUserId(int id);
    Cart findProductOnUserCart(int uId, int pId);
    void updateCartQuantity(Cart cart);
    void updateCartStatus(int id, boolean setB);
    List<Cart> findSelectedCartInUserCart(int userId);
}
