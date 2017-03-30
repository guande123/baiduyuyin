package com.demo.zgd.gdtts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SpeechSynthesizerListener {
    private static final String TAG = "MainActivity";
    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private String mSampleDirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
      //  initDir();

        startTTS();
    }

    private void initDir() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
    }

    private void makeDir(String sampleDirPath) {
        File file = new File(sampleDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void getPermission() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i =0;i<grantResults.length;i++)
            Log.i(TAG, "onRequestPermissionsResult: "+grantResults[i]);


        if(requestCode==1001&&grantResults[0]==0){

        }else{
            Toast.makeText(MainActivity.this,"read_phone_state permission is not allowed",Toast.LENGTH_SHORT).show();
        }
    }

    // 初始化语音合成客户端并启动
    private void startTTS() {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("wfSabvowhlCLLxGnmbkzT1Uj", "fb8d4be6a973e34a183d695369ee551f");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("9458825");
        // 设置语音合成文本模型文件
        Log.i(TAG, "startTTS: "+getResources().getAssets().getLocales()[0].toString());
        Toast.makeText(MainActivity.this,"startTTS:"+getResources().getAssets().getLocales()[0].toString(),Toast.LENGTH_LONG).show();
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "/assets/bd_etts_text.dat");
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "/assets/bd_etts_speech_male.dat");
        // 设置语音合成声音授权文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "assets/temp_license");
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            mSpeechSynthesizer.speak("百度语音合成示例程序正在运行");
        } else {
            // 授权失败
        }
    }
    public void onError(String arg0, SpeechError arg1) {
        // 监听到出错，在此添加相关操作
        Log.i(TAG, "onError: "+arg1.toString());
    }
    public void onSpeechFinish(String arg0) {
        // 监听到播放结束，在此添加相关操作
        Log.i(TAG, "onSpeechFinish: "+arg0.toString());
    }
    public void onSpeechProgressChanged(String arg0, int arg1) {
        // 监听到播放进度有变化，在此添加相关操作
        Log.i(TAG, "onSpeechProgressChanged: "+arg0.toString());
    }
    public void onSpeechStart(String arg0) {
        // 监听到合成并播放开始，在此添加相关操作
        Log.i(TAG, "onSpeechStart: "+arg0.toString());
    }
    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
        // 监听到有合成数据到达，在此添加相关操作
        Log.i(TAG, "onSynthesizeDataArrived: "+arg0.toString());
    }
    public void onSynthesizeFinish(String arg0) {
        // 监听到合成结束，在此添加相关操作
    }
    public void onSynthesizeStart(String arg0) {
        // 监听到合成开始，在此添加相关操作
        Log.i(TAG, "onSynthesizeStart: "+arg0.toString());
    }

}
