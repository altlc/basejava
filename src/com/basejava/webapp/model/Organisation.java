package com.basejava.webapp.model;

import com.basejava.webapp.storage.Link;
import com.basejava.webapp.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {

    private Link homePage;
    private List<Stage> stages = new ArrayList<>();

    public Organisation() {
    }

    public Organisation(String name, String url, Stage... stages) {
        this(new Link(name, url), Arrays.asList(stages));
    }

    public Organisation(Link homePage, List<Stage> stages) {
        this.homePage = homePage;
        this.stages = stages;
    }

    public Organisation(String name, String url, List<Stage> stages) {
        this(new Link(name, url), stages);
    }

    public List<Stage> getStages() {
        return stages;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void addStage(LocalDate startDate, LocalDate endDate, String jobTitle, String description) {
        stages.add(new Stage(startDate, endDate, jobTitle, description));
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, stages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return homePage.equals(that.homePage) && stages.equals(that.stages);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" • ").append(homePage).append("\n");
        for (Stage period : stages) {
            result.append(period);
        }
        return result.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Stage implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String jobTitle;
        private String description;

        public Stage() {

        }

        public Stage(LocalDate startDate, LocalDate endDate, String jobTitle, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(jobTitle, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.jobTitle = jobTitle;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, jobTitle, description);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Stage stage = (Stage) o;
            return startDate.equals(stage.startDate) && endDate.equals(stage.endDate) && jobTitle.equals(stage.jobTitle) && description.equals(stage.description);
        }

        @Override
        public String toString() {
            return " -> c " + startDate + " по " + endDate + " " + jobTitle + "\n" + description + "\n";
        }
    }

}
