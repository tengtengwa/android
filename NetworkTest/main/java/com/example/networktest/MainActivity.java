package com.example.networktest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = findViewById(R.id.send_request);
        responseText = findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_request) {
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.150/get_data.json")
//                            .url("https://www.baidu.com")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGGSON(responseData);
//                    parseXMLWithJSONObject(responseData);
//                    showResponse(responseData);
                } catch (Exception e) {
                    Log.d("Lpp", "sendRequestWithOkHttp: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void parseJSONWithGGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
        for (App app : appList) {
            Log.d("ttw", "id is : " + app.getId());
            Log.d("ttw", "name is : " + app.getName());
            Log.d("ttw", "version is : " + app.getVersion());
        }
    }


/*    private void parseXMLWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("ttw", "id is: " + id);
                Log.d("ttw", "name is: " + name);
                Log.d("ttw", "version is: " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

/*    private void parseXMLWithPull(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));

//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser = factory.newPullParser();
//            xmlPullParser.setInput(new StringReader(xmlData));
//            int eventType = xmlPullParser.getEventType();
//            String id = "";
//            String name = "";
//            String version = "";
//            while (eventType != xmlPullParser.END_DOCUMENT) {
//                String nodeName = xmlPullParser.getName();
//                switch (eventType) {
//                    case XmlPullParser.END_TAG: {
//                        if ("app".equals(nodeName)) {
//                            Log.d("Mainactivity", "id is: " + id);
//                            Log.d("Mainactivity", "name is: " + name);
//                            Log.d("Mainactivity", "version is: " + version);
//                        }
//                        break;
//                    }
//                    default:
//                        break;
//                }
//                eventType = xmlPullParser.next();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


/*    private void sendRequestWithHttpURLConnection() {
        //开启线程发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream is = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }*/

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
