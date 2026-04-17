package RNCP.TrocSkillHub.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "knowledge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "level")
    private String level;
    
    @Column(name = "category_id")
    private Long categoryId;
}