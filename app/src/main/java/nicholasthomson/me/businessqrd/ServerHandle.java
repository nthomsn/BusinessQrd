package nicholasthomson.me.businessqrd;

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
            Webb webb = Webb.create();
            webb.get("http:/[2620:0:2820:a0b:449:4603:9726:774d]:8888/post/")
                    .param("authString", authString)
                    .param("name", "Nick")
                    .param("param3", "c")
                    .ensureSuccess()
                    .asVoid();
        }
    }
}
