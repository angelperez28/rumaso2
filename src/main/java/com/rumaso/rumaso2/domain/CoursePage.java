package com.rumaso.rumaso2.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CoursePage.
 */
@Entity
@Table(name = "course_page")
public class CoursePage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @ManyToMany
    @JoinTable(name = "course_page_section",
               joinColumns = @JoinColumn(name = "course_page_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "section_id", referencedColumnName = "id"))
    private Set<Section> sections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public CoursePage rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public CoursePage sections(Set<Section> sections) {
        this.sections = sections;
        return this;
    }

    public CoursePage addSection(Section section) {
        this.sections.add(section);
        section.getCoursePages().add(this);
        return this;
    }

    public CoursePage removeSection(Section section) {
        this.sections.remove(section);
        section.getCoursePages().remove(this);
        return this;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursePage)) {
            return false;
        }
        return id != null && id.equals(((CoursePage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CoursePage{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            "}";
    }
}
