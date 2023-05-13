package rikkei.academy.guitarplusclonejava.service.IImageService;

import rikkei.academy.guitarplusclonejava.model.Image;
import rikkei.academy.guitarplusclonejava.service.IGenericService;

import java.util.List;

public interface IImageService extends IGenericService<Image> {
    List<Image> findImagesByProductId(int pId);
}
