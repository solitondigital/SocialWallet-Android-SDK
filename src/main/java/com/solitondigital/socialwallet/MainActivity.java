package com.solitondigital.socialwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solitondigital.socialwalletsdk.SocialWalletActivity;
import com.solitondigital.socialwalletsdk.model.PaymentQueryResult;
import com.solitondigital.socialwalletsdk.model.PaymentRequest;

public class MainActivity extends AppCompatActivity{

    private AppCompatActivity mActivity = this;
    PaymentQueryResult mPaymentQueryResult;
    TextView txtMerchant;
    TextView txtOrderId;
    TextView txtItemDesc;
    TextView txtAmount;
    Button btnPay;
    Button btnNewPurchase;

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

    Integer PAYMENT_REQUEST_CODE = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnPay = (Button)findViewById(R.id.btnPay);
        btnNewPurchase = (Button)findViewById(R.id.btnNewPurchase);
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
        btnPay.setVisibility(View.VISIBLE);
        btnNewPurchase.setVisibility(View.GONE);

        setTitle("SocialWallet SDK Demo");

        btnNewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String orderID = String.valueOf(System.currentTimeMillis());
                queryOrderID = orderID;
                txtOrderId.setText(queryOrderID);

                btnPay.setVisibility(View.VISIBLE);
                btnNewPurchase.setVisibility(View.GONE);

            }
        });

        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.merchantCode(merchant_code);
                paymentRequest.amount(Double.valueOf(amount));
                paymentRequest.description(item_desc);
                paymentRequest.orderId(queryOrderID);
                paymentRequest.apiKey(api_key);


                Intent socialWalletIntent = new Intent(getApplicationContext(),SocialWalletActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("paymentRequest", paymentRequest);
                socialWalletIntent.putExtras(args);
                socialWalletIntent.putExtra("production",false);

                startActivityForResult(socialWalletIntent,PAYMENT_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYMENT_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                String status = data.getStringExtra("status");
                String payment_id = data.getStringExtra("payment_id");
                String order_id = data.getStringExtra("order_id");
                String amount = data.getStringExtra("amount");
                String description = data.getStringExtra("description");
                String request_time = data.getStringExtra("request_time");
                String user_name = data.getStringExtra("user_name");
                String emoney_txid = data.getStringExtra("emoney_txid");

                paymentIdLayout.setVisibility(View.VISIBLE);
                txtPaymentId.setText(payment_id);
                txtStatus.setText("Successfull payment");
            }
            else
            {
                txtStatus.setText("Payment error");
            }


            statusLayout.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.GONE);
            btnNewPurchase.setVisibility(View.VISIBLE);
        }
    }
}