package com.clstephenson.logmyroast.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "roast_log")
public class RoastLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roast_date")
    @NotNull
    private LocalDate roastDate;

    @ManyToOne(targetEntity = Source.class)
    private Source source;

    @ManyToOne(targetEntity = CoffeeBean.class)
    private CoffeeBean coffeeBean;

    @Column(name = "start_weight_grams")
    private int startWeightInGrams;

    @Column(name = "end_weight_grams")
    private int endWeightInGrams;

    @Column(name = "start_roast_time")
    private String startRoastTime;

    @Column(name = "first_crack_start")
    private String firstCrackStartTime;

    @Column(name = "first_crack_end")
    private String firstCrackEndTime;

    @Column(name = "end_roast_time")
    private String endRoastTime;

    @Column(name = "notes")
    private String notes;

    public RoastLogEntry() {
    }

    public RoastLogEntry(@NotNull LocalDate roastDate) {
        this.roastDate = roastDate;
    }

    public RoastLogEntry(@NotNull LocalDate roastDate, Source source, CoffeeBean coffeeBean, int startWeightInGrams,
                         int endWeightInGrams, String firstCrackStartTime, String startRoastTime, String firstCrackEndTime,
                         String endRoastTime, String notes) {
        this.roastDate = roastDate;
        this.source = source;
        this.coffeeBean = coffeeBean;
        this.startWeightInGrams = startWeightInGrams;
        this.endWeightInGrams = endWeightInGrams;
        this.startRoastTime = startRoastTime;
        this.firstCrackStartTime = firstCrackStartTime;
        this.firstCrackEndTime = firstCrackEndTime;
        this.endRoastTime = endRoastTime;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getRoastDate() {
        return roastDate;
    }

    public void setRoastDate(LocalDate roastDate) {
        this.roastDate = roastDate;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
    }

    public int getStartWeightInGrams() {
        return startWeightInGrams;
    }

    public void setStartWeightInGrams(int startWeightInGrams) {
        this.startWeightInGrams = startWeightInGrams;
    }

    public int getEndWeightInGrams() {
        return endWeightInGrams;
    }

    public void setEndWeightInGrams(int endWeightInGrams) {
        this.endWeightInGrams = endWeightInGrams;
    }

    public String getFirstCrackStartTime() {
        return firstCrackStartTime;
    }

    public void setFirstCrackStartTime(String firstCrackStart) {
        this.firstCrackStartTime = firstCrackStart;
    }

    public String getStartRoastTime() {
        return startRoastTime;
    }

    public void setStartRoastTime(String firstCrackPeak) {
        this.startRoastTime = firstCrackPeak;
    }

    public String getFirstCrackEndTime() {
        return firstCrackEndTime;
    }

    public void setFirstCrackEndTime(String firstCrackEnd) {
        this.firstCrackEndTime = firstCrackEnd;
    }

    public String getEndRoastTime() {
        return endRoastTime;
    }

    public void setEndRoastTime(String roastEnd) {
        this.endRoastTime = roastEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roastDate, source, coffeeBean, startWeightInGrams, endWeightInGrams, firstCrackStartTime,
                startRoastTime, firstCrackEndTime, endRoastTime, notes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoastLogEntry that = (RoastLogEntry) o;
        return startWeightInGrams == that.startWeightInGrams &&
                endWeightInGrams == that.endWeightInGrams &&
                roastDate.equals(that.roastDate) &&
                Objects.equals(source, that.source) &&
                Objects.equals(coffeeBean, that.coffeeBean) &&
                Objects.equals(firstCrackStartTime, that.firstCrackStartTime) &&
                Objects.equals(startRoastTime, that.startRoastTime) &&
                Objects.equals(firstCrackEndTime, that.firstCrackEndTime) &&
                Objects.equals(endRoastTime, that.endRoastTime) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public String toString() {
        return "RoastLogEntry{" +
                "id=" + id +
                ", roastDate=" + roastDate +
                ", source=" + source +
                ", coffeeBean=" + coffeeBean +
                ", startWeightInGrams=" + startWeightInGrams +
                ", endWeightInGrams=" + endWeightInGrams +
                ", firstCrackStart='" + firstCrackStartTime + '\'' +
                ", firstCrackPeak='" + startRoastTime + '\'' +
                ", firstCrackEnd='" + firstCrackEndTime + '\'' +
                ", roastEnd='" + endRoastTime + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
