package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.guitarplusclonejava.model.Image;
import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.service.CatalogService.CatalogServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.CatalogService.ICatalogService;
import rikkei.academy.guitarplusclonejava.service.IImageService.IImageService;
import rikkei.academy.guitarplusclonejava.service.IImageService.ImageServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.ProductService.IProductService;
import rikkei.academy.guitarplusclonejava.service.ProductService.ProductServiceIMPL;

import java.io.File;

@Controller
@RequestMapping(value ="/product")
@PropertySource("classpath:upload-file.properties")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    IImageService imageService = new ImageServiceIMPL();

    ICatalogService catalogService = new CatalogServiceIMPL();

    IProductService productService = new ProductServiceIMPL();
    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("products", productService.findAll());
        return "/admin-views/ProductManagement/show1";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("catalogList", catalogService.findAll());
        model.addAttribute("product", new Product());
        return "/admin-views/ProductManagement/create-product-form";
    }

    @PostMapping("/save")
    public String createProduct(
            @RequestParam ("files")MultipartFile[] files,
            @ModelAttribute ("product") Product newProduct,
            @RequestParam ("catalogId") String catalogId
    ) {
        newProduct.setCatalog(catalogService.findById(Integer.parseInt(catalogId)));
        productService.save(newProduct);
        try {
            for (MultipartFile file : files) {
                Image newImage = new Image();
                String fileName = file.getOriginalFilename();
                FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
                newImage.setUrl(fileName);
                newImage.setProductId(productService.getLastProductId());
                imageService.save(newImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/product/create";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        productService.remove(id);
        return "redirect:/product/show";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("catalogList", catalogService.findAll());
        return "/admin-views/ProductManagement/update";
    }
}
