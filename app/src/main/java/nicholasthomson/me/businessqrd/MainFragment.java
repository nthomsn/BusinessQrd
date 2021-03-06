package nicholasthomson.me.businessqrd;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import nicholasthomson.me.businessqrd.zxing.IntentIntegrator;
import nicholasthomson.me.businessqrd.zxing.IntentResult;

/**
 * Created by Nick on 11/15/14.
 */

public class MainFragment extends Fragment implements View.OnClickListener {


    private static class DownloadQRImage extends AsyncTask<Void, Void, Bitmap> {

        private Activity activity;
        private String authString;

        public DownloadQRImage(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            FetchQRCode fetch = new FetchQRCode();
            fetch.downloadBitmap();
            ServerHandle handle = new ServerHandle(fetch.getGenerated());
            authString = fetch.getGenerated();
            Tabs tabs = (Tabs) activity;
            handle.submitQR(tabs.getInfo());
            return fetch.getQRBitMap();
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            Intent i = new Intent(activity, QRCodeDisplay.class);
            i.putExtra("BitmapImage", b);
            i.putExtra("authString", authString);
            activity.startActivity(i);
        }
    }

    public static class RequestContact extends AsyncTask<String, Void, Boolean> {

        private Activity activity;

        public RequestContact(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            ServerHandle handle = new ServerHandle(strings[0]);
            Tabs tabs = (Tabs) activity;
            JSONObject json = handle.requestContact(tabs.getInfo());
            if (json == null) {
                return false;
            }

            activity.startActivity((new NewContact()).getContactIntent(json));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                Toast.makeText(activity, "Could not find waldo :(", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ImageView button1 = (ImageView) rootView.findViewById(R.id.create_tag_button);
        final ImageView button2 = (ImageView) rootView.findViewById(R.id.scan_tag_button);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_tag_button) {
            new DownloadQRImage(getActivity()).execute();
        } else if (view.getId() == R.id.scan_tag_button) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            new RequestContact(getActivity()).execute(scanResult.getContents());
        }
    }

}
