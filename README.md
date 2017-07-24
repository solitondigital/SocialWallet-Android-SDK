# SocialWallet-Android-SDK And Sample Code

## 1) Pre Requisites

- Android Studio
- Test Merchant Code, API Key and Username/PIN (please email sdk@solitondigital.io to obtain)

## 2) Adding the Social Wallet SDK to Your Project

The Social Wallet Android SDK is now available at [JCenter Repository](https://bintray.com/solitondigital/SocialWalletAndroidSDK/SocialWalletAndroidSDK). The latest version is available via `jcenter()`:

Add this to your gradle file
```groovy
compile 'com.solitondigital:socialwalletsdk:1.0.3'
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

                String order_id = String.valueOf(System.currentTimeMillis());

                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.merchantCode(merchant_code);
                paymentRequest.amount(Double.valueOf(amount));
                paymentRequest.description(item_description);
                paymentRequest.orderId(order_id);
                paymentRequest.apiKey(api_key);

                SocialWallet wallet = new SocialWallet(production);

                wallet.OneTimePayment(mActivity, paymentRequest);

            }
        });
    }
}
```

### II) How to listen to payment event

Set `PaymentRequestCallback` to `SocialWallet` Payment request event listener. `onPaymentReceived` method will be called upon any payment event.

```java
import com.solitondigital.socialwalletsdk.SocialWallet;
import com.solitondigital.socialwalletsdk.interfaces.PaymentRequestCallback;
import com.solitondigital.socialwalletsdk.model.PaymentRequestResult;

public class MainActivity extends AppCompatActivity{

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Button btnPay = (Button)findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            
                SocialWallet wallet = new SocialWallet(false);
                wallet.setPaymentRequestEventListener(PaymentRequestCallback());

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
}
```
Status = 2 : Payment Succesful (Please email sdk@solitondigital.io for complete list of status codes)

## 4) Moving into Production

Please email sdk@solitondigital.io to obtain production Merchant Code and API Key when you are ready to go live
