package com.lcoce.www.runsomething.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lcoce.www.runsomething.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WebSocket的测试 - PHP后台
 */
public class WebSocketTest extends Activity {
    @BindView(R.id.mContentEt)
    EditText mContentEt;
    @BindView(R.id.mSentBt)
    Button mSentBt;
    @BindView(R.id.mContentTv)
    TextView mContentTv;

    String target="周刚";
    String targetId;

    private WebSocketClient mSocketClient;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://打开通道
                    mContentTv.setText(mContentTv.getText() + "\n" + msg.obj);
                    mSocketClient.send("{\"type\":\"login\",\"client_name\":\"baby\",\"uid\":\"18380430507\"}");
                    break;
                case 3://接收消息
                    try {
                        JSONObject m = (JSONObject) msg.obj;
                        StringBuffer sb = new StringBuffer();

                        if (m.getString("type").equals("say")) {
                            sb.append(m.getString("from_client_name"));
                            sb.append(":");
                            sb.append(m.getString("content"));
                            sb.append("\n");
                        } else if (m.getString("type").equals("list")) {

                            JSONObject clientList = new JSONObject(m.getString("client_list"));
                            Iterator<String> keys = clientList.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                if (clientList.getString(key).equals(target)){
                                    targetId = key;
                                }
                                sb.append(clientList.getString(key));
                                sb.append(";");
                            }
                        } else if (m.getString("type").equals("ping")) {
                            sb.append("ping");
                        }
                        mContentTv.setText(mContentTv.getText() + "\n" + sb.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    mContentTv.setText(mContentTv.getText() + "\n" + msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocketClient = new WebSocketClient(new URI("ws://oa.lcoce.com:7272"), new Draft_10()) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Log.d("picher_log", "打开通道" + handshakedata.getHttpStatus());
                            handler.obtainMessage(0, "打开通道:" + handshakedata.getHttpStatus()).sendToTarget();
                        }

                        @Override
                        public void onMessage(String message) {
                            Log.d("picher_log", "接收消息" + message);
                            try {
                                handler.obtainMessage(3, new JSONObject(message)).sendToTarget();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("picher_log", "通道关闭");
                            handler.obtainMessage(1, "通道关闭...").sendToTarget();
                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.d("picher_log", "链接错误");
                            handler.obtainMessage(2, "链接错误...").sendToTarget();
                        }
                    };
                    mSocketClient.connect();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        mSentBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSocketClient != null) {
                    //                    {"type":"say","to_client_id":"7f00000108fe000002b9","to_client_name":"niasc","content":"啊擦拭"}
                    mSocketClient.send("{\"type\":\"say\",\"content\":\"" + mContentEt.getText().toString().trim() + "\",\"to_client_id\":\""+targetId+"\",\"to_client_name\":\""+target+"\"}");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocketClient != null) {
            mSocketClient.close();
        }
    }

}
