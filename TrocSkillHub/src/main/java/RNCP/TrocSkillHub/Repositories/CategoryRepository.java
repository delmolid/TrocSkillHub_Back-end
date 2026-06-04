package RNCP.TrocSkillHub.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import RNCP.TrocSkillHub.Models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
