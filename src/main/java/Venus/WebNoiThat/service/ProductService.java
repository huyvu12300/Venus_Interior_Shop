package Venus.WebNoiThat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Venus.WebNoiThat.model.Product;
import Venus.WebNoiThat.repository.ProductRepository;
@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}
	public void addProduct(Product product){
		productRepository.save(product);
	}
	public void removeProductById(long id){
		productRepository.deleteById(id);
	}
	public Optional<Product> getProductById(long id){
		return productRepository.findById(id);
	}
	public List<Product> getAllProductsByCategoryId(int id){
		return productRepository.findAllByCategory_Id(id);
	}

	public Page<Product> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.productRepository.findAll(pageable);
	}

}
