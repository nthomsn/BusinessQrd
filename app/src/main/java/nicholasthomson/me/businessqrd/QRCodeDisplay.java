package nicholasthomson.me.businessqrd;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;


public class QRCodeDisplay extends Activity {

    public static class MakeHeartbeat extends AsyncTask<String, Void, Boolean> {

        private Activity activity;
        private String authString;

        public MakeHeartbeat(Activity activity, String authString) {
            this.activity = activity;
            this.authString = authString;
            Log.d("Cool", authString);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            JSONObject json = null;
            while(json == null) {
                ServerHandle handle = new ServerHandle(authString);
                json = handle.heartBeat();
            }
            activity.startActivity((new NewContact()).getContactIntent(json));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_display);
        Bitmap b = getIntent().getParcelableExtra("BitmapImage");
        ImageView v = (ImageView) this.findViewById(R.id.QRCodeImageView);
        String authString = getIntent().getStringExtra("authString");
        new MakeHeartbeat(this, authString).execute();
        v.setImageBitmap(b);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.qrcode_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
