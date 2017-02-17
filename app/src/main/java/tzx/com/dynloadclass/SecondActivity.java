package tzx.com.dynloadclass;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月17日 11:14
 * Description:
 */

public class SecondActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            test();
            Test test = new Test();
            //Class test = Class.forName(DexUtils.mClassName);
            //Object obj = test.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() throws Exception{
        BaseDexClassLoader baseDexClassLoader = (BaseDexClassLoader) this.getClassLoader();
        ClassLoader baseSourceDexClassLocaderParent = baseDexClassLoader.getParent();
        DexClassLoader classLoader = DexUtils.getCustomerDexClassLoader(this, baseSourceDexClassLocaderParent);


        Field classLoaderField = baseDexClassLoader.getClass().getSuperclass().getSuperclass().getDeclaredField("parent");
        classLoaderField.setAccessible(true);
        classLoaderField.set(baseDexClassLoader, classLoader);
    }
}
