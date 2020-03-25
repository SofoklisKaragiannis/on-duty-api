package pensa.on.duty.api.framework;

/**
 * Back-end URI collection
 */
public class V1 {

    private static final String URI_BASE = "/rest/v1/";

    private static final String URI_GET_DUTIES = "getDuties";
    public static final String URI_GET_DUTIES_ABSOLUTE = URI_BASE + URI_GET_DUTIES;

    private static final String URI_ADD_SPECIALIZER_DAYS_OFF = "addSpecializerDaysOff";
    public static final String URI_ADD_SPECIALIZER_DAYS_OFF_ABSOLUTE = URI_BASE + URI_ADD_SPECIALIZER_DAYS_OFF;

    private static final String URI_SET_MONTH = "setMonth";
    public static final String URI_SET_MONTH_ABSOLUTE = URI_BASE + URI_SET_MONTH;

    private static final String URI_INITIALIZE_MONTH = "initializeMonth";
    public static final String URI_SET_INITIALIZE_ABSOLUTE = URI_BASE + URI_INITIALIZE_MONTH;

    private static final String URI_ADD_SPECIALIZER = "addSpecializer";
    public static final String URI_ADD_SPECIALIZER_ABSOLUTE = URI_BASE + URI_ADD_SPECIALIZER;

    private static final String URI_GET_MONTH_STATISTICS = "getMonthStatistics";
    public static final String URI_GET_MONTH_STATISTICS_ABSOLUTE = URI_BASE + URI_GET_MONTH_STATISTICS;
    
    private V1() {
    }
}
