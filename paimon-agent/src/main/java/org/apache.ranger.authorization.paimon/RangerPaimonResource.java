package org.apache.ranger.authorization.paimon;

import org.apache.ranger.plugin.policyengine.RangerAccessResourceImpl;

public class RangerPaimonResource extends RangerAccessResourceImpl {

    public static final String KEY_CATALOG = "catalog";

    public static final String KEY_DATABASE = "database";

    public static final String KEY_TABLE = "table";

    public static final String KEY_COLUMN = "column";

    public RangerPaimonResource(PaimonObjectType objectType, String firstLevelResource) {
        this(objectType, firstLevelResource, null, null, null);
    }

    public RangerPaimonResource(PaimonObjectType objectType, String firstLevelResource, String secondLevelResource) {
        this(objectType, firstLevelResource, secondLevelResource, null, null);
    }

    public RangerPaimonResource(PaimonObjectType objectType, String firstLevelResource, String secondLevelResource,
                                String thirdLevelResource) {
        this(objectType, firstLevelResource, secondLevelResource, thirdLevelResource, null);
    }

    public RangerPaimonResource(PaimonObjectType objectType, String firstLevelResource, String secondLevelResource,
                                String thirdLevelResource, String fourthLevelResource) {
        // set essential info according to objectType
        switch (objectType) {
            case CATALOG:
                setValue(KEY_CATALOG, firstLevelResource);
                break;
            case DATABASE:
                setValue(KEY_CATALOG, firstLevelResource);
                setValue(KEY_DATABASE, secondLevelResource);
                break;
            case TABLE:
                setValue(KEY_CATALOG, firstLevelResource);
                setValue(KEY_DATABASE, secondLevelResource);
                setValue(KEY_TABLE, thirdLevelResource);
                break;
            case COLUMN:
                setValue(KEY_CATALOG, firstLevelResource);
                setValue(KEY_DATABASE, secondLevelResource);
                setValue(KEY_TABLE, thirdLevelResource);
                setValue(KEY_COLUMN, fourthLevelResource);
                break;
            default:
                break;
        }
    }

}
