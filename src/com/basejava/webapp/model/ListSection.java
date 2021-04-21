package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> contentList;

    public ListSection(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return contentList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    public List<String> getContentList() {
        return contentList;
    }
}
