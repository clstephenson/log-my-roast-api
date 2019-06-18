package com.clstephenson.logmyroast.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "coffee_beans")
public class CoffeeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Origin origin;

    @NotNull
    private String farm;

    private String elevation;

    private Processes process;

    @Column(name = "recommended_roast")
    private String recommendedRoast;

    private String notes;

    public CoffeeBean() {
    }

    public CoffeeBean(@NotNull Origin origin, @NotNull String farm) {
        this.origin = origin;
        this.farm = farm;
    }

    public CoffeeBean(@NotNull Origin origin, @NotNull String farm, String elevation, Processes process,
                      String recommendedRoast, String notes) {
        this.origin = origin;
        this.farm = farm;
        this.elevation = elevation;
        this.process = process;
        this.recommendedRoast = recommendedRoast;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public Processes getProcess() {
        return process;
    }

    public void setProcess(Processes process) {
        this.process = process;
    }

    public String getRecommendedRoast() {
        return recommendedRoast;
    }

    public void setRecommendedRoast(String recommendedRoast) {
        this.recommendedRoast = recommendedRoast;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, farm, elevation, process, recommendedRoast, notes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeBean that = (CoffeeBean) o;
        return origin == that.origin &&
                farm.equals(that.farm) &&
                Objects.equals(elevation, that.elevation) &&
                process == that.process &&
                Objects.equals(recommendedRoast, that.recommendedRoast) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public String toString() {
        return "CoffeeBean{" +
                "id=" + id +
                ", origin=" + origin +
                ", farm='" + farm + '\'' +
                ", elevation='" + elevation + '\'' +
                ", process=" + process +
                ", recommendedRoast='" + recommendedRoast + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
