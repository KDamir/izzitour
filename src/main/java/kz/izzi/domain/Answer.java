package kz.izzi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "result")
    private String result;

    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "answer")
    @JsonIgnore
    private Set<Filter> filters = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "answer_next_questions",
               joinColumns = @JoinColumn(name="answers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="next_questions_id", referencedColumnName="id"))
    private Set<Question> nextQuestions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public Answer result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public Answer filters(Set<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public Answer addFilter(Filter filter) {
        this.filters.add(filter);
        filter.setAnswer(this);
        return this;
    }

    public Answer removeFilter(Filter filter) {
        this.filters.remove(filter);
        filter.setAnswer(null);
        return this;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

    public Set<Question> getNextQuestions() {
        return nextQuestions;
    }

    public Answer nextQuestions(Set<Question> questions) {
        this.nextQuestions = questions;
        return this;
    }

    public Answer addNextQuestions(Question question) {
        this.nextQuestions.add(question);
        return this;
    }

    public Answer removeNextQuestions(Question question) {
        this.nextQuestions.remove(question);
        return this;
    }

    public void setNextQuestions(Set<Question> questions) {
        this.nextQuestions = questions;
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
        Answer answer = (Answer) o;
        if (answer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            "}";
    }
}
