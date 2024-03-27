package Venus.WebNoiThat.controller;

import Venus.WebNoiThat.model.Product;
import Venus.WebNoiThat.service.CategoryService;
import Venus.WebNoiThat.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @GetMapping
    public String Home() {
        return "Home/index";
    }

    @GetMapping("shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        return findPaginated(1, model);
    }
    @RequestMapping("contact")
    public String contact() {
        return "Home/contact";
    }

    @RequestMapping("about")
    public String about() {
        return "Home/about";
    }

    @RequestMapping("shop")
    public String product() {
        return "Home/shop";
    }

    @GetMapping("shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "Home/shop";
    }

    @GetMapping("shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable int id){
        model.addAttribute("product", productService.getProductById(id).get());
        return "Home/viewProduct";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 6;

        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        List<Product> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);
        return "Home/shop";
    }
}

