package org.pythonbyte.jbugsoup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JBugSoupTest {
    private static final int COENAGRION_INTERROGATUM_ID = 191845;

    @Test
    public void testFindBugGuideTaxonIdByScientificName() {
        assertEquals( "191845", JBugSoup.findTaxonIdByScientificName( "Coenagrion interrogatum" ).getIndividualResult() );
    }

    @Test
    public void testFindScientificNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findScientificNameByTaxonId( COENAGRION_INTERROGATUM_ID );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Coenagrion interrogatum", resultSet.getIndividualResult() );
    }

    @Test
    public void testFindCommonNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findCommonNameByTaxonId( COENAGRION_INTERROGATUM_ID );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Subarctic Bluet", resultSet.getIndividualResult() );
    }

    @Test
    public void testFindOccurancesByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurrencesByTaxonId( COENAGRION_INTERROGATUM_ID );

        assertEquals( 5, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    @Test
    public void testFindOccurancesByScientificName() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurrencesByScientificName( "Coenagrion interrogatum" );
        resultSet.getErrors().forEach( System.out::println );

        assertEquals( 5, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    @Test
    public void testIsBugGuideTaxonIdInState() {
        assertNull( JBugSoup.isTaxonIdInState( 1, "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isTaxonIdInState( COENAGRION_INTERROGATUM_ID, "Ontario" ) );
        assertTrue( JBugSoup.isTaxonIdInState( COENAGRION_INTERROGATUM_ID, "Manitoba" ) );
    }

    @Test
    public void testIsScientificNameInState() {
        assertNull( JBugSoup.isScientificNameInState( "No existo", "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Ontario" ) );
        assertTrue( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Manitoba" ) );
    }
}
