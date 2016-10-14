package com.jsqix.gxt.tv.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.jsqix.gxt.tv.view.MyDialog;

public class AlertUtil
{
  public static ProgressDialog pdialog;
  public static AlertDialog aDialog;

  @SuppressWarnings("deprecation")
public static void alert(Context context, String titleMsg, String msg, String butStr)
  {
    aDialog = new MyDialog(context);
    aDialog.setTitle(titleMsg);
    aDialog.setMessage(msg);
    aDialog.setInverseBackgroundForced(true);
    aDialog.setButton(butStr, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
    aDialog.show();
  }

  public static ProgressDialog getPdl(Context context, String titleMsg, String msg)
  {
    pdialog = new ProgressDialog(context);
    pdialog.setTitle(titleMsg);
    pdialog.setCancelable(true);
    pdialog.setMessage(msg);
    pdialog.setProgressStyle(0);
    pdialog.show();
    return pdialog;
  }

  public static void close()
  {
    if (pdialog != null) {
      pdialog.dismiss();
      pdialog = null;
    }
    if (aDialog != null) {
      aDialog.dismiss();
      aDialog = null;
    }
  }
}