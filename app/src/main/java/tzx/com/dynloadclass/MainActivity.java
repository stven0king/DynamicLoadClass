package tzx.com.dynloadclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test() {
        DexClassLoader classLoader = DexUtils.getCustomerDexClassLoader(this);
        try {
            Class<?> clazz = classLoader.loadClass(DexUtils.mClassName);
            Constructor<?> constructor = clazz.getConstructor(new Class[] {});//得到构造器
            Object object = constructor.newInstance();//实例化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
