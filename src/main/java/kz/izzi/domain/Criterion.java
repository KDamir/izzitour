package kz.izzi.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import kz.izzi.domain.enumeration.TypeEnum;

/**
 * A Criterion.
 */
@Entity
@Table(name = "criterion")
public class Criterion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeEnum type;

    @Column(name = "max_value")
    private String maxValue;

    @NotNull
    @Column(name = "min_value", nullable = false)
    private String minValue;

    @ManyToOne
    private Filter filter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEnum getType() {
        return type;
    }

    public Criterion type(TypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public Criterion maxValue(String maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public Criterion minValue(String minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public Filter getFilter() {
        return filter;
    }

    public Criterion filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
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
        Criterion criterion = (Criterion) o;
        if (criterion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), criterion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Criterion{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maxValue='" + getMaxValue() + "'" +
            ", minValue='" + getMinValue() + "'" +
            "}";
    }
}
