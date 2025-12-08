package com.spotify_api.search_web.model;

import java.util.Set;

public class ItemsPage<T> {
    private String href;
    private int limit;
    private String next;
    private String previous;
    private Set<T> items;

    // Getters and Setters
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Set<T> getItems() {
        return items;
    }

    public void setItems(Set<T> items) {
        this.items = items;
    }
}
