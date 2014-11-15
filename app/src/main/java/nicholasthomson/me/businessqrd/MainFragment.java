package nicholasthomson.me.businessqrd;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Nick on 11/15/14.
 */

public class MainFragment extends Fragment implements View.OnClickListener {


    private static class DownloadQRImage extends AsyncTask<Void, Void, Bitmap> {

        private Activity activity;

        public DownloadQRImage(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            FetchQRCode fetch = new FetchQRCode();
            fetch.downloadBitmap();
            return fetch.getQRBitMap();
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            //Toast.makeText(activity, "Asyn completed!", Toast.LENGTH_LONG).show();
            //System.out.println("ASYN DONE");
            Intent i = new Intent(activity, QRCodeDisplay.class);
            i.putExtra("BitmapImage", b);
            activity.startActivity(i);
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
        final Button button = (Button) rootView.findViewById(R.id.create_tag_button);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_tag_button) {
            new DownloadQRImage(getActivity()).execute();
        }
    }

}
