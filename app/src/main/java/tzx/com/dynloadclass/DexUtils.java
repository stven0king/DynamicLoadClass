package tzx.com.dynloadclass;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月17日 11:21
 * Description:
 */

public class DexUtils {

    public static final String mClassName = "tzx.com.dynloadclass.Test";//和导出之前的包名和类名保持一致

    public static String getDynamicDexPath() {
        return android.os.Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Test.dex";// 前半部分为获得SD卡的目录
    }

    public static DexClassLoader getCustomerDexClassLoader(Context context, ClassLoader loader) {
        String mDexPath = getDynamicDexPath();
        File dexOutputDir = context.getDir("dex", 0);//
        File file = new File(mDexPath);
        DexClassLoader classLoader = new DexClassLoader(file.getAbsolutePath(),
                dexOutputDir.getAbsolutePath(), null, loader);
        return classLoader;
    }

    public static DexClassLoader getCustomerDexClassLoader(Context context) {
        return getCustomerDexClassLoader(context, context.getClassLoader());
    }

    public static Object combineArray(Object firstArray, Object secondArray) {
        Class<?> localClass = firstArray.getClass().getComponentType();
        int firstArrayLength = Array.getLength(firstArray);
        int allLength = firstArrayLength + Array.getLength(secondArray);
        Object result = Array.newInstance(localClass, allLength);
        for (int k = 0; k < allLength; ++k) {
            if (k < firstArrayLength) {
                Array.set(result, k, Array.get(firstArray, k));
            } else {
                Array.set(result, k, Array.get(secondArray, k - firstArrayLength));
            }
        }
        return result;
    }
}
