package com.vissermc.trivially;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Simple store of some properties
@Service
public class SingleRowService {

    private final SingleRowRepository repo;
    private final String defaultUrl;

    public SingleRowService(SingleRowRepository repo,
                            @Value("${trivially.default-url}") String defaultUrl) {
        this.repo = repo;
        this.defaultUrl = defaultUrl;
    }

    public String getUrl() {
        var view = repo.getRow();
        return view.map(SingleRow::getUrl).orElse(defaultUrl);
    }

    @Transactional
    public void setUrl(String newValue) {
        repo.save(new SingleRow(newValue));
    }
}
