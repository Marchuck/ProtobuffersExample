package pl.marchuck.protobuffersexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import pl.marchuck.protobuffersexample.model.UserProtos;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Action1<Throwable> err = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Log.e(TAG, "An error occurred: ", throwable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ProtocolBufferAPI().init().getUser(new Random().nextInt(5))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<UserProtos.User>() {
                                    @Override
                                    public void call(UserProtos.User user) {

                                        Log.w(TAG, "firstName: " + user.getFirstName());
                                        Log.w(TAG, "lastNAme: " + user.getLastName());
                                        Log.w(TAG, "id: " + user.getId());

                                        Log.i(TAG, "monster id: " + user.getMonsters(0).getId());
                                        Log.i(TAG, "monster name: " + user.getMonsters(0).getName());
                                        Log.i(TAG, "monster type: " + user.getMonsters(0).getType());

                                        Toast.makeText(MainActivity.this, "received user", Toast.LENGTH_SHORT).show();
                                    }
                                }, err);
                    }
                });
    }
}
