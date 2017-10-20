package kz.izzi.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import kz.izzi.domain.enumeration.CategoryEnum;

/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    private CategoryEnum value;

    private Long placeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEnum getValue() {
        return value;
    }

    public void setValue(CategoryEnum value) {
        this.value = value;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if(categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
