package RNCP.TrocSkillHub.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDTO {
    private Long id;
    private String name;
    private String level;
    private Long categoryId;
}