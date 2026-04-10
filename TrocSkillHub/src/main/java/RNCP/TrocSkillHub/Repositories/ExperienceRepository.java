package RNCP.TrocSkillHub.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import RNCP.TrocSkillHub.Models.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

}