package org.pythonbyte.jbugsoup.helpers;

import org.pythonbyte.jbugsoup.BugGuideConfiguration;

public class UrlHelpers {
    public static String getBugGuideDataUrlForTaxonId( int bugGuideTaxonId ) {
        return BugGuideConfiguration.URL_PREFIX + "/node/view/" + bugGuideTaxonId + "/data";
    }

    public static String getBugGuideAdvancedTaxonSearchUrl( String searchValue ) {
        return BugGuideConfiguration.URL_PREFIX + "adv_search/taxon.php?q=" + searchValue.replace( " ", "%20");
    }
}
