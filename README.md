# DynamicLoadClass


使用三种不同的方式在Android中加载自己dex中的类

项目中使用的Test.dex，是在项目开始的时候先写的Test.java然后打包apk，再从apk中解压出来的class.dex文件重命名为Test.dex。

将Test.java文件修改后打包的apk就与Test.dex中的Test类只有实现不同，其它完全一样，我们在程序运行时将apk的Test类替换为Test.dex中的Test，这样就实现了类的替换。

>- 自定义ClassLoader加载需要的类
>- 在Android原来的PathClassLoader类加载器与其parent类加载器之间插入一个自定义类加载器，利用双亲委派机制在加载类时先在自定义加载器中寻找。
>- 自定义类加载器生产DexClassLoader对象获取其中DexPathList成员变量的Element[],将其添加到默认的类加载器PathClassLoader中的DexPathlist成员变量的Element[]的前边，在findclass的时候先找到的是自定义类加载器中的类，从而达到动态替换。


##运行代码注意
程序运行版本5.0以上（PS：Android5.0一下版本会有65535的问题）Test.dex文件，我测试的时候是放在sdcard的根目录下


因为没多对多dex文件做处理，所以Demo生成的唯一的dex会被打上CLASS_ISPREVERIFIED标识。
程序在5.0以下的Android系统中运行会报错：

```java
java.lang.IllegalAccessError: Class ref in pre-verified class resolved to unexpected implementation
```
当然使用反射除外，反射调用其他dex中的类的方法时是不校验CLASS_ISPREVERIFIED的。
