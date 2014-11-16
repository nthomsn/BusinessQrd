package nicholasthomson.me.businessqrd;

import android.content.Intent;
import android.provider.ContactsContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nick on 11/16/14.
 */
public class NewContact {
    public Intent getContactIntent(JSONObject json) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        try {
            json = new JSONObject(json.getString("name"));
            intent.putExtra(ContactsContract.Intents.Insert.NAME, json.getString("First Name")
                    + " " +json.getString("Last Name"));
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, json.getString("Phone Number"));
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, json.getString("Email Address"));
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, json.getString("Address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return intent;
    }
}
