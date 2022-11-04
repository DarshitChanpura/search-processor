/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.search.relevance.transformer.kendraintelligentranking.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.opensearch.common.settings.SecureSetting;
import org.opensearch.common.settings.SecureString;
import org.opensearch.common.settings.Setting;
import org.opensearch.common.settings.Setting.Property;
import org.opensearch.search.relevance.transformer.kendraintelligentranking.configuration.Constants;

public class KendraIntelligentRankerSettings {

  /**
   * Flag controlling whether to invoke Kendra for rescoring.
   */
  public static final Setting<Integer> KENDRA_ORDER_SETTING = Setting.intSetting(Constants.ORDER_SETTING_NAME, 1, 1,
      Property.Dynamic, Property.IndexScope);

  /**
   * Document field to be considered as "body" when invoking Kendra.
   */
  public static final Setting<List<String>> KENDRA_BODY_FIELD_SETTING = Setting.listSetting(Constants.BODY_FIELD_SETTING_NAME, Collections.emptyList(),
      Function.identity(), new FieldSettingValidator(Constants.BODY_FIELD_SETTING_NAME),
      Property.Dynamic, Property.IndexScope);

  /**
   * Document field to be considered as "title" when invoking Kendra.
   */
  public static final Setting<List<String>> KENDRA_TITLE_FIELD_SETTING = Setting.listSetting(Constants.TITLE_FIELD_SETTING_NAME, Collections.emptyList(),
      Function.identity(), new FieldSettingValidator(Constants.TITLE_FIELD_SETTING_NAME),
      Property.Dynamic, Property.IndexScope);

  static final class FieldSettingValidator implements Setting.Validator<List<String>> {

    private String settingName;

    public FieldSettingValidator(final String name) {
      this.settingName = name;
    }

    @Override
    public void validate(List<String> value) {
      if (value != null && value.size() > 1) {
        throw new IllegalArgumentException("[" + this.settingName + "] can have at most 1 element");
      }
    }
  }

  /**
   * The access key (ie login id) for connecting to Kendra.
   */
  public static final Setting<SecureString> ACCESS_KEY_SETTING = SecureSetting.secureString("kendra_intelligent_ranking.aws.access_key", null);

  /**
   * The secret key (ie password) for connecting to Kendra.
   */
  public static final Setting<SecureString> SECRET_KEY_SETTING = SecureSetting.secureString("kendra_intelligent_ranking.aws.secret_key", null);

  /**
   * The session token for connecting to Kendra.
   */
  public static final Setting<SecureString> SESSION_TOKEN_SETTING = SecureSetting.secureString("kendra_intelligent_ranking.aws.session_token", null);

  public static final Setting<String> SERVICE_ENDPOINT_SETTING = Setting.simpleString("kendra_intelligent_ranking.service.endpoint", Setting.Property.NodeScope);

  public static final Setting<String> SERVICE_REGION_SETTING = Setting.simpleString("kendra_intelligent_ranking.service.region", Setting.Property.NodeScope);

  public static final Setting<String> EXECUTION_PLAN_ID_SETTING = Setting.simpleString("kendra_intelligent_ranking.service.execution_plan_id", Setting.Property.NodeScope);

  public static final Setting<String> ASSUME_ROLE_ARN_SETTING = Setting.simpleString("kendra_intelligent_ranking.service.assume_role_arn", Setting.Property.NodeScope);

  public static final List<Setting<?>> getAllSettings() {
    return Arrays.asList(
      KENDRA_ORDER_SETTING,
      KENDRA_BODY_FIELD_SETTING,
      KENDRA_TITLE_FIELD_SETTING,
      ACCESS_KEY_SETTING,
      SECRET_KEY_SETTING,
      SESSION_TOKEN_SETTING,
      SERVICE_ENDPOINT_SETTING,
      SERVICE_REGION_SETTING,
      EXECUTION_PLAN_ID_SETTING,
      ASSUME_ROLE_ARN_SETTING
    );
  }
}
