package com.example.demo;

import java.util.List;

public class ApiResponse {

    private List<Result> results;

    public String getFirstPlateNumber() {
        if (results != null && !results.isEmpty()) {
            return results.get(0).getPlate();
        }
        return null;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}


