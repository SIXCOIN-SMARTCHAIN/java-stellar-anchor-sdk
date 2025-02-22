package org.stellar.anchor.platform.config;

import org.stellar.anchor.config.SecretConfig;
import org.stellar.anchor.platform.configurator.SecretManager;

public class PropertySecretConfig implements SecretConfig {

  public static final String SECRET_SEP_10_JWT_SECRET = "secret.sep10.jwt_secret";
  public static final String SECRET_SEP_10_SIGNING_SEED = "secret.sep10.signing_seed";
  public static final String SECRET_SEP_24_INTERACTIVE_URL_JWT_SECRET =
      "secret.sep24.interactive_url.jwt_secret";
  public static final String SECRET_SEP_24_MORE_INFO_URL_JWT_SECRET =
      "secret.sep24.more_info_url.jwt_secret";
  public static final String SECRET_CALLBACK_API_AUTH_SECRET = "secret.callback_api.auth_secret";
  public static final String SECRET_PLATFORM_API_AUTH_SECRET = "secret.platform_api.auth_secret";

  public String getSep10JwtSecretKey() {
    return SecretManager.getInstance().get(SECRET_SEP_10_JWT_SECRET);
  }

  public String getSep10SigningSeed() {
    return SecretManager.getInstance().get(SECRET_SEP_10_SIGNING_SEED);
  }

  @Override
  public String getSep24InteractiveUrlJwtSecret() {
    return SecretManager.getInstance().get(SECRET_SEP_24_INTERACTIVE_URL_JWT_SECRET);
  }

  @Override
  public String getSep24MoreInfoUrlJwtSecret() {
    return SecretManager.getInstance().get(SECRET_SEP_24_MORE_INFO_URL_JWT_SECRET);
  }

  public String getCallbackApiSecret() {
    return SecretManager.getInstance().get(SECRET_CALLBACK_API_AUTH_SECRET);
  }

  public String getPlatformApiSecret() {
    return SecretManager.getInstance().get(SECRET_PLATFORM_API_AUTH_SECRET);
  }
}
