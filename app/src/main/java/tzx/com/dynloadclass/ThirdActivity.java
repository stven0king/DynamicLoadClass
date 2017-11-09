package tzx.com.dynloadclass;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * Created by tanzhenxing
 * Date: 2017年02月17日 11:50
 * Description:自定义类加载器生产DexClassLoader对象获取其中DexPathList成员变量的Element[],将其添加到默认的类
 *            加载器PathClassLoader中的DexPathlist成员变量的Element[]的前边，在findclass的时候先找到的是
 *           自定义类加载器中的类，从而达到动态替换。
 */

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(ThirdActivity.this.getClass().getSimpleName());
    }

    protected void loadClass() {

        try {
            //baseDexClassLoader  instanceof PathClassLoader
            BaseDexClassLoader baseDexClassLoader = (BaseDexClassLoader) this.getClassLoader();
            //BaseDexClassLoader  DexPathList:pathList
            Field baseDexClassLoaderField = baseDexClassLoader.getClass().getSuperclass().getDeclaredField("pathList");
            baseDexClassLoaderField.setAccessible(true);
            //DexPathList:pathList
            Object mPathList = baseDexClassLoaderField.get(baseDexClassLoader);
            //DexPathList    Element[]:dexElements
            Field pathListField = mPathList.getClass().getDeclaredField("dexElements");
            pathListField.setAccessible(true);
            //Element[]:dexElements
            Object dexElements = pathListField.get(mPathList);

            //CustomerDexClassLoader
            DexClassLoader dexClassLoader = DexUtils.getCustomerDexClassLoader(this, this.getClassLoader());
            Field dexClassLoaderField = dexClassLoader.getClass().getSuperclass().getDeclaredField("pathList");
            dexClassLoaderField.setAccessible(true);
            Object mPathList_ = dexClassLoaderField.get(dexClassLoader);
            Field pathListField_ = mPathList_.getClass().getDeclaredField("dexElements");
            pathListField_.setAccessible(true);
            //CustomerDexClassLoader    Element[]:dexElements
            Object dexElements_ = pathListField_.get(mPathList_);
            //combine  CustomerDexClassLoader's dexElements and PathClassLoader's  dexElements
            Object allElements = DexUtils.combineArray(dexElements_, dexElements);
            pathListField.setAccessible(true);
            pathListField.set(mPathList, allElements);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void show() {
        Test test = new Test(mContext);
        //Class loadClass = null;
        //try {
        //    loadClass = Class.forName(DexUtils.mClassName);
        //    Constructor<?> constructor = loadClass.getConstructor(new Class[] {Context.class});//得到构造器
        //    Object object = constructor.newInstance(new Object[] {mContext});//实例化
        //} catch (ClassNotFoundException e) {
        //    e.printStackTrace();
        //} catch (IllegalAccessException e) {
        //    e.printStackTrace();
        //} catch (InstantiationException e) {
        //    e.printStackTrace();
        //} catch (NoSuchMethodException e) {
        //    e.printStackTrace();
        //} catch (InvocationTargetException e) {
        //    e.printStackTrace();
        //}
    }
}
