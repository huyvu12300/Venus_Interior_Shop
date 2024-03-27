package Venus.WebNoiThat.controller;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import Venus.WebNoiThat.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Venus.WebNoiThat.dto.ProductDTO;
import Venus.WebNoiThat.model.Category;
import Venus.WebNoiThat.service.CategoryService;
import Venus.WebNoiThat.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@GetMapping("/admin")
	public String adminHome() {
		return "Admin/adminHome";
	}
	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "Admin/categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "Admin/categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "/Admin/categoriesAdd";
		}else
			return "404";
	}
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		return "Admin/products";
	}
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "Admin/productsAdd";
	}
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
								 @RequestParam("productImage")MultipartFile file,
								 @RequestParam("imgName")String imgName) throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setQuantity(productDTO.getQuantity());
		product.setDescription(productDTO.getDescription());
		product.setHot(productDTO.isHot());
		String imageUUID;
		if(!file.isEmpty()){
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else{
			imageUUID = imgName;
		}
		product.setImageName((imageUUID));
		productService.addProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable long id, Model model,@RequestParam(name = "isHot", required = false) boolean isHot
	){
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setDescription(productDTO.getDescription());
		productDTO.setImageName(product.getImageName());
		productDTO.setHot(isHot);
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		return "Admin/productsAdd";
	}

}
