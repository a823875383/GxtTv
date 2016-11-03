package com.jsqix.gxt.tv.update;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.api.InterfaceJSONPost;
import com.jsqix.gxt.tv.api.JSONPost;
import com.jsqix.gxt.tv.obj.BaseObj;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.KeyUtils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownServeStart implements InterfaceJSONPost/* extends Activity implements InstallReceiverListener */ {
    private Context context;
    private boolean iposIsInstalled = false;
    private String installedVersion = "1.0.0";
    private InstallReceiver receiver;
    private File cacheDir;
    private String cachePath;
    // private String urlpath;
    private SilentUpdateObj mUpdate;
    private String apkName;
    private Logger gLogger;

    public DownServeStart() {
    }

    public DownServeStart(Context context) {
        Log.v("context", context.toString());
        start(context);
    }

    // @Override
    // protected void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_main);
    // findViewById(R.id.btn_quickpay).setOnClickListener(
    // new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // urlpath =
    // "http://d2.eoemarket.com/app0/89/89798/apk/719546.apk?channel_id=426";
    // apkName="114SL.apk";
    // start(DownServeStart.this);
    // init();
    // new Thread(checkVersionRunnable).start();
    //
    // }
    // });
    // }

    public void execute(Intent intent, Logger gLogger) {
        if (intent != null) {
            // urlpath = intent.getStringExtra("urlpath");
            // apkName = intent.getStringExtra("apkName");
            apkName = "gxtTv.apk";
        }
        init();
        this.gLogger = gLogger;
//		new Thread(checkVersionRunnable).start();
        checkVersion();
    }

    private void checkVersion() {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("deviceId", ACache.get(context).getAsString(KeyUtils.S_ID));
        JSONPost post = new JSONPost(context, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };

        post.execute(HttpUtil.CHECK_VERSION);
    }

    private void start(Context context) {
        this.context = context;
        // Log.v("GetUpdateState", "..添加BroadcastReceiver...");
        // // AlertUtil.getPdl(context, "提示:", "正在处理中....");
        // IntentFilter filter = new IntentFilter(
        // "android.intent.action.PACKAGE_ADDED");
        // filter.addAction("android.intent.action.PACKAGE_REMOVED");
        // filter.addDataScheme("package");
        // this.receiver = new InstallReceiver();
        // this.receiver.setIPOSUtilsListener(this);
        // this.context.registerReceiver(this.receiver, filter);
    }

    private synchronized void init() {
        PackageInfo packageInfo = getPackageInfo();
        this.cacheDir = this.context.getCacheDir();
//        this.cacheDir = this.context.getFilesDir();
        this.cachePath = this.cacheDir.getAbsolutePath() + File.separator
                + apkName;
        if (packageInfo == null) {
            this.iposIsInstalled = false;
        } else {
            this.iposIsInstalled = true;
            this.installedVersion = packageInfo.versionName;
        }

    }

    /*private Runnable checkVersionRunnable = new Runnable() {

        public void run() {
            try {
                mUpdate = BoxService.checkVersion();
                if (mUpdate != null) {
                    gLogger.info(mUpdate.toString());
                    Log.v("GetUpdateState", mUpdate.toString());
                    if (mUpdate.getAppVersion() != null
                            && !("V" + installedVersion)
                                    .equalsIgnoreCase(mUpdate.getAppVersion())) {
                        Message msg = new Message();
                        msg.what = 0;
                        myHandler.sendMessage(msg);
                    } else if (iposIsInstalled) {
                        Log.v("GetUpdateState", "..已安装最新版本...");
                        gLogger.info("..The latest version is already installed...");
                        // startApk();

                    } else if (new File(cachePath).exists()) {
                        Message msg = new Message();
                        msg.what = 1;
                        myHandler.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                Log.v("GetUpdateState", "..查询更新失败..." + e.getMessage());
                gLogger.info("..Query update failed..." + e.getMessage());
                e.printStackTrace();
            }
        }
    };*/
    private Runnable downApk = new Runnable() {

        @Override
        public void run() {
            boolean isSuccess = new NetworkManager(context).urlDownloadToFile(
                    context, mUpdate.getObj().getDownUrl(), cachePath);
            if (!isSuccess) {
                Log.v("GetUpdateState", "..下载失败...");
                gLogger.info("..Download failed...");

            } else {
                Message msg = new Message();
                msg.what = 1;
                myHandler.sendMessage(msg);
            }

        }
    };
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // AlertUtil.close();
            switch (msg.what) {
                case 0:
                    showDownApkDialog();
                    break;
                case 1:
                    showInstallDialog(cachePath);
                    break;
            }
        }

    };

    private void showDownApkDialog() {
        // AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
        // tDialog.setTitle("下载提示").setMessage("您暂未安装商旅插件。\n点击确定，立即下载。");
        // tDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
        // {
        // public void onClick(DialogInterface dialog, int which) {
        // Toast.makeText(context, "后台下载中....", Toast.LENGTH_LONG).show();
        // new Thread(downApk).start();
        // }
        // });
        // tDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
        // {
        // public void onClick(DialogInterface dialog, int which) {
        // dialog.dismiss();
        // }
        // });
        // tDialog.setCancelable(false);
        // tDialog.show();
        Log.v("GetUpdateState", "..开始下载...");
        gLogger.info("..Start the download...");
        new Thread(downApk).start();

    }

    private Runnable sclient = new Runnable() {

        @Override
        public void run() {
            // intasllApk(new File(cachePath));

            int ret = PackageUtils.install(context,
                    new File(cachePath).getPath());
            Log.v("GetUpdateState", ret + "");
            gLogger.info("result: " + ret);
        }
    };

    private void showInstallDialog(final String cachePath) {
        final File file = new File(cachePath);
        if (file.exists()) {
            Log.v("GetUpdateState", "文件路径：" + file.getAbsolutePath());
            gLogger.info("file path：" + file.getAbsolutePath());
            Log.v("GetUpdateState", "...下载成功...");
            gLogger.info("...Download success...");
            // Intent intent = new Intent();
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setAction("android.intent.action.VIEW");
            // String type = "application/vnd.android.package-archive";
            // intent.setDataAndType(Uri.parse("file://" + cachePath), type);
            // context.startActivity(intent);
            Log.v("GetUpdateState", "...静默安装...");
            gLogger.info("...Silent installation...");

            // new Thread(sclient).start();
            // intasllApk(file);

            // int ret = PackageUtils.install(context, file.getPath());

            chmod(cachePath);

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            intent.putExtra("com.trigtop.INSTALL_SILENT", true);
            context.startActivity(intent);

            // Log.v("GetUpdateState", "...打开APP...");
            // gLogger.info("...Open APP...");
            // startApk();
            // startApk("com.example.dianwotv",
            // "com.example.dianwotv.SplashActivity");
        } else {
            Log.v("GetUpdateState", "...文件不存在...");
            gLogger.info("...File does not exist...");
        }

    }

    private void chmod(String path) {
        try {
            String command = "chmod 755 " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PackageInfo getPackageInfo() {
        try {
            PackageManager manager = context.getPackageManager();
            List pkgList = manager.getInstalledPackages(0);
            for (int i = 0; i < pkgList.size(); i++) {
                PackageInfo pI = (PackageInfo) pkgList.get(i);
                if (pI.packageName.equalsIgnoreCase("com.jsqix.gxt.tv"))
                    return pI;
            }
        } catch (Exception e) {
        }
        return null;
    }

    // private PackageInfo getApkInfo(Context context, String archiveFilePath) {
    // PackageManager pm = context.getPackageManager();
    // PackageInfo apkInfo = pm.getPackageArchiveInfo(archiveFilePath, 128);
    // return apkInfo;
    // }

    private boolean startApk() {
        // AlertUtil.close();
        boolean isSuccess = false;
        Intent intent = new Intent();
        ComponentName componetName = new ComponentName("com.jsqx.dianwotv",
                "com.jsqx.dianwotv.SplashActivity");
        intent.setComponent(componetName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (context
                .checkCallingOrSelfPermission("com.DownSDK.custempermission") == PackageManager.PERMISSION_GRANTED) {
            try {
                ((Activity) this.context).startActivity(intent);
                gLogger.info("The open succeeded");
                isSuccess = true;
            } catch (Exception e) {
                try {
                    ((Application) this.context).startActivity(intent);
                    isSuccess = true;
                    gLogger.info("The open succeeded");
                } catch (Exception e2) {
                    gLogger.info("Failed to open" + e2.getMessage());
                }
            }

        }
        return isSuccess;
    }

    public void ondestroy() {
        try {
            context.unregisterReceiver(this.receiver);
        } catch (Exception e) {
        }
    }

    private void intasllApk(File file) {
        ProcessBuilder pb = new ProcessBuilder("/system/bin/sh");
        // java.lang.ProcessBuilder: Creates operating system processes.
        pb.directory(new File("/"));// 设置shell的当前目录。
        try {
            Process proc = pb.start();
            // 获取输入流，可以通过它获取SHELL的输出。
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(
                    proc.getErrorStream()));
            // 获取输出流，可以通过它向SHELL发送命令。
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(proc.getOutputStream())), true);
            // out.println("pwd");
            // out.println("su root");//执行这一句时会弹出对话框（以下程序要求授予最高权限...），要求用户确认。
            // out.println("cd /data/ir-map");//这个目录在系统中要求有root权限才可以访问的。
            // out.println("ls -l");//这个命令如果能列出当前安装的APK的数据文件存放目录，就说明我们有了ROOT权限。
            out.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install pm install -r "
                    + file.getPath());
            out.println("exit");
            // proc.waitFor();
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line); // 打印输出结果
                gLogger.info("result: " + line);
            }
            while ((line = err.readLine()) != null) {
                System.out.println(line); // 打印错误输出结果
                gLogger.info("result: " + line);
            }
            in.close();
            out.close();
            proc.destroy();
        } catch (Exception e) {
            System.out.println("exception:" + e);
            gLogger.info("result: " + e.getMessage());
        }
    }

    public void slientInstallApk(File file) {
        String result = "";
        String[] args = {"LD_LIBRARY_PATH=/vendor/lib:/system/lib", "pm",
                "install", "-r", file.getPath()};
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            // baos.write('/n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
            Log.d("GetUpdateState", "Action result: " + result);
            gLogger.info("Action result: " + result);
            // text.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
            gLogger.info("Action result: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            gLogger.info("Action result: " + e.getMessage());
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

    }

    /**
     * 静默安装
     *
     * @param file
     * @return
     */
    public boolean slientInstall(File file) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            dataOutputStream = new DataOutputStream(out);
            // gLogger.info("--Mount system --");
            // dataOutputStream.writeBytes("busybox mount -o remount,rw /system"
            // + "\n");// mount -r -w -o remount -t ext4 /dev/block/sda6
            // // /system
            // gLogger.info("--Get Root --");
            // dataOutputStream.writeBytes("chmod 777 " + file.getPath() +
            // "\n");
            gLogger.info("--Root Install --");
            dataOutputStream
                    .writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
                            + file.getPath() + "\n");
            gLogger.info("--waite... --");
            // 提交命令
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
                Log.v("GetUpdateState", "安装成功");
                gLogger.info("Successfully installed");
            } else if (value == 1) { // 失败
                result = false;
                Log.v("GetUpdateState", "安装失败");
                gLogger.info("Installation failed");
            } else { // 未知情况
                result = false;
                Log.v("GetUpdateState", "安装未知情况");
                gLogger.info("installation of the unknown  " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("GetUpdateState", e.getMessage());
            gLogger.info(e.getMessage());
        } finally {
            try {
                if (out != null)
                    out.close();
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (process != null)
                    process.destroy();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void postCallback(int resultCode, String result) {
        BaseObj baseobj = new Gson().fromJson(result, BaseObj.class);
        if (baseobj != null) {
            if (baseobj.getCode().equals("000")) {
                mUpdate = new Gson().fromJson(result, SilentUpdateObj.class);
                if (!("V" + installedVersion)
                        .equalsIgnoreCase(mUpdate.getObj().getAppVersion())) {
                    Message msg = new Message();
                    msg.what = 0;
                    myHandler.sendMessage(msg);
                } else if (iposIsInstalled) {
                    Log.v("GetUpdateState", "..已安装最新版本...");
                    gLogger.info("..The latest version is already installed...");
                    // startApk();

                } else if (new File(cachePath).exists()) {
                    Message msg = new Message();
                    msg.what = 1;
                    myHandler.sendMessage(msg);
                }
            } else {
                Log.v("GetUpdateState", "..查询更新失败..." + baseobj.getMsg());
                gLogger.info("..Query update failed..." + baseobj.getMsg());
            }
        } else {
            Log.v("GetUpdateState", "..查询更新失败...连不上服务器");
            gLogger.info("..Query update failed...connect error");
        }
    }
}
