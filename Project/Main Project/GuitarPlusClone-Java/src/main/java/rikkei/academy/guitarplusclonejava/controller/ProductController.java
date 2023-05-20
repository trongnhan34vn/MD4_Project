package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.Cart;
import rikkei.academy.guitarplusclonejava.model.Image;
import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.IMPL.CatalogServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.ICatalogService;
import rikkei.academy.guitarplusclonejava.service.IImageService;
import rikkei.academy.guitarplusclonejava.service.IMPL.ImageServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IProductService;
import rikkei.academy.guitarplusclonejava.service.IMPL.ProductServiceIMPL;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

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
        model.addAttribute("config", new Config());
        return "/admin-views/ProductManagement/show1";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("catalogList", catalogService.findAll());
        model.addAttribute("product", new Product());
        return "/admin-views/ProductManagement/create";
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
                fileName = checkExist(fileName, imageService.findAll());
                System.out.println(fileName);
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

    @GetMapping("/delete")
    public String delete(@RequestParam ("idDel") int id) {
        for (Image image : productService.findById(id).getListImgs()) {
            File file = new File(fileUpload + image.getUrl());
            if (file.delete()) {
                System.out.println("xoá được r");
            } else {
                System.out.println("đ xoá được");
            }
        }
        productService.remove(id);
        return "redirect:/product/show";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam ("idU") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("catalogList", catalogService.findAll());
        return "/admin-views/ProductManagement/update";
    }

    @PostMapping("/update")
    public String updateProduct(
            @RequestParam ("files")MultipartFile[] files,
            @ModelAttribute ("product") Product uProduct,
            @RequestParam ("catalogId") String catalogId
    ) {
        uProduct.setCatalog(catalogService.findById(Integer.parseInt(catalogId)));
        productService.save(uProduct);
        try {
            for (MultipartFile file : files) {
                Image newImage = new Image();
                String fileName = file.getOriginalFilename();
                FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
                newImage.setUrl(fileName);
                newImage.setProductId(uProduct.getProductId());
                imageService.save(newImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/product/update?idU=" + uProduct.getProductId();
    }

    @GetMapping("/product-detail")
    public String productDetail(@RequestParam ("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("config", Config.currencyVN);
        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        return "/user-views/product-detail";
    }

    private String checkExist(String fileName, List<Image> listImgs) {
        for (Image image: listImgs) {
            if (image.getUrl().equals(fileName)) {
                String name = fileName.split("\\.")[0];
                String type = fileName.split("\\.")[1];
                fileName = name + "_" + image.getId() + "."+ type;
                return fileName;
            }
        }
        return fileName;
    }
}
