package nicholasthomson.me.businessqrd;

import android.content.Intent;
import android.provider.ContactsContract;

import org.json.JSONObject;

import nicholasthomson.me.businessqrd.Webb.Response;
import nicholasthomson.me.businessqrd.Webb.Webb;

/**
 * Created by nick on 11/15/14.
 */
public class ServerHandle {
    String authString = null;

    public ServerHandle(String authString) {
        this.authString = authString;
    }

    public void submitQR(JSONObject json) {
        if (authString != null) {
            Webb webb = Webb.create();
            webb.get("http://104.236.53.130:8888/post/")
                    .param("authString", authString)
                    .param("name", json.toString())
                    .ensureSuccess()
                    .asVoid();
        }
    }

    public JSONObject requestContact(JSONObject json) {
        if (authString != null) {
            try {
                Webb webb = Webb.create();
                Response<String> stringResponse = webb.get("http://104.236.53.130:8888/get/")
                        .param("authString", authString)
                        .param("name", json.toString())
                        .ensureSuccess()
                        .asString();
                System.out.println(stringResponse.getBody());
                JSONObject response = new JSONObject(stringResponse.getBody());
                return response;


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    //added heartbeat
    public JSONObject heartBeat() {
        if (authString != null) {
            try {
                Webb webb = Webb.create();
                Response<String> stringResponse = webb.get("http://104.236.53.130:8888/get/")
                        .param("authString", authString)
                        .ensureSuccess()
                        .asString();
                System.out.println(stringResponse.getBody());
                JSONObject response = new JSONObject(stringResponse.getBody());
                return response;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
