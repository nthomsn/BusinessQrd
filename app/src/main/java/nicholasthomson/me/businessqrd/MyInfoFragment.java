package nicholasthomson.me.businessqrd;

import android.app.Fragment;
import android.os.Bundle;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MyInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View view;

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
        return rootView;
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