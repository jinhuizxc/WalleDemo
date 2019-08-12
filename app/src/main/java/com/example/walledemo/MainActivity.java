package com.example.walledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.meituan.android.walle.WalleChannelReader;

/**
 * Android打包之多版本、多环境、多渠道
 * https://www.jianshu.com/p/e33f96dce96d?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 *
 * 解决Error:All flavors must now belong to a named flavor dimension. Learn more at https://d.android.com
 * https://blog.csdn.net/syif88/article/details/75009663/
 *
 * 打包:
 * 打包多环境
 * 这里我们直接执行assemble命令，打包所有的buildType*productFlavors
 *
 * 1、配置build.gradle
 * (1) 在位于项目的根目录 build.gradle 文件中添加Walle Gradle插件的依赖， 如下：
 * buildscript {
 *     dependencies {
 *         classpath 'com.android.tools.build:gradle:2.2.3'
 *         classpath 'com.meituan.android.walle:plugin:1.0.3'
 *     }
 * }
 *
 * (2) 在当前App的 build.gradle 文件中apply这个插件，并添加上用于读取渠道号的aar
 *
 * (3) 这里，我根据不同的环境生成了不同包名的apk，方便在手机上同时安装多个环境的应用。为了让gradle动态更改apk的名称和图标，我们需要在manifest文件中使用
 * {app_name}等占位符
 *
 * 2、打包多环境
 * 这里我们直接执行assemble命令，打包所有的buildType*productFlavors
 *
 * 或者使用命令行也可以：
 * gradle assemble
 *
 * 3、打包多渠道
 * 在Project的根目录下新建channel文件：
 * anzhi #安智
 * baidu #百度
 * huawei #华为
 * oppo #oppo
 * wdj #豌豆荚
 * xiaomi #小米
 * yyb #应用宝
 *
 * 执行gradle命令：
 * (1) 打包文件内的渠道包
 * gradle assembleProductRelease -PchannelFile=channel
 *
 * (2) 打包自定义数组内的渠道包
 * gradle assembleProductRelease -PchannelList=qihu,vivo,lenovo
 *
 *
 * 如何生成渠道包
 * 生成渠道包的方式是和assemble${variantName}Channels指令结合，渠道包的生成目录默认存放在 build/outputs/apk/，也可以通过walle闭包中的apkOutputFolder参数来指定输出目录
 * 用法示例：
 * 生成渠道包 ./gradlew clean assembleReleaseChannels
 * 支持 productFlavors ./gradlew clean assembleMeituanReleaseChannels

 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WalleTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView tvEnv = (TextView) findViewById(R.id.tv_env);
        TextView tvChannel = (TextView) findViewById(R.id.tv_channel);
        TextView tvPackage = (TextView) findViewById(R.id.tv_package);
        // 在代码中获取多渠道信息
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        Log.e(TAG, "channel: " + channel);
        // 在代码中获取多环境信息
        int envType = BuildConfig.ENV_TYPE;
        Log.e(TAG, "envType: "+ envType);

        String packageName = getPackageName();

        switch (envType) {
            case EnvType.DEVELOP:
                tvEnv.setText("envType=" + "开发环境");
                break;
            case EnvType.CHECK:
                tvEnv.setText("envType=" + "测试环境");
                break;
            case EnvType.PRODUCT:
                tvEnv.setText("envType=" + "生产环境");
                break;
        }
        tvChannel.setText("channel=" + channel);
        tvPackage.setText("package=" + packageName);




    }
}
