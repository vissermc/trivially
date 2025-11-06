package com.vissermc.trivially;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

// Store all persistent changeable properties in a single row
@Entity
@Table(name = "single_row")
public class SingleRow {

    @Id
    @Column(name = "id")
    final private Long id = 1L;

    @Setter
    @Column(name = "url", nullable = false)
    private String url;

    protected SingleRow() {
        // JPA only
    }

    public SingleRow(String url) {
        this.url = url;
    }

    @SuppressWarnings("LombokGetterMayBeUsed") // cannot be used because of Vite
    public String getUrl() {
        return url;
    }


}
