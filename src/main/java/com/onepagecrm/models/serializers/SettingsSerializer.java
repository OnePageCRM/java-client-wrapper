package com.onepagecrm.models.serializers;

import com.onepagecrm.models.internal.Commission;
import com.onepagecrm.models.internal.CostSetup;
import com.onepagecrm.models.internal.Country;
import com.onepagecrm.models.internal.Settings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 4/23/16.
 *
 * @author Cillian Myles <cillian@onepagecrm.com> 19/06/2017.
 */
public class SettingsSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(SettingsSerializer.class.getName());

    public static Settings fromJsonObject(JSONObject dataObject) {
        try {
            JSONObject costObject = dataObject.getJSONObject(COST_SETUP_TAG);
            CostSetup costSetup = new CostSetup()
                    .setEnabled(costObject.optBoolean(COST_ENABLED_TAG))
                    .setRequired(costObject.optBoolean(COST_REQUIRED_TAG))
                    .setCommissionBase(Commission.Base.fromString(costObject.optString(COMMISSION_BASE_TAG)))
                    .setCommissionPercentage(costObject.optDouble(COMMISSION_PERCENTAGE_TAG));

            JSONObject settingsObject = dataObject.getJSONObject(SETTINGS_TAG);
            return new Settings()
                    .setTimeZone(settingsObject.optString(TIME_ZONE_TAG))
                    .setDateFormat(settingsObject.optString(DATE_FORMAT_TAG))
                    .setNotWorkingDays(settingsObject.optString(NOT_WORKING_DAYS_TAG))
                    .setMilitaryTime(!settingsObject.optBoolean(TIME_WITH_AMPM_TAG))
                    .setListingSize(settingsObject.optInt(LISTING_SIZE_TAG))
                    .setCurrency(settingsObject.optString(CURRENCY_TAG))
                    .setCurrencySymbol(settingsObject.optString(CURRENCY_SYMBOL_TAG))
                    .setPopularCountries(fromPopularCountryArray(settingsObject.optJSONArray(POPULAR_COUNTRIES_TAG)))
                    .setDealStages(DealStageSerializer.fromJsonArray(settingsObject.optJSONArray(DEAL_STAGES_TAG)))
                    .setDefaultView(settingsObject.optString(DEFAULT_CONTACT_TYPE_TAG))
                    .setSeparator(settingsObject.optString(SEPARATOR_TAG))
                    .setDelimiter(settingsObject.optString(DELIMITER_TAG))
                    .setShowTidyStream(settingsObject.optBoolean(SHOW_TIDY_STREAM_TAG))
                    .setShowCompanyFieldsWithContact(settingsObject.optBoolean(SHOW_COMPANY_FIELDS_WITH_CONTACT_TAG))
                    .setShowCompanyAddress(settingsObject.optBoolean(SHOW_COMPANY_ADDRESS_TAG))
                    .setShowCompanyDescription(settingsObject.optBoolean(SHOW_COMPANY_DESCRIPTION_TAG))
                    .setShowCompanyPhone(settingsObject.optBoolean(SHOW_COMPANY_PHONE_TAG))
                    .setShowCompanyURL(settingsObject.optBoolean(SHOW_COMPANY_URL_TAG))
                    .setCostSetup(costSetup);

        } catch (JSONException e) {
            LOG.severe("Error parsing Settings object");
            LOG.severe(e.toString());
            return new Settings();
        }
    }

    private static List<Country> fromPopularCountryArray(JSONArray choicesArray) {
        List<Country> countries = new LinkedList<>();
        List<String> countryCodes = BaseSerializer.toListOfStrings(choicesArray);
        int lastIndex = countryCodes.size() - 1;
        for (int i = 0; i < countryCodes.size(); i++) {
            Country country = new Country()
                    .setCode(countryCodes.get(i))
                    .setPopularity(lastIndex);
            countries.add(i, country);
            lastIndex--;
        }
        return countries;
    }
}
