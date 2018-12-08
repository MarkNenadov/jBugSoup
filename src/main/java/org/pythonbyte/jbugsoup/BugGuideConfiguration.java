package org.pythonbyte.jbugsoup;

public class BugGuideConfiguration {
    public static final String URL_PREFIX = "http://bugguide.net/";

    public static final int ADVANCED_SEARCH_ID_INDEX = 0;
    public static final int ADVANCED_SEARCH_SCIENTIFIC_NAME_INDEX = 1;
    public static final int ADVANCED_SEARCH_COMMON_NAME_INDEX = 2;
    public static final String ADVANCED_SEARCH_DELIMITER_CHARACTER_REGEX = "\\|";
    public static final int ADVANCED_SEARCH_COLUMN_COUNT = 4;
    public static String TAXON_ID_DOES_NOT_EXIST_MESSAGE = "The BugGuide taxon ID you've provided doesn't seem to exist.";
}
