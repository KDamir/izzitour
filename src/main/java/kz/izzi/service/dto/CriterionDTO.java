package kz.izzi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import kz.izzi.domain.enumeration.TypeEnum;

/**
 * A DTO for the Criterion entity.
 */
public class CriterionDTO implements Serializable {

    private Long id;

    private TypeEnum type;

    private String maxValue;

    @NotNull
    private String minValue;

    private Long filterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CriterionDTO criterionDTO = (CriterionDTO) o;
        if(criterionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), criterionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CriterionDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maxValue='" + getMaxValue() + "'" +
            ", minValue='" + getMinValue() + "'" +
            "}";
    }
}
