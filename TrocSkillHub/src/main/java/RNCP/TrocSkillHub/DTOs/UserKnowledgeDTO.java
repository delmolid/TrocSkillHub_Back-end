package RNCP.TrocSkillHub.DTOs;


public record UserKnowledgeDTO (
    Long knowledgeId,
    String knowledgeName,
    String level,      
    String type
  ) {}
