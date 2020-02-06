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

    @Column(name = "first_crack_start")
    private String firstCrackStart;

    @Column(name = "first_crack_peak")
    private String firstCrackPeak;

    @Column(name = "first_crack_end")
    private String firstCrackEnd;

    @Column(name = "roast_end")
    private String roastEnd;

    @Column(name = "notes")
    private String notes;

    public RoastLogEntry() {
    }

    public RoastLogEntry(@NotNull LocalDate roastDate) {
        this.roastDate = roastDate;
    }

    public RoastLogEntry(@NotNull LocalDate roastDate, Source source, CoffeeBean coffeeBean, int startWeightInGrams,
                         int endWeightInGrams, String firstCrackStart, String firstCrackPeak, String firstCrackEnd,
                         String roastEnd, String notes) {
        this.roastDate = roastDate;
        this.source = source;
        this.coffeeBean = coffeeBean;
        this.startWeightInGrams = startWeightInGrams;
        this.endWeightInGrams = endWeightInGrams;
        this.firstCrackStart = firstCrackStart;
        this.firstCrackPeak = firstCrackPeak;
        this.firstCrackEnd = firstCrackEnd;
        this.roastEnd = roastEnd;
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

    public String getFirstCrackStart() {
        return firstCrackStart;
    }

    public void setFirstCrackStart(String firstCrackStart) {
        this.firstCrackStart = firstCrackStart;
    }

    public String getFirstCrackPeak() {
        return firstCrackPeak;
    }

    public void setFirstCrackPeak(String firstCrackPeak) {
        this.firstCrackPeak = firstCrackPeak;
    }

    public String getFirstCrackEnd() {
        return firstCrackEnd;
    }

    public void setFirstCrackEnd(String firstCrackEnd) {
        this.firstCrackEnd = firstCrackEnd;
    }

    public String getRoastEnd() {
        return roastEnd;
    }

    public void setRoastEnd(String roastEnd) {
        this.roastEnd = roastEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roastDate, source, coffeeBean, startWeightInGrams, endWeightInGrams, firstCrackStart,
                firstCrackPeak, firstCrackEnd, roastEnd, notes);
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
                Objects.equals(firstCrackStart, that.firstCrackStart) &&
                Objects.equals(firstCrackPeak, that.firstCrackPeak) &&
                Objects.equals(firstCrackEnd, that.firstCrackEnd) &&
                Objects.equals(roastEnd, that.roastEnd) &&
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
                ", firstCrackStart='" + firstCrackStart + '\'' +
                ", firstCrackPeak='" + firstCrackPeak + '\'' +
                ", firstCrackEnd='" + firstCrackEnd + '\'' +
                ", roastEnd='" + roastEnd + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
