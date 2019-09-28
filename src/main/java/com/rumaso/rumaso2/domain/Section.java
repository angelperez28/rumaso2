package com.rumaso.rumaso2.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "semester")
    private String semester;

    @Column(name = "year")
    private Integer year;

    @Column(name = "department")
    private String department;

    @Column(name = "section")
    private String section;

    @OneToOne
    @JoinColumn(unique = true)
    private Professor professor;

    @OneToOne(mappedBy = "section")
    @JsonIgnore
    private Grades grades;

    @ManyToMany(mappedBy = "sections")
    @JsonIgnore
    private Set<CoursePage> coursePages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public Section courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public Section semester(String semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public Section year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public Section department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public Section section(String section) {
        this.section = section;
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Section professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Grades getGrades() {
        return grades;
    }

    public Section grades(Grades grades) {
        this.grades = grades;
        return this;
    }

    public void setGrades(Grades grades) {
        this.grades = grades;
    }

    public Set<CoursePage> getCoursePages() {
        return coursePages;
    }

    public Section coursePages(Set<CoursePage> coursePages) {
        this.coursePages = coursePages;
        return this;
    }

    public Section addCoursePage(CoursePage coursePage) {
        this.coursePages.add(coursePage);
        coursePage.getSections().add(this);
        return this;
    }

    public Section removeCoursePage(CoursePage coursePage) {
        this.coursePages.remove(coursePage);
        coursePage.getSections().remove(this);
        return this;
    }

    public void setCoursePages(Set<CoursePage> coursePages) {
        this.coursePages = coursePages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Section)) {
            return false;
        }
        return id != null && id.equals(((Section) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", semester='" + getSemester() + "'" +
            ", year=" + getYear() +
            ", department='" + getDepartment() + "'" +
            ", section='" + getSection() + "'" +
            "}";
    }
}
