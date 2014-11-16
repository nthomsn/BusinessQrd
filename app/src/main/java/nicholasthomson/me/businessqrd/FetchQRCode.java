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
    String generated = null;

    private String generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        generated = new BigInteger(length, random).toString(32);
        return generated;
    }

    public void downloadBitmap() {

        try {
            URL imageUrl = new URL("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + generateRandom(60));
            qrCode = BitmapFactory.decodeStream((InputStream) imageUrl.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGenerated() {
        return generated;
    }

    Bitmap getQRBitMap() {
        return qrCode;
    }
}
