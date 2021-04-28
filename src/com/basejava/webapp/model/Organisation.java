package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organisation {

    private final String organisation;
    private final String url;
    private final List<Stage> stages = new ArrayList<>();

    public Organisation(String organisation, String url) {
        this.organisation = organisation;
        this.url = url;
    }

    public void addStage(LocalDate startDate, LocalDate endDate, String jobTitle, String description) {
        stages.add(new Stage(startDate, endDate, jobTitle, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return organisation.equals(that.organisation) && url.equals(that.url) && stages.equals(that.stages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisation, url, stages);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" • ").append(organisation).append(" ").append(url).append("\n");
        for(Stage period: stages) {
            result.append(period);
        }
        return result.toString();
    }
}
