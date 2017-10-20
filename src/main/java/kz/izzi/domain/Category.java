package kz.izzi.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import kz.izzi.domain.enumeration.CategoryEnum;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_value")
    private CategoryEnum value;

    @ManyToOne
    private Place place;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEnum getValue() {
        return value;
    }

    public Category value(CategoryEnum value) {
        this.value = value;
        return this;
    }

    public void setValue(CategoryEnum value) {
        this.value = value;
    }

    public Place getPlace() {
        return place;
    }

    public Category place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
