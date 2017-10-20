package kz.izzi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Place.
 */
@Entity
@Table(name = "place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "avg_price")
    private Double avgPrice;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

    @Column(name = "start_work_time")
    private String startWorkTime;

    @Column(name = "end_work_time")
    private String endWorkTime;

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Place name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public Place avgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
        return this;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getLat() {
        return lat;
    }

    public Place lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public Place lon(String lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public Place startWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
        return this;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public Place endWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
        return this;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Place categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Place addCategories(Category category) {
        this.categories.add(category);
        category.setPlace(this);
        return this;
    }

    public Place removeCategories(Category category) {
        this.categories.remove(category);
        category.setPlace(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
        Place place = (Place) o;
        if (place.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), place.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", avgPrice='" + getAvgPrice() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", startWorkTime='" + getStartWorkTime() + "'" +
            ", endWorkTime='" + getEndWorkTime() + "'" +
            "}";
    }
}
