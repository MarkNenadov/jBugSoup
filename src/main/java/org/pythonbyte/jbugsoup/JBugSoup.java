package org.pythonbyte.jbugsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pythonbyte.jbugsoup.helpers.UrlHelpers;

import java.io.IOException;

public class JBugSoup {
    public static JBugSoupResultSet findOccurrencesByTaxonId( int taxonId ) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( UrlHelpers.getBugGuideDataUrlForTaxonId( taxonId ) );

            resultSet.addStateColumnData( dataPageDocument );
        } catch ( IOException e ) {
            resultSet.addError(
                    "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]"
            );
        }
        return resultSet;
    }

    private static Document getPageDocumentForUrl(String url ) throws IOException {
        Connection connection = Jsoup.connect( url );

        return connection.get();
    }

    public static Boolean isTaxonIdInState( int taxonId, String stateName ) {
        JBugSoupResultSet resultSet = findOccurrencesByTaxonId( taxonId );

        if ( resultSet.hasErrors() ) {
            return null;
        }

        return resultSet.hasResultValue( stateName );
    }

    public static Boolean isScientificNameInState(String scientificName, String stateName ) {
        JBugSoupResultSet resultSet = JBugSoup.findOccurrencesByScientificName( scientificName );

        if ( resultSet.hasErrors() ) {
            return null;
        }

        return resultSet.hasResultValue( stateName );
    }

    public static JBugSoupResultSet findOccurrencesByScientificName( String scientificName ) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            JBugSoupResultSet taxonIdResultSet = findTaxonIdByScientificName( scientificName );

            if ( !taxonIdResultSet.hasErrors() ) {
                resultSet.addStateColumnData(
                        getDataPageDocumentForTaxon( taxonIdResultSet.getIndividualResult() )
                );
            } else {
                resultSet.addError( BugGuideConfiguration.TAXON_ID_DOES_NOT_EXIST_MESSAGE );
            }
        } catch (IOException e) {
            resultSet.addError(
                    "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]"
            );
        }
        return resultSet;
    }

    private static Document getDataPageDocumentForTaxon( String taxonIdValue ) throws IOException {
        String bugGuideDataUrlForTaxon = UrlHelpers.getBugGuideDataUrlForTaxonId( Integer.valueOf( taxonIdValue ) );
        return  getPageDocumentForUrl( bugGuideDataUrlForTaxon );
    }

    public static JBugSoupResultSet findScientificNameByTaxonId( int taxonId ) {
        return findValueFromAdvancedSearchByTaxonId(
                taxonId,
                BugGuideConfiguration.ADVANCED_SEARCH_SCIENTIFIC_NAME_INDEX
        );
    }

    public static JBugSoupResultSet findCommonNameByTaxonId( int taxonId ) {
        return findValueFromAdvancedSearchByTaxonId(
                taxonId,
                BugGuideConfiguration.ADVANCED_SEARCH_COMMON_NAME_INDEX
        );
    }

    private static JBugSoupResultSet findValueFromAdvancedSearchByTaxonId( int taxonId, int index) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl(
                    UrlHelpers.getBugGuideAdvancedTaxonSearchUrl( String.valueOf( taxonId ) )
            );

            String dataPageDocumentText = dataPageDocument.text();
            String[] dataPageDocumentTextSplit = dataPageDocumentText.split(
                    BugGuideConfiguration.ADVANCED_SEARCH_DELIMITER_CHARACTER_REGEX
            );

            if ( dataPageDocumentTextSplit.length == BugGuideConfiguration.ADVANCED_SEARCH_COLUMN_COUNT ) {
                resultSet.addResult( dataPageDocumentTextSplit[index ] );
            } else {
                resultSet.addError(
                        "Unable to understand response to advanced search [" + dataPageDocumentText + "]"
                );
            }
        } catch (IOException e) {
            resultSet.addError(
                    "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]"
            );
        }

        return resultSet;

    }
    private static JBugSoupResultSet findValueFromAdvancedSearchByScientificName( String scientificName, int index ) {
        JBugSoupResultSet resultSet = new JBugSoupResultSet();
        try {
            Document dataPageDocument = getPageDocumentForUrl( UrlHelpers.getBugGuideAdvancedTaxonSearchUrl( scientificName ) );

            String dataPageDocumentText = dataPageDocument.text();
            String[] dataPageDocumentTextSplit = dataPageDocumentText.split(
                    BugGuideConfiguration.ADVANCED_SEARCH_DELIMITER_CHARACTER_REGEX
            );

            if ( dataPageDocumentTextSplit.length == BugGuideConfiguration.ADVANCED_SEARCH_COLUMN_COUNT ) {
                resultSet.addResult( dataPageDocumentTextSplit[index ] );
            } else {
                resultSet.addError(
                        "Unable to understand response to advanced search [" + dataPageDocumentText + "]"
                );
            }
        } catch (IOException e) {
            resultSet.addError( "IOException when attempting to access BugGuide.net [" + e.getMessage() + "]" );
        }

        return resultSet;

    }

    public static JBugSoupResultSet findTaxonIdByScientificName( String scientificName ) {
        return findValueFromAdvancedSearchByScientificName(
                scientificName,
                BugGuideConfiguration.ADVANCED_SEARCH_ID_INDEX
        );
    }
}
