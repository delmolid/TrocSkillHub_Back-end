package RNCP.TrocSkillHub.Models;

import jakarta.persistence.*;


@Entity
@Table(name = "user_knowledge")
public class UserKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;
    @Column(name = "level")
    private String level;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private KnowledgeType type; // enum SKILL / NEED

    public UserKnowledge() {}
    public UserKnowledge(User user, Knowledge knowledge, KnowledgeType type, String level) {
        this.user = user;
        this.knowledge = knowledge;
        this.type = type;
        this.level = level;
    }
    // Getters
    public Long getId() {
        return id;
    }
    public User getUserId() {
        return user;
    }
    public Knowledge getKnowledge() {
        return knowledge;
    }
    public KnowledgeType getType() {
        return type;
    }
    public String getLevel() {
        return level;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUserId(User user) {
        this.user = user;
    }
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge= knowledge;
    }
    public void setType(KnowledgeType type) {
        this.type = type;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
public String toString() {
    return "UserKnowledge{" +
            "id=" + id +
            ", userId=" + user +
            ", knowledgeId=" + knowledge+
            ", type=" + type +
            ", level='" + level + '\'' +
            '}';
}
}


