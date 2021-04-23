package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String organisation;
    private final String url;
    private final String jobTitle;
    private final String description;

    public Experience(LocalDate startDate, LocalDate endDate, String organisation, String url, String jobTitle, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.organisation = organisation;
        this.url = url;
        this.jobTitle = jobTitle;
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, organisation, url, jobTitle, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!organisation.equals(that.organisation)) return false;
        if (!url.equals(that.url)) return false;
        if (!jobTitle.equals(that.jobTitle)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public String toString() {
        return organisation + " " + url + "\n" +
                " c " + startDate + " по " + endDate + " " + jobTitle + "\n" +
                description + "\n";
    }
}
