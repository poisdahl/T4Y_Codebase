package de.tum.score.transport4you.mobile.communication;

/**
 * Created by poi on 18.01.16.
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.tum.score.transport4you.mobile.R;
import de.tum.score.transport4you.mobile.application.applicationcontroller.impl.ApplicationSingleton;
import de.tum.score.transport4you.mobile.presentation.presentationmanager.impl.Contents;
import de.tum.score.transport4you.shared.mobilebusweb.data.impl.BlobEntry;

public class GenerateQRCodeActivity extends Activity implements OnClickListener{

    public static String ticketCode = "#####";

    private String LOG_TAG = "GenerateQRCode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "***** CREATING QR");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodepage);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                BlobEntry een = ApplicationSingleton.getApplicationController().getStoredBlobEntry();
                String qrInputText = een.geteTicketList().get(0).getCustomerId() + "#####" + GenerateQRCodeActivity.ticketCode.substring(0,30);
                Log.v(LOG_TAG, qrInputText);

                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }


                break;

            // More buttons go here (if any) ...
            case R.id.button2:
                BlobEntry een2 = ApplicationSingleton.getApplicationController().getStoredBlobEntry();
                String qrInputText2 = een2.geteTicketList().get(0).getCustomerId() + "#####" + "quatsch";
                Log.v(LOG_TAG, qrInputText2);

                //Find screen size
                WindowManager manager2 = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display2 = manager2.getDefaultDisplay();
                Point point2 = new Point();
                display2.getSize(point2);
                int width2 = point2.x;
                int height2 = point2.y;
                int smallerDimension2 = width2 < height2 ? width2 : height2;
                smallerDimension = smallerDimension2 * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder2 = new QRCodeEncoder(qrInputText2,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder2.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.imageView2);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }


                break;

        }
    }

}
