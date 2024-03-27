package Venus.WebNoiThat.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private List<ProductDTO> products; // Danh sách các sản phẩm trong đơn hàng
    private double total; // Tổng số tiền của đơn hàng
    private int user_id;
    private String customerAddress; // Địa chỉ của khách hàng
    private String customerPhone; // Số điện thoại của khách hàng
    // Các thuộc tính khác nếu cần thiết, như địa chỉ giao hàng, số điện thoại, v.v.
}
