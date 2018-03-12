package com.example.sun.myshake;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by sun on 18/1/4.
 */

public class ScreenShotUtils {
    private static final String TAG = "sunwillfly Screen";

    // 1、截Activity界面（包含空白的状态栏）

    /**
     * 根据指定的Activity截图（带空白的状态栏）
     *
     * @param context 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivity(Activity context) {
        View view = context.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bitmap;
    }

    // 2、截Activity界面（去除状态栏）

    /**
     * 根据指定的Activity截图（去除状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public Bitmap shotActivityNoStatusBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        // 获取屏幕宽和高
        int widths = dm.widthPixels;
        int heights = dm.heightPixels;


        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }


    private static final String SCREEN_SHOT_DIR = "MyShake_ScreenShot";
    private static String FILE_NAME_PRE = "ScreenShot";

//    /**
//     * @param bitmap     :当前屏幕的位图数据
//     * @param fileDirStr :截图保存文件位置
//     */
//    private static void saveScreenShot(Bitmap bitmap, String fileDirStr)
//            throws Exception {
//        FileOutputStream fos = null;
//        String filePath = generateFilePath(fileDirStr) + "/" + generateFileName() + ".png";
//        Log.i(TAG, "filePath = " + filePath);
//        fos = new FileOutputStream(filePath);
//        if (null != fos) {
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
//            try {
//                fos.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                fos.close();
//            }
//            Log.i(TAG, "screen shot complete");
//        }
//    }

    // 生成文件夹路径
    private static String generateFilePath(String fileDirStr) throws FileNotFoundException {
        File file = null;
        String filePath = fileDirStr;
        if (fileDirStr == null || fileDirStr.trim().length() == 0) {
            filePath = Environment.getExternalStorageDirectory() + "/"
                    + SCREEN_SHOT_DIR;
            file = new File(filePath);
        } else {
            file = new File(fileDirStr);
        }
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.e(TAG, "create folder failed ");
                throw new FileNotFoundException("can't create screenShot folder");
            } else {
                Log.i(TAG, "create folder successful");
            }
        }
        Log.d(TAG, "generateFilePath = " + filePath);
        return filePath;
    }

    //根据当前时间生成文件名
    private static String generateFileName() {
        String filename = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        filename = FILE_NAME_PRE + year  + month + day  + hour  + minutes  + second + millisecond;
        Log.d(TAG, "generateFileName = " + filename);
        return filename;
    }


    public static String getScreenShotPath(Activity context) {
        String filePath = null;
        try {
            Bitmap bitmap = shotActivity(context);

            FileOutputStream fos = null;
            filePath = generateFilePath("") + "/" + generateFileName() + ".png";
            Log.i(TAG, "getScreenShotPath filePath = " + filePath);
            fos = new FileOutputStream(filePath);
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                try {
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    fos.close();
                }
                Log.i(TAG, "screen shot complete");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return filePath;
        }

    }



}
