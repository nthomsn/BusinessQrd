package nicholasthomson.me.businessqrd;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import nicholasthomson.me.businessqrd.R;
import nicholasthomson.me.businessqrd.zxing.IntentIntegrator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyInfoFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View view;
    private Uri selectedImageUri;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MyInfoFragment newInstance(int sectionNumber) {
        MyInfoFragment fragment = new MyInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_my_info, container, false);
        view = rootView;
        view.findViewById(R.id.addPhoto).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:
                if (data != null) {
                    selectedImageUri = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap btemp = BitmapFactory.decodeFile(selectedImageUri.getPath());
                    /// use btemp Image file

                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addPhoto) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(
                    Intent.createChooser(intent, "Complete action using"),
                    1);
        }
    }

    public void updateInfo() {
        JSONObject info = new JSONObject();
        for (View v : view.findViewById(R.id.infoLinearLayout).getTouchables()) {
            if (v instanceof EditText) {
                EditText et = (EditText) v;
                if (et.getText().length() > 2) {
                    try {
                        String hint = (String) et.getHint();
                        hint.replace("_", "");
                        info.put(hint, et.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ((Tabs) getActivity()).setInfo(info);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                updateInfo();
            }
        }
    }
}