package cn.edu.sustc.androidclient;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.sustc.androidclient.api.APIInterface;
import cn.edu.sustc.androidclient.common.APIClient;
import cn.edu.sustc.androidclient.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    @BindView(R.id.request_method_spinner)
    Spinner methodSpinner;
    @BindView(R.id.urlEt)
    EditText urlEt;
    @BindView(R.id.sendBtn)
    Button sendBtn;
    @BindView(R.id.requestEt)
    EditText requestEt;
    @BindView(R.id.responseEt)
    EditText responseEt;

    private String url;
    private String method;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // butter knife bind
        ButterKnife.bind(MainActivity.this);

        // Retrofit2
        retrofit = APIClient.getClient();
        // Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        // Spinner
        final String[] method_names = {"GET", "POST", "DELETE", "PATCH"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, method_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodSpinner.setAdapter(adapter);

        methodSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        method = method_names[pos];
                        Toast.makeText(MainActivity.this,
                                "Http Method: " + method, Toast.LENGTH_SHORT).show();
                        if (method.equals("GET") || method.equals("DELETE")){
                            requestEt.setEnabled(false);
                        }else{
                            requestEt.setEnabled(true);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }




    @OnClick({R.id.sendBtn})
    public void OnClick(View view){
        // URL
        url = urlEt.getText().toString();
        if (url.length() == 0){
            // alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false)
                    .setMessage(getResources().getString(R.string.alert_url_none))
                    .setTitle(getResources().getString(R.string.alert));
            Logger.w("No URL is provided");

            builder.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }
            );

            builder.show();
        }else{
            Toast.makeText(MainActivity.this, "URL: " + url, Toast.LENGTH_SHORT).show();
            // send request
            APIInterface request = retrofit.create(APIInterface.class);
            Call<JsonObject> call = request.doGet(url);
            call.enqueue(
                    new Callback<JsonObject>(){
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            String result = "";
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            result = gson.toJson(response.body().getAsJsonObject());
                            responseEt.setText(result);
                            Logger.json(result);
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            // alert dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setCancelable(false)
                                    .setMessage(getResources().getString(R.string.alert_failed))
                                    .setTitle(getResources().getString(R.string.alert));

                            builder.setPositiveButton(getResources().getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }
                            );
                        }
                    }
            );
        }
    }
}
