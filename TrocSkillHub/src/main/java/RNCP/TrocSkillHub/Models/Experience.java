package RNCP.TrocSkillHub.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company", nullable = true)
    private String company;

    @Column(name = "job", nullable = true)
    private String job;

    @Column(name = "date_start", nullable = true)
    private LocalDate dateStart;

    @Column(name = "date_end", nullable = true)
    private LocalDate dateEnd;

    // Constructor required by JPA
    public Experience() {
    }

    // Constructor with fields
    public Experience(String company, String job, LocalDate dateStart, LocalDate dateEnd) {
        this.company = company;
        this.job = job;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getJob() {
        return job;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", job='" + job + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
