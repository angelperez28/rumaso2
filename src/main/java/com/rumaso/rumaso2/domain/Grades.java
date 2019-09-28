package com.rumaso.rumaso2.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Grades.
 */
@Entity
@Table(name = "grades")
public class Grades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "a_number")
    private Integer aNumber;

    @Column(name = "b_number")
    private Integer bNumber;

    @Column(name = "c_number")
    private Integer cNumber;

    @Column(name = "d_number")
    private Integer dNumber;

    @Column(name = "f_number")
    private Integer fNumber;

    @Column(name = "w_number")
    private Integer wNumber;

    @Column(name = "student_number")
    private Integer studentNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Section section;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getaNumber() {
        return aNumber;
    }

    public Grades aNumber(Integer aNumber) {
        this.aNumber = aNumber;
        return this;
    }

    public void setaNumber(Integer aNumber) {
        this.aNumber = aNumber;
    }

    public Integer getbNumber() {
        return bNumber;
    }

    public Grades bNumber(Integer bNumber) {
        this.bNumber = bNumber;
        return this;
    }

    public void setbNumber(Integer bNumber) {
        this.bNumber = bNumber;
    }

    public Integer getcNumber() {
        return cNumber;
    }

    public Grades cNumber(Integer cNumber) {
        this.cNumber = cNumber;
        return this;
    }

    public void setcNumber(Integer cNumber) {
        this.cNumber = cNumber;
    }

    public Integer getdNumber() {
        return dNumber;
    }

    public Grades dNumber(Integer dNumber) {
        this.dNumber = dNumber;
        return this;
    }

    public void setdNumber(Integer dNumber) {
        this.dNumber = dNumber;
    }

    public Integer getfNumber() {
        return fNumber;
    }

    public Grades fNumber(Integer fNumber) {
        this.fNumber = fNumber;
        return this;
    }

    public void setfNumber(Integer fNumber) {
        this.fNumber = fNumber;
    }

    public Integer getwNumber() {
        return wNumber;
    }

    public Grades wNumber(Integer wNumber) {
        this.wNumber = wNumber;
        return this;
    }

    public void setwNumber(Integer wNumber) {
        this.wNumber = wNumber;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public Grades studentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
        return this;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Section getSection() {
        return section;
    }

    public Grades section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grades)) {
            return false;
        }
        return id != null && id.equals(((Grades) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grades{" +
            "id=" + getId() +
            ", aNumber=" + getaNumber() +
            ", bNumber=" + getbNumber() +
            ", cNumber=" + getcNumber() +
            ", dNumber=" + getdNumber() +
            ", fNumber=" + getfNumber() +
            ", wNumber=" + getwNumber() +
            ", studentNumber=" + getStudentNumber() +
            "}";
    }
}
