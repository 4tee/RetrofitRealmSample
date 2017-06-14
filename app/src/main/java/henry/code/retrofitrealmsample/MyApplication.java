package henry.code.retrofitrealmsample;

import android.app.Application;

import henry.code.retrofitrealmsample.realm.RealmDb;

/**
 * Created by henry.thetswe on 13/6/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmDb.init(this);
    }
}
