package RNCP.TrocSkillHub.Models;

import jakarta.persistence.*;

import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @JdbcTypeCode(Types.BINARY)
    @Column(nullable = true)
    private byte[] picture;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String address;
    
    @Column(nullable = true)
    private String city;
    
    @Column(name = "country", nullable = true)
    private String country;
    
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserKnowledge> userKnowledge = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experience = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> project = new ArrayList<>();


    public User() {
    }

    public User(String firstName, String lastName, String address, String country, String city, String phoneNumber,
            String email, byte[] picture, List<Education> education, List<Experience> experience,
            List<Project> project) {
       
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picture = picture;
        this.education = education;
        this.experience = experience;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public LocalDate getUpdatedAt(){
        return updatedAt;
    }

    public List<UserKnowledge> getUserKnowledge() {
        return userKnowledge;
      }

    public List<Education> getEducation() {
        return education;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDate updateAt){
        this.updatedAt = updateAt;
    }

    public void setUserKnowledge(List<UserKnowledge> userKnowledge) {
        this.userKnowledge = userKnowledge;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

@Override
public String toString() {
    return "User{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address='" + address + '\'' +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", email='" + email + '\'' +
            ", picture=" + (picture != null ? "byte[" + picture.length + "]" : "null") +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
}
}
