# LogApp
关于Android日志处理

This is a useful log tool for Android 

Android LogCat 工具类，目前功能：

- 无参数打印
- 打印所在行号
- 打印所在函数
- AS点击方法名自动跳转
- Json格式自动解析打印
- xml格式自定解析打印
- Log信息存储到文件(6.0以上需要动态申请存储空间权限)
- 变长参数打印
- 无logcat最多4000字符打印限制

---

##Features

- Can use ToolLog.d() with no tag
- Print log info with line number and method name
- Jump to the position where the log is invoked, by click in the Android Studio Logcat

##Update

- Add support for Long JSON String
- ToolLog.file() could save the log to file
- Add support for xml format string
- Add support for long string

##Sample Usage

![](https://github.com/ZhaoKaiQiang/KLog/blob/master/image/demo.gif)

###ToolLog.d()

Use this method , you can get a log like follwing，default tag is current class name

![](http://i13.tietuku.com/6eac96ebfbea82cb.png)

###ToolLog.d(String)

you can print a String with this method ,default tag is current class name
![](http://i13.tietuku.com/95871d0388a27777.png)

###ToolLog.d(Tag,String)

you can set tag by youself , the result is as bollow 
![](http://i13.tietuku.com/f0286fa45f975346.png)

###ToolLog.json(String)

you can print a String with json format ,and it will be formated as following
![](http://i13.tietuku.com/767fa81ad27c8f9f.png)

###ToolLog.json(Tag,String)

as same as above , but with a tag whitch are set by yourself
![](http://i13.tietuku.com/bc9714547a4f50fe.png)

###ToolLog.file()

you can save the log string in to file !

```
 ToolLog.file(TAG, Environment.getExternalStorageDirectory(), "test.txt", JSON_LONG);
```

![](https://github.com/ZhaoKaiQiang/KLog/blob/master/image/file.png)

###ToolLog.xml()

print xml format string in logcat

```
 ToolLog.xml(XML);
```

![](https://github.com/ZhaoKaiQiang/KLog/blob/master/image/xml.png)

##Eclipse

You need add Toollog.jar into your project ,because the Toollog dependences on dom4j，so you alse need add the dom4j-2.0.0-RC1.jar to your project . The jar file is in the library's libs .

##Notice

If you don't need the method -- ToolLog.xml() ，you can delete the method about it ,so that you can decrease the almost size of this library.

##License

```
Copyright 2015, 2016 ZhaoKaiQiang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
##Applocation初始化配置：
	//日志处理
        ToolLog.init(true);
        CrashHandler.getInstance().init(getApplicationContext());
        // 收集设备参数信息
        FileLogUtils.initData(getApplicationContext(), "",true);
        LogReader.startCatchLog("*");


##日志基础权限：
    <!-- 读取日志权限 -->
    <uses-permission android:name="android.permission.READ_LOGS" />
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <!-- 往sdcard中写入数据的权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <!-- 在sdcard中创建/删除文件的权限 -->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />




##工具类说明： 个别需要对应权限
- Such as Log, Averager, Base64, Check, FlashLight, KeyguardLock, LogReader, Network, SilentInstaller, TimeAverager, TimeCounter, Toastor, WakeLock, ScreenReceiver, SmsReceiver, PhoneReceiver, NotificationService, AndroidUtil, AppUtil, BitmapUtil, ByteUtil, ClassUtil, DialogUtil, FieldUtil, FileUtil, HexUtil, MD5Util, NotificationUtil, NumberUtil, PackageUtil, RandomUtil, ShellUtil, TelephoneUtil, VibrateUtil, IOUtils, FileUtils, AsyncExecutor, etc.
 
 
##LiteCommon是一系列通用类、辅助类、工具类的集合，有以下特点：
- **1. 通用性强**：只有常用、通用才集入。
- **2. 体积超小**：不到50K！加入增强IO包混淆后70K！
- **3. 纯  纯  纯**：类间独立，单挑大梁，极少耦合，就是单纯！
- 其中包括bitmap处理，文件操作，加密存储器，shell命令，静默安装，计数器，均值器，吐司，日志，校验，提示，网络监测等基础功能，以及一些Base64、MD5、Hex、Byte、Number、Dialog、Filed、Class、Package、Telephone、Random等工具类。

1. async包：异步与并发
-----
- **AsyncExecutor**：   一个简单的可以自定义线程池并发执行器
2. log包：日志
-----
- **Log**：             一个和android系统日志类同名(方便快速替换)的Log工具类，不同的是这个Log具有一键开关功能，方便快速开发打开调试模式。
3. assit包：辅助
-----
- **Averager**：        均值器， 添加一些列数字或时间戳，获取其均值。
- **Base64**：          Base64， 兼容到android1.0版本的Base64编解码器。
- **Check**：           检测类， 检测各种对象是否为null或empty。
- **FlashLight**：      闪光灯， 开启、关闭闪光灯。
- **KeyguardLock**：    锁屏管理， 锁屏、禁用锁屏，判断是否锁屏
- **LogReader**：       日志捕获器， 将某个程序、级别的日志记录到sd卡中，方便远程调试。
- **Network**：         网络探测器， 判断网络是否打开、连接、可用，以及当前网络状态。
- **SilentInstaller**： 安装器， 静默安装、卸载（仅在root过的手机上）。
- **TimeAverager**：    计时均值器， 统计耗时的同时，多次调用可得出其花费时间均值。
- **TimeCounter**：     计时器， 顾名思义，统计耗时用的。
- **Toastor**：         吐司， 解决多次连续弹出提示问题，可只弹出最后一次，也可连续弹出轻量级提示。
- **WakeLock**：        屏幕管理， 点亮、关闭屏幕，判断屏幕是否点亮
4. data包：数据处理
-----
- **DataKeeper**：       加密存储器，持久化工具，可加密，更简单、安全的存储（持久化）、获取数字、布尔值、甚至对象。
- **chipher包**：        放置加解密辅助类。
5. io包：文件与IO
-----
- **Charsets**：         字节编码类
- **FilenameUtils**：    通用的文件名字、路径操作工具类
- **FileUtils**：        通用文件操作工具类
- **IOUtils**：          通用IO流操作工具类
- **StringCodingUtils**：字符串编码工具类
- **stream包**：         IO流操作辅助类
- 
6. receiver包：通用广播接收器
-----
- **ScreenReceiver**：  屏幕接收器，可收到屏幕点亮、关闭的广播，并通过回调通知给调用者
- **PhoneReceiver**：      电话监听，来电、去电、通话、挂断的监听以及来去电话号码的获取。
- **SmsReceiver**：        短信接收器，可获取短信内容，发送者号码，短信中心号码等。


7. utils包：常用工具类
-----
- **AndroidUtil**：     android信息， 获取android手机品牌、商家、版本号等信息
- **AppUtil**：         app工具， 检测是否前台运行
- **BitmapUtil**：      位图操作， 拍照，裁剪，圆角，byte、string互转，压缩，放缩，保存等
- **ByteUtil**：        byte工具类
- **ClassUtil**：       类工具， 新建实例，判断类的类型等
- **DialogUtil**：      对话框工具类， 统一全局对话框
- **FieldUtil**：       属性工具类，获取属性值、获取属性泛型类型等
- **FileUtil**：        文件工具类
- **HexUtil**：         16进制工具类，16进制和byte、char像话转化
- **MD5Util**：         MD5工具类
- **NotificationUtil**：通知工具类，便捷显示到顶部栏
- **NumberUtil**：      数字工具类，各种数字安全转化
- **PackageUtil**：     应用程序类，打开、安装，卸载，启动应用以及获取应用信息
- **RandomUtil**：      随机工具类，产生随机string或数字，随机洗牌等
- **ShellUtil**：       shell 命令工具类
- **TelephoneUtil**：   电话工具类，手机号、运营商、IMEI、IMSI等信息
- **VibrateUtil**：     震动工具类，调用系统震动功能

8. service包：通用服务
- **NotificationService**：通知监听，各类通知服务的监听，获取通知的简述、标题、内容等信息，可以获取诸如QQ、微信、淘宝、浏览器等所有的在通知栏提示的消息。
