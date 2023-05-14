package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rikkei.academy.guitarplusclonejava.service.IImageService;
import rikkei.academy.guitarplusclonejava.service.IMPL.ImageServiceIMPL;

import java.io.File;

@Controller
@RequestMapping("/image")
@PropertySource("classpath:upload-file.properties")
public class ImageController {
    @Value("${file-upload}")
    private String fileUpload;
    IImageService imageService = new ImageServiceIMPL();
    @GetMapping("/delete")
    public String deleteImage(@RequestParam ("idI") int idI, @RequestParam ("idP") int idP) {
        File file = new File(fileUpload + imageService.findById(idI).getUrl());
        if(file.delete()) {
            System.out.println("xoá được r");
        } else {
            System.out.println("đ xoá được");
        }
        imageService.remove(idI);
        return "redirect:/product/update?idU=" + idP;
    }
}
