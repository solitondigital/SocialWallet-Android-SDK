# SocialWallet-Android-SDK And Sample Code

## 1) Pre Requisites

- Android Studio
- Test Merchant Code, API Key and Username/PIN (please email sdk@solitondigital.io to obtain)

## 2) Adding the Social Wallet SDK to Your Project

The Social Wallet Android SDK is now available at [JCenter Repository](https://bintray.com/solitondigital/SocialWalletAndroidSDK/SocialWalletAndroidSDK). The latest version is available via `jcenter()`:

Add this to your gradle file
```groovy
compile 'com.solitondigital:socialwalletsdk:1.0.8'
```

## 3) Usage

### I) How to make payment
Create a new object PaymentRequest and initialise it with 
- api_key
- merchant_code
- order_id
- amount
- item_description
- production(false for development)

```java
import com.solitondigital.socialwalletsdk.SocialWallet;
import com.solitondigital.socialwalletsdk.model.PaymentRequest;

public class MainActivity extends AppCompatActivity{

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Button btnPay = (Button)findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.merchantCode(merchant_code);
                paymentRequest.amount(Double.valueOf(amount));
                paymentRequest.description(item_description);
                paymentRequest.orderId(order_id);
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
}
```

### II) How to listen to payment event

`onActivityResult` method will be called upon any payment event.

```java
import com.solitondigital.socialwalletsdk.SocialWallet;
import com.solitondigital.socialwalletsdk.interfaces.PaymentRequestCallback;
import com.solitondigital.socialwalletsdk.model.PaymentRequestResult;

public class MainActivity extends AppCompatActivity{

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
```
Status = 2 : Payment Succesful (Please email sdk@solitondigital.io for complete list of status codes)

## 4) Moving into Production

Please email sdk@solitondigital.io to obtain production Merchant Code and API Key when you are ready to go live
