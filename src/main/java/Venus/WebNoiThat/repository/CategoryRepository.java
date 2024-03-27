package Venus.WebNoiThat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Venus.WebNoiThat.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}