package org.pythonbyte.java.jbugsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JBugSoup {
    public static JBugSoupResultSet findOccurancesByBugGuideTaxonId(int bugGuideTaxonId ) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( getBugGuideDataUrlForTaxonId( bugGuideTaxonId ) );

            Elements stateColumnTds = dataPageDocument.getElementsByClass( "bgdata-state-column" );

            if ( stateColumnTds.size() == 0 ) {
                resultSet.addError( "The BugGuide taxon ID you have provided seems to not exist," );
            } else {
                stateColumnTds.forEach(currentElement -> {
                    resultSet.addResult(currentElement.text());
                });
            }
        } catch (IOException e) {
            resultSet.addError( "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]" );
        }
        return resultSet;
    }
    private static Document getPageDocumentForUrl(String url ) throws IOException {
        Connection connection = Jsoup.connect( url );

        return connection.get();
    }

    private static String getBugGuideDataUrlForTaxonId(int bugGuideTaxonId) {
        return BugGuideConfiguration.URL_PREFIX + "/node/view/" + bugGuideTaxonId + "/data";
    }

   private static String getBugGuideAdvancedTaxonSearchUrl(String searchValue) {
        return BugGuideConfiguration.URL_PREFIX + "/adv_search/taxon.php?q=" + searchValue;
    }

    public static Boolean isBugGuideTaxonIdInState(int bugGuideTaxonId, String stateName ) {
        JBugSoupResultSet resultSet = JBugSoup.findOccurancesByBugGuideTaxonId( bugGuideTaxonId );

        if ( resultSet.hasErrors() ) {
            return null;
        }

        return resultSet.hasResultValue( stateName );
    }

    public static Boolean isScientificNameInState(String scientificName, String stateName ) {
        JBugSoupResultSet resultSet = JBugSoup.findOccurancesByScientificName( scientificName );

        if ( resultSet.hasErrors() ) {
            return null;
        }

        return resultSet.hasResultValue( stateName );
    }

    public static JBugSoupResultSet findOccurancesByScientificName(String scientificName) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( getBugGuideDataUrlForTaxonId( Integer.valueOf( findBugGuideTaxonIdByScientificName( scientificName ).getIndividualResult() ) ) );

            Elements stateColumnTds = dataPageDocument.getElementsByClass( "bgdata-state-column" );

            if ( stateColumnTds.size() == 0 ) {
                resultSet.addError( "The BugGuide taxon ID you have provided seems to not exist," );
            } else {
                stateColumnTds.forEach(currentElement -> {
                    resultSet.addResult(currentElement.text());
                });
            }
        } catch (IOException e) {
            resultSet.addError( "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]" );
        }
        return resultSet;
    }

    public static JBugSoupResultSet findScientificNameByBugGuideTaxonId(int bugGuideTaxonId) {
        return findValueFromAdvancedSearchByBugGuideTaxonId( bugGuideTaxonId, BugGuideConfiguration.ADVANCED_SEARCH_SCIENTIFIC_NAME_INDEX );
    }

    public static JBugSoupResultSet findCommonNameByBugGuideTaxonId( int bugGuideTaxonId ) {
        return findValueFromAdvancedSearchByBugGuideTaxonId( bugGuideTaxonId, BugGuideConfiguration.ADVANCED_SEARCH_COMMON_NAME_INDEX );
    }

    private static JBugSoupResultSet findValueFromAdvancedSearchByBugGuideTaxonId(int bugGuideTaxonId, int index) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( getBugGuideAdvancedTaxonSearchUrl( String.valueOf( bugGuideTaxonId ) ) );

            String dataPageDocumentText = dataPageDocument.text();
            String[] dataPageDocumentTextSplit = dataPageDocumentText.split( BugGuideConfiguration.ADVANCED_SEARCH_DELIMITER_CHARACTER_REGEX );

            if ( dataPageDocumentTextSplit.length == BugGuideConfiguration.ADVANCED_SEARCH_COLUMN_COUNT ) {
                resultSet.addResult( dataPageDocumentTextSplit[index ] );
            } else {
                resultSet.addError( "Unable to understand response to advanced search [" + dataPageDocumentText + "]" );
            }
        } catch (IOException e) {
            resultSet.addError( "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]" );
        }

        return resultSet;

    }
    private static JBugSoupResultSet findValueFromAdvancedSearchByScientificName( String scientificName, int index) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( getBugGuideAdvancedTaxonSearchUrl( scientificName ) );

            String dataPageDocumentText = dataPageDocument.text();
            String[] dataPageDocumentTextSplit = dataPageDocumentText.split( BugGuideConfiguration.ADVANCED_SEARCH_DELIMITER_CHARACTER_REGEX );

            if ( dataPageDocumentTextSplit.length == BugGuideConfiguration.ADVANCED_SEARCH_COLUMN_COUNT ) {
                resultSet.addResult( dataPageDocumentTextSplit[index ] );
            } else {
                resultSet.addError( "Unable to understand response to advanced search [" + dataPageDocumentText + "]" );
            }
        } catch (IOException e) {
            resultSet.addError( "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]" );
        }

        return resultSet;

    }

    public static JBugSoupResultSet findBugGuideTaxonIdByScientificName( String scientificName ) {
        return findValueFromAdvancedSearchByScientificName( scientificName, BugGuideConfiguration.ADVANCED_SEARCH_ID_INDEX );
    }

}
