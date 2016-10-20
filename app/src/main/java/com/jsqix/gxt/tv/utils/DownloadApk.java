package com.jsqix.gxt.tv.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jsqix.gxt.tv.base.AppContext;
import com.jsqix.gxt.tv.obj.GetUpdateState;
import com.jsqix.gxt.tv.R;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class DownloadApk {

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static DownloadApk updateManager;

    private Context mContext;
    // 通知对话框
    private Dialog noticeDialog;
    // 下载对话框
    private Dialog downloadDialog;
    // '已经是最新' 或者 '无法获取最新版本' 的对话框
    private Dialog latestOrFailDialog;
    // 进度条
    private ProgressBar mProgress;
    // 显示下载数值
    private TextView mProgressText;
    // 查询动画
    private ProgressDialog mProDialog;
    // 进度值
    private int progress;
    // 下载线程
    private Thread downLoadThread;
    // 终止标记
    private boolean interceptFlag;
    // 提示语
    private String updateMsg = "";
    // 返回的安装包url
    private String apkUrl = "";
    // 下载包保存路径
    private String savePath = "";
    // apk保存完整路径
    private String apkFilePath = "";
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    private String apkFileSize;
    // 已下载文件大小
    private String tmpFileSize;

    private String curVersionName = "";
    private int curVersionCode;
    private GetUpdateState mUpdate;
    private String apkName = "";

    public DownloadApk(Context context) {
        mContext = context;
    }

    public DownloadApk() {
        super();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mProgressText.setText(tmpFileSize + "/" + apkFileSize);
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final File file = new File(savePath, apkName);
                            Intent intent = new Intent();
                            intent.addFlags(268435456);
                            intent.setAction("android.intent.action.VIEW");
                            String type = "application/vnd.android.package-archive";
                            intent.setDataAndType(Uri.fromFile(file), type);
                            mContext.startActivity(intent);
                        }
                    }).start();

                    break;
                case DOWN_NOSDCARD:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", 3000).show();
                    break;
            }
        }

        ;
    };

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
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream
                    .writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
                            + file.getPath());
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean install() {
        File file = new File(savePath, apkName);
        String[] args = {"pm", "install", "-r", Uri.fromFile(file).getPath()};
        String result = null;
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
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        if (result != null
                && (result.endsWith("Success") || result.endsWith("Success\n"))) {
            Toast.makeText(mContext, "安装成功", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(mContext, "安装失败", Toast.LENGTH_LONG).show();
        return false;
    }

    public static DownloadApk getUpdateManager() {
        if (updateManager == null) {
            updateManager = new DownloadApk();
        }
        updateManager.interceptFlag = false;
        return updateManager;
    }

    /**
     * 获取当前客户端版本信息
     */
    public String getCurrentVersion() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            curVersionName = info.versionName;
            curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return curVersionName;
    }

    /**
     * 显示版本更新通知对话框
     */
    public void showNoticeDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog(mUpdate);
            }
        });
        builder.setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示下载对话框
     */
    public void showDownloadDialog(GetUpdateState mGetUpdateState) {
        mUpdate = mGetUpdateState;
        Builder builder = new Builder(mContext);
        builder.setTitle("正在下载新版本");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

        builder.setView(v);
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
                AppContext.getInstance().AppExit(mContext);
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                interceptFlag = true;
                AppContext.getInstance().AppExit(mContext);
            }
        });
        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                apkName = "gxt" + mUpdate.getAppVersion() + ".apk";
                String tmpApk = "gxt" + ".tmp";
                // 判断是否挂载了SD卡
                String storageState = Environment.getExternalStorageState();
                if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                    savePath = Environment.getExternalStorageDirectory() + File.separator +
                            Constant.SD_DIRECTORY + File.separator + "update" + File.separator;

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    apkFilePath = savePath + apkName;
                    tmpFilePath = savePath + tmpApk;
                }

                // 没有挂载SD卡，无法下载文件
                if (apkFilePath == null || apkFilePath == "") {
                    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                    return;
                }

                File ApkFile = new File(apkFilePath);
                // 是否已下载更新文件
                if (ApkFile.exists()) {
                    PackageInfo packageArchiveInfo = mContext
                            .getPackageManager().getPackageArchiveInfo(
                                    apkFilePath, PackageManager.GET_ACTIVITIES);
                    if (packageArchiveInfo == null
                            || !mUpdate.getAppVersion().equalsIgnoreCase(
                            "v" + packageArchiveInfo.versionName)) {
                        ApkFile.delete();
                    } else {
                        downloadDialog.dismiss();
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        return;
                    }
                }

                // 输出临时下载文件
                File tmpFile = new File(tmpFilePath);
                if (tmpFile.exists())
                    tmpFile.delete();
//				long tmpSize=tmpFile.length();
//				RandomAccessFile out = new RandomAccessFile(tmpFile,"rw"); 
//				out.seek(tmpSize);
                FileOutputStream fos = new FileOutputStream(tmpFile);
                apkUrl = mUpdate.getApp_url();
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
//				conn.setAllowUserInteraction(true);
//				conn.setRequestProperty("range","bytes="+tmpSize+"-"); 
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                // 显示文件大小格式：2个小数点显示
                DecimalFormat df = new DecimalFormat("0.00");
                // 进度条下面显示的总文件大小
                apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 进度条下面显示的当前下载文件大小
                    tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                    // 当前进度值
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成 - 将临时下载文件转成APK文件
                        if (tmpFile.renameTo(ApkFile)) {
                            // 通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                        }
                        break;
                    }
                    fos.write(buf, 0, numread);
//					out.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载

                fos.close();
//				out.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     *
     * @param url
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

}
