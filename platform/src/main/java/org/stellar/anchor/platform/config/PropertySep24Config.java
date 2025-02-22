package org.stellar.anchor.platform.config;

import static org.stellar.anchor.util.StringHelper.isEmpty;
import static org.stellar.anchor.util.StringHelper.snakeToCamelCase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.stellar.anchor.config.SecretConfig;
import org.stellar.anchor.config.Sep24Config;
import org.stellar.anchor.platform.data.JdbcSep24Transaction;
import org.stellar.anchor.util.NetUtil;

@Getter
@Setter
public class PropertySep24Config implements Sep24Config, Validator {
  static List<String> validFields =
      Arrays.stream(JdbcSep24Transaction.class.getDeclaredFields())
          .sequential()
          .map(Field::getName)
          .collect(Collectors.toList());
  boolean enabled;
  InteractiveUrlConfig interactiveUrl;
  MoreInfoUrlConfig moreInfoUrl;
  SecretConfig secretConfig;

  public PropertySep24Config(SecretConfig secretConfig) {
    this.secretConfig = secretConfig;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class InteractiveUrlConfig {
    String baseUrl;
    long jwtExpiration;
    List<String> txnFields;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MoreInfoUrlConfig {
    String baseUrl;
    long jwtExpiration;
    List<String> txnFields;
  }

  @Override
  public boolean supports(@NotNull Class<?> clazz) {
    return Sep24Config.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(@NotNull Object target, @NotNull Errors errors) {
    if (enabled) {
      validateInteractiveUrlConfig(errors);
      validateMoreInfoUrlConfig(errors);
    }
  }

  void validateInteractiveUrlConfig(Errors errors) {
    if (interactiveUrl == null) {
      errors.rejectValue(
          "interactiveUrl",
          "sep24-interactive-url-invalid",
          "sep24.interactive_url is not defined.");
    } else {
      if (!NetUtil.isUrlValid(interactiveUrl.baseUrl)) {
        errors.rejectValue(
            "interactiveUrl",
            "sep24-interactive-url-base-url-not-valid",
            String.format(
                "sep24.interactive_url.base_url:[%s] is not a valid URL.", interactiveUrl.baseUrl));
      }
      if (interactiveUrl.jwtExpiration <= 0) {
        errors.rejectValue(
            "interactiveUrl",
            "sep24-interactive-url-jwt-expiration-not-valid",
            String.format(
                "sep24.interactive_url.jwt_expiration:[%s] must be greater than 0.",
                interactiveUrl.jwtExpiration));
      }
      for (String field : interactiveUrl.txnFields) {
        if (!isEmpty(field)) {
          if (!validFields.contains(snakeToCamelCase(field))) {
            errors.rejectValue(
                "interactiveUrl.txnFields",
                "sep24-interactive-url-txn-fields-not-valid",
                String.format(
                    "sep24.interactive_url.txn_fields contains the field:[%s] which is not valid transaction field",
                    field));
          }
        }
      }
      if (isEmpty(secretConfig.getSep24InteractiveUrlJwtSecret())) {
        errors.reject(
            "sep24-interactive-url-jwt-secret-not-defined",
            "Please set the secret.sep24.interactive_url.jwt_secret or SECRET_SEP24_INTERACTIVE_URL_JWT_SECRET environment variable");
      }
    }
  }

  void validateMoreInfoUrlConfig(Errors errors) {
    if (moreInfoUrl == null) {
      errors.rejectValue(
          "moreInfoUrl", "sep24-moreinfo-url-invalid", "sep24.more-info-url is not defined.");
    } else {
      if (!NetUtil.isUrlValid(moreInfoUrl.baseUrl)) {
        errors.rejectValue(
            "moreInfoUrl",
            "sep24-more-info-url-base-url-not-valid",
            String.format(
                "sep24.more_info_url.base_url:[%s] is not a valid URL.", moreInfoUrl.baseUrl));
      }
      if (moreInfoUrl.jwtExpiration <= 0) {
        errors.rejectValue(
            "moreInfoUrl",
            "sep24-more-info-url-jwt-expiration-not-valid",
            String.format(
                "sep24.more_info_url.jwt_expiration:[%s] must be greater than 0.",
                moreInfoUrl.jwtExpiration));
      }
      for (String field : moreInfoUrl.txnFields) {
        if (!isEmpty(field)) {
          if (!validFields.contains(snakeToCamelCase(field))) {
            errors.rejectValue(
                "moreInfoUrl.txnFields",
                "sep24-more_info-url-txn-fields-not-valid",
                String.format(
                    "sep24.more_info_url.txn_fields contains the field:[%s] which is not valid transaction field",
                    field));
          }
        }
      }
      if (isEmpty(secretConfig.getSep24InteractiveUrlJwtSecret())) {
        errors.reject(
            "sep24-more-info-url-jwt-secret-not-defined",
            "Please set the secret.sep24.more_info_url.jwt_secret or SECRET_SEP24_MORE_INFO_URL_JWT_SECRET environment variable");
      }
    }
  }
}
