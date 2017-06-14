package henry.code.retrofitrealmsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import henry.code.retrofitrealmsample.databinding.ActivityMainBinding;
import henry.code.retrofitrealmsample.model.Response;
import henry.code.retrofitrealmsample.net.OpenAirQuality;
import henry.code.retrofitrealmsample.realm.RealmDb;
import henry.code.retrofitrealmsample.util.DisposableManager;
import henry.code.retrofitrealmsample.util.DisposingObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnFetchFromInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromInternetWithRx();
            }
        });

        binding.btnShowCountryCodeFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryCodeFromDatabase();
            }
        });
        binding.btnShowCountryNameCodeFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryNameAndCodeFromDatabase();
            }
        });
    }


    @Override
    protected void onDestroy() {
        DisposableManager.dispose();
        super.onDestroy();
    }


    private void fetchDataFromInternetWithRx() {
        OpenAirQuality.fetchData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
                        RealmDb.saveResult(response.getResults());
                    }
                })
                .subscribeWith(new DisposingObserver<Response>() {
                    @Override
                    public void onNext(Response response) {
                        if (response.getResults() != null) {
                            binding.content.setText(response.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.content.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showCountryNameAndCodeFromDatabase() {
        RealmDb.getCountryCodeAndNameAsObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposingObserver<String>() {
                    @Override
                    public void onNext(String string) {
                        binding.content.setText(string);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.content.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showCountryCodeFromDatabase() {
        RealmDb.getCountryCodeAsObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposingObserver<String>() {
                    @Override
                    public void onNext(String result) {
                        binding.content.setText(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.content.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
