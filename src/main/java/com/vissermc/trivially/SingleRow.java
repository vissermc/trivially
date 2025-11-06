package com.vissermc.trivially;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// Store all persistent changeable properties in a single row
@Entity
@Table(name = "single_row")
public class SingleRow {

    @Id
    @Column(name = "id")
    private Long id = 1L;

    @Setter
    @Getter
    @Column(name = "url", nullable = false)
    private String url;

    protected SingleRow() {
        // JPA only
    }

    public SingleRow(String url) {
        this.url = url;
    }

}
