package nicholasthomson.me.businessqrd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;

/**
 * Created by Nick on 11/15/14.
 */
public class FetchQRCode {

    Bitmap qrCode = null;

    private String generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(length, random).toString(32);
    }

    public void downloadBitmap() {

        try {
            URL imageUrl = new URL("http://chart.apis.google.com/chart?cht=qr" +
                    "&chs=300x300&chl=" + generateRandom(60) + "&chld=H|0");
            qrCode = BitmapFactory.decodeStream((InputStream) imageUrl.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bitmap getQRBitMap() {
        return qrCode;
    }
}
