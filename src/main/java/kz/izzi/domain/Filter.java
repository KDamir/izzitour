package kz.izzi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Filter.
 */
@Entity
@Table(name = "filter")
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "probability")
    private Double probability;

    @ManyToOne
    private Answer answer;

    @OneToMany(mappedBy = "filter")
    @JsonIgnore
    private Set<Criterion> criteria = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProbability() {
        return probability;
    }

    public Filter probability(Double probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Filter answer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Set<Criterion> getCriteria() {
        return criteria;
    }

    public Filter criteria(Set<Criterion> criteria) {
        this.criteria = criteria;
        return this;
    }

    public Filter addCriteria(Criterion criterion) {
        this.criteria.add(criterion);
        criterion.setFilter(this);
        return this;
    }

    public Filter removeCriteria(Criterion criterion) {
        this.criteria.remove(criterion);
        criterion.setFilter(null);
        return this;
    }

    public void setCriteria(Set<Criterion> criteria) {
        this.criteria = criteria;
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
        Filter filter = (Filter) o;
        if (filter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filter{" +
            "id=" + getId() +
            ", probability='" + getProbability() + "'" +
            "}";
    }
}
