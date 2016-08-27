package org.pythonbyte.test;

import junit.framework.TestCase;
import org.pythonbyte.java.jbugsoup.JBugSoup;
import org.pythonbyte.java.jbugsoup.JBugSoupResultSet;

public class JBugSoupTest extends TestCase {
    public void testFindBugGuideTaxonIdByScientificName() {
        assertEquals( "191845", JBugSoup.findBugGuideTaxonIdByScientificName( "Coenagrion interrogatum" ).getIndividualResult() );
    }

    public void testFindScientificNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findScientificNameByBugGuideTaxonId( 191845 );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Coenagrion interrogatum", resultSet.getIndividualResult() );
    }

    public void testFindCommonNameByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findCommonNameByBugGuideTaxonId( 191845 );

        assertNotNull( resultSet.getIndividualResult() );
        assertEquals( "Subarctic Bluet", resultSet.getIndividualResult() );
    }

    public void testFindOccurancesByBugGuideTaxonId() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurancesByBugGuideTaxonId( 191845 );

        assertEquals( 3, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    public void testFindOccurancesByScientificName() {
        JBugSoupResultSet resultSet = JBugSoup.findOccurancesByScientificName( "Coenagrion interrogatum" );

        assertEquals( 3, resultSet.getResults().size() );
        assertEquals( 0, resultSet.getErrors().size() );
        assertTrue( resultSet.hasResultValue( "Manitoba" ) );
        assertTrue( resultSet.hasResultValue( "New Brunswick" ) );
        assertTrue( resultSet.hasResultValue( "Saskatchewan" ) );
    }

    public void testIsBugGuideTaxonIdInState() {
        assertNull( JBugSoup.isBugGuideTaxonIdInState( 1, "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isBugGuideTaxonIdInState( 191845, "Ontario" ) );
        assertTrue( JBugSoup.isBugGuideTaxonIdInState( 191845, "Manitoba" ) );
    }

    public void testIsScientificNameInState() {
        assertNull( JBugSoup.isScientificNameInState( "No existo", "Ontario" ) ); // taxon id doesn't exist
        assertFalse( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Ontario" ) );
        assertTrue( JBugSoup.isScientificNameInState( "Coenagrion interrogatum", "Manitoba" ) );
    }
}