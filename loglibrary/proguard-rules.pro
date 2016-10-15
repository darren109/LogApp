# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Program\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
 #指定代码的压缩级别
       -optimizationpasses 5
        #包明不混合大小写
        -dontusemixedcaseclassnames
       #不去忽略非公共的库类
       -dontskipnonpubliclibraryclasses

        #优化  不优化输入的类文件
       -dontoptimize

        #预校验
       -dontpreverify

        #混淆时是否记录日志
       -verbose

        # 混淆时所采用的算法
       -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

       #保护注解
       -keepattributes *Annotation*

       # 保持哪些类不被混淆
       -keep public class * extends android.app.Fragment
       -keep public class * extends android.app.Activity
       -keep public class * extends android.app.Application
       -keep public class * extends android.app.Service
       -keep public class * extends android.content.BroadcastReceiver
       -keep public class * extends android.content.ContentProvider
       -keep public class * extends android.app.backup.BackupAgentHelper
       -keep public class * extends android.preference.Preference
       -keep public class com.android.vending.licensing.ILicensingService
       #如果有引用v4包可以添加下面这行
       -keep public class * extends android.support.v4.app.Fragment
       #忽略警告
       -ignorewarning
        #保持自定义控件类不被混淆
       -keepclasseswithmembers class * {
            public <init>(android.content.Context, android.util.AttributeSet);
       }

       #保持自定义控件类不被混淆
       -keepclassmembers class * extends android.app.Activity {
            public void *(android.view.View);
       }

       # 保持 native 方法不被混淆
       -keepclasseswithmembernames class * {
           native <methods>;
       }

       -keepclasseswithmembernames class * {
           public <init>(android.content.Context, android.util.AttributeSet);
       }

       -keepclasseswithmembernames class * {
           public <init>(android.content.Context, android.util.AttributeSet, int);
       }

       # 保持枚举 enum 类不被混淆
       -keepclassmembers enum * {
           public static **[] values();
           public static ** valueOf(java.lang.String);
       }

       #保持 Serializable 不被混淆
       -keepnames class * implements java.io.Serializable

       #保持 Serializable 不被混淆并且enum 类也不被混淆
       -keepclassmembers class * implements java.io.Serializable {
               static final long serialVersionUID;
               private static final java.io.ObjectStreamField[] serialPersistentFields;
               !static !transient <fields>;
               !private <fields>;
               !private <methods>;
               private void writeObject(java.io.ObjectOutputStream);
               private void readObject(java.io.ObjectInputStream);
               java.lang.Object writeReplace();
               java.lang.Object readResolve();
       }

       #保留整个包
#       -keep class com.chinazyjr.financial.view.** {
#               *;
#       }

        #不混淆资源类
       -keepclassmembers class **.R$* {
               public static <fields>;
       }

       #如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
           #gson
           #-libraryjars libs/gson-2.2.4.jar
#           -keepattributes Signature
           # Gson specific classes
#           -keep class sun.misc.Unsafe { *; }
           # Application classes that will be serialized/deserialized over Gson
#           -keep class com.google.gson.examples.android.model.** { *; }



        -libraryjars libs/dom4j-2.0.0-RC1.jar
        #-libraryjars libs/umeng-analytics-v5.6.1.jar
        #-libraryjars libs/umeng-onlineconfig_v1.0.0.jar
        #-libraryjars libs/umeng-update-v2.6.0.1.jar
        #-libraryjars libs/xUtils-2.6.14.jar
        #-libraryjars libs/zxing.jar
