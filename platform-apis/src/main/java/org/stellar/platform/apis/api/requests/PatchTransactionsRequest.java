package org.stellar.platform.apis.api.requests;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import lombok.Data;
import org.stellar.platform.apis.shared.Amount;
import org.stellar.platform.apis.shared.Refund;

@Data
public class PatchTransactionsRequest {
  String id;
  String status;

  @SerializedName("amount_in")
  Amount amountIn;

  @SerializedName("amount_out")
  Amount amountOut;

  @SerializedName("amount_fee")
  Amount amountFee;

  @SerializedName("transfer_received_at")
  LocalDateTime transferReceivedAt;

  String message;
  Refund refund;

  @SerializedName("external_transaction_id")
  String externalTransactionId;
}