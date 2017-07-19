package com.solitondigital.socialwallet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solitondigital.socialwalletsdk.SocialWallet;
import com.solitondigital.socialwalletsdk.interfaces.PaymentQueryCallback;
import com.solitondigital.socialwalletsdk.interfaces.PaymentRequestCallback;
import com.solitondigital.socialwalletsdk.model.PaymentQueryResult;
import com.solitondigital.socialwalletsdk.model.PaymentRequest;
import com.solitondigital.socialwalletsdk.model.PaymentRequestResult;

public class MainActivity extends AppCompatActivity{

    private Activity mActivity = this;
    PaymentQueryResult mPaymentQueryResult;
    TextView txtMerchant;
    TextView txtOrderId;
    TextView txtItemDesc;
    TextView txtAmount;

    LinearLayout paymentIdLayout;
    TextView txtPaymentId;
    LinearLayout statusLayout;
    TextView txtStatus;

    String queryOrderID = String.valueOf(System.currentTimeMillis());

    //Please email sdk@solitondigital.io to get the merchant code and api_key
    String merchant_code = "";
    String api_key = "";
    String amount = "1.00";
    String item_desc = "Sandisk 32GB Memory Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Button btnPay = (Button)findViewById(R.id.btnPay);
        txtMerchant = (TextView)findViewById(R.id.merchant);
        txtOrderId = (TextView)findViewById(R.id.orderID);
        txtItemDesc = (TextView)findViewById(R.id.itemDesc);
        txtAmount = (TextView)findViewById(R.id.amount);

        paymentIdLayout = (LinearLayout)findViewById(R.id.paymentID_layout);
        txtPaymentId = (TextView)findViewById(R.id.paymentID);
        statusLayout = (LinearLayout)findViewById(R.id.status_layout);
        txtStatus = (TextView)findViewById(R.id.status);

        txtMerchant.setText(merchant_code);
        txtOrderId.setText(queryOrderID);
        txtItemDesc.setText(item_desc);
        txtAmount.setText(amount);
        paymentIdLayout.setVisibility(View.GONE);
        statusLayout.setVisibility(View.GONE);


        setTitle("SocialWallet SDK Demo");

        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String orderID = String.valueOf(System.currentTimeMillis());
                queryOrderID = orderID;

                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.merchantCode(merchant_code);
                paymentRequest.amount(Double.valueOf(amount));
                paymentRequest.description(item_desc);
                paymentRequest.orderId(orderID);
                paymentRequest.apiKey(api_key);

                SocialWallet wallet = new SocialWallet(false);
                wallet.setPaymentRequestEventListener(PaymentRequestCallback());
                wallet.setPaymentQueryEventListener(PaymentQueryCallback());

                wallet.OneTimePayment(mActivity, paymentRequest);

            }
        });
    }

    private PaymentRequestCallback PaymentRequestCallback(){
        return new PaymentRequestCallback() {

            @Override
            public void onPaymentReceived(PaymentRequestResult callbackResult) {
                txtMerchant.setText(callbackResult.getMerchant());
                txtOrderId.setText(callbackResult.getOrderId());
                paymentIdLayout.setVisibility(View.VISIBLE);
                txtPaymentId.setText(callbackResult.getPaymentId());
                statusLayout.setVisibility(View.VISIBLE);

                if(callbackResult.getStatus().equalsIgnoreCase("2")) {
                    txtStatus.setText("Successfull payment");
                }
                else
                {
                    txtStatus.setText("Payment error");
                }
            }
        };
    }

    private PaymentQueryCallback PaymentQueryCallback(){
        return new PaymentQueryCallback() {
            @Override
            public void onResultReceived(PaymentQueryResult pqrResult) {
            }
        };
    }
}