package org.pythonbyte.jbugsoup.helpers;

import org.pythonbyte.jbugsoup.BugGuideConfiguration;

public class UrlHelpers {
    public static String getBugGuideDataUrlForTaxonId( int taxonId ) {
        return BugGuideConfiguration.URL_PREFIX + "/node/view/" + taxonId + "/data";
    }

    public static String getBugGuideAdvancedTaxonSearchUrl( String searchValue ) {
        String escapedQuery = searchValue.replace(" ", "%20");
        return BugGuideConfiguration.URL_PREFIX + "adv_search/taxon.php?q=" + escapedQuery;
    }
}
