package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product> {
    int getLastProductId();
    List<Product> getHotProducts();
    List<Product> getOutStandingProducts();
}
