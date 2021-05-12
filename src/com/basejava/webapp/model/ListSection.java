package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<String> contentList;

    public ListSection() {

    }

    public ListSection(List<String> contentList) {
        this.contentList = contentList;
    }

    public List<String> getContentList() {
        return contentList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return contentList.equals(that.contentList);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String item : contentList) {
            result.append(" • ").append(item).append("\n");
        }
        return result.toString();
    }
}
