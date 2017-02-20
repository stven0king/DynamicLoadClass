package tzx.com.dynloadclass;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月17日 11:14
 * Description:在Android原来的PathClassLoader类加载器与其parent类加载器之间插入一个自定义类加载器，利用
 *            双亲委派机制在加载类时先在自定义加载器中寻找。
 */

public class SecondActivity  extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(SecondActivity.this.getClass().getSimpleName());
    }

    protected void loadClass() {
        BaseDexClassLoader baseDexClassLoader = (BaseDexClassLoader) this.getClassLoader();
        ClassLoader baseSourceDexClassLocaderParent = baseDexClassLoader.getParent();
        DexClassLoader classLoader = DexUtils.getCustomerDexClassLoader(this, baseSourceDexClassLocaderParent);

        try {
            Field classLoaderField = baseDexClassLoader.getClass().getSuperclass().getSuperclass().getDeclaredField("parent");
            classLoaderField.setAccessible(true);
            classLoaderField.set(baseDexClassLoader, classLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void show() {
        Test test = new Test(mContext);
    }
}
