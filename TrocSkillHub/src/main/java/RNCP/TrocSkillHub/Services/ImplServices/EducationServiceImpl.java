package RNCP.TrocSkillHub.Services.ImplServices;

import java.util.List;

import RNCP.TrocSkillHub.Models.Education;
import RNCP.TrocSkillHub.Repositories.EducationRepository;

public class EducationServiceImpl {

    private final EducationRepository educationRepository

    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Education createEducation(Education education) {
        return educationRepository.save(education);
    }

    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    public Education getEducationById() {

    }

}
