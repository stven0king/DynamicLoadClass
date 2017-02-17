package tzx.com.dynloadclass;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月17日 11:50
 * Description:
 */

public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            test();
            Test test1 = new Test();
            //Class test = Class.forName(DexUtils.mClassName);
            //Object obj = test.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() throws NoSuchFieldException, IllegalAccessException {

        BaseDexClassLoader baseDexClassLoader = (BaseDexClassLoader) this.getClassLoader();
        Field baseDexClassLoaderField = baseDexClassLoader.getClass().getSuperclass().getDeclaredField("pathList");
        baseDexClassLoaderField.setAccessible(true);
        Object mPathList = baseDexClassLoaderField.get(baseDexClassLoader);
        Field pathListField = mPathList.getClass().getDeclaredField("dexElements");
        pathListField.setAccessible(true);
        Object dexElements = pathListField.get(mPathList);

        DexClassLoader dexClassLoader = DexUtils.getCustomerDexClassLoader(this, this.getClassLoader());
        Field dexClassLoaderField = dexClassLoader.getClass().getSuperclass().getDeclaredField("pathList");
        dexClassLoaderField.setAccessible(true);
        Object mPathList_ = dexClassLoaderField.get(dexClassLoader);
        Field pathListField_ = mPathList_.getClass().getDeclaredField("dexElements");
        pathListField_.setAccessible(true);
        Object dexElements_ = pathListField_.get(mPathList_);

        Object allElements = DexUtils.combineArray(dexElements_, dexElements);
        pathListField.setAccessible(true);
        pathListField.set(mPathList, allElements);

    }
}
