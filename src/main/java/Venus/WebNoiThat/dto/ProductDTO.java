package Venus.WebNoiThat.dto;

import Venus.WebNoiThat.model.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	private String name;	
	private int categoryId;
	private double price;
	private double weight;
	private int quantity;
	private String description;
	private String imageName;
	private boolean isHot;
}
