package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.service.IGenericService;

public interface IProductService extends IGenericService<Product> {
    int getLastProductId();
}
