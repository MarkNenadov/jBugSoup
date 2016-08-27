package org.pythonbyte.java.jbugsoup;

import java.util.ArrayList;
import java.util.List;

public class JBugSoupResultSet {
    List<String> errors = new ArrayList<>();
    List<String> results = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public void addError(String errorValue) {
        errors.add( errorValue );
    }

    public void addResult(String resultValue ) {
        results.add( resultValue );
    }

    public boolean hasErrors() {
        return errors != null && errors.size() > 0;
    }

    public Boolean hasResultValue(String stateName) {
        return results != null && results.contains(stateName);
    }

    public String getIndividualResult() {
        if ( results.size() != 1 ) {
            return null;
        }

        return results.get( 0 );
    }
}
