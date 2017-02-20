package tzx.com.dynloadclass;

import android.content.Context;

import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月20日 10:02
 * Description:自定义ClassLoader加载需要的类
 */

public class FirstActivity extends BaseActivity {
    ClassLoader classLoader;
    @Override
    protected void onStart() {
        super.onStart();
        setTitle(FirstActivity.this.getClass().getSimpleName());
    }

    protected void loadClass() {
        classLoader = DexUtils.getCustomerDexClassLoader(this, this.getClassLoader().getParent());
    }

    @Override
    protected void show() {
        if (classLoader == null) classLoader = this.getClassLoader();
        try {
            Class<?> clazz = classLoader.loadClass(DexUtils.mClassName);
            Constructor<?> constructor = clazz.getConstructor(new Class[] {Context.class});//得到构造器
            Object object = constructor.newInstance(new Object[] {mContext});//实例化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
