package nicholasthomson.me.businessqrd;

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

    public void submitQR() {
        if (authString != null) {
            System.out.println("WE ARE TRYING TO ACCESS THE SERVERS!!!!!!!");
            Webb webb = Webb.create();
            webb.get("http://104.236.53.130:8888/post/")
                    .param("authString", authString)
                    .param("name", "Nick")
                    .ensureSuccess()
                    .asVoid();
        }
    }

    public void requestContact() {
        if (authString != null) {
            System.out.println("WE ARE TRYING TO ACCESS THE SERVERS!!!!!!!");
            Webb webb = Webb.create();
            /*webb.get("http://104.236.53.130:8888/get/")
                    .param("authString", authString)
                    .param("name", "Bob")
                    .ensureSuccess()
                    .asVoid();*/
            webb.get("http://104.236.53.130:8888/post/")
                    .param("authString", authString)
                    .param("name", "Bob")
                    .ensureSuccess()
                    .asVoid();
            //System.out.println(res.toString());
        }
    }
}
