package org.pythonbyte.jbugsoup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JBugSoupTest {
    @Test
    public void testFindBugGuideTaxonIdByScientificName() {
        assertEquals( "191845", JBugSoup.findBugGuideTaxonIdByScientificName( "Coenagrion interrogatum" ).getIndividualResult() );
    }

    @Test
    public void testFindScientificNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findScientificNameByBugGuideTaxonId( 191845 );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Coenagrion interrogatum", resultSet.getIndividualResult() );
    }

    @Test
    public void testFindCommonNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findCommonNameByBugGuideTaxonId( 191845 );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Subarctic Bluet", resultSet.getIndividualResult() );
    }

    @Test
    public void testFindOccurancesByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurrencesByBugGuideTaxonId( 191845 );

        assertEquals( 4, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    @Test
    public void testFindOccurancesByScientificName() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurrencesByScientificName( "Coenagrion interrogatum" );
        resultSet.getErrors().forEach( System.out::println );

        assertEquals( 4, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    @Test
    public void testIsBugGuideTaxonIdInState() {
        assertNull( JBugSoup.isBugGuideTaxonIdInState( 1, "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isBugGuideTaxonIdInState( 191845, "Ontario" ) );
        assertTrue( JBugSoup.isBugGuideTaxonIdInState( 191845, "Manitoba" ) );
    }

    @Test
    public void testIsScientificNameInState() {
        assertNull( JBugSoup.isScientificNameInState( "No existo", "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Ontario" ) );
        assertTrue( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Manitoba" ) );
    }
}