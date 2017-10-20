package kz.izzi.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Filter entity.
 */
public class FilterDTO implements Serializable {

    private Long id;

    private Double probability;

    private Long answerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilterDTO filterDTO = (FilterDTO) o;
        if(filterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
            "id=" + getId() +
            ", probability='" + getProbability() + "'" +
            "}";
    }
}
