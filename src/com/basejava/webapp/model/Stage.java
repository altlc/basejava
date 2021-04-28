package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Stage {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String jobTitle;
    private final String description;

    public Stage(LocalDate startDate, LocalDate endDate, String jobTitle, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return " -> c " + startDate + " по " + endDate + " " + jobTitle + "\n" + description + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stage stage = (Stage) o;
        return startDate.equals(stage.startDate) && endDate.equals(stage.endDate) && jobTitle.equals(stage.jobTitle) && description.equals(stage.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, jobTitle, description);
    }
}
