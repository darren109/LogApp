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

###ToolLog.d()
###ToolLog.d(String)
###ToolLog.d(Tag,String)
###ToolLog.json(String)
###ToolLog.j(Tag,String)
###ToolLog.f()
```
 ToolLog.f(TAG, Environment.getExternalStorageDirectory(), "test.txt", JSON_LONG);
```
###ToolLog.x()
```
 ToolLog.x(XML);
```
##Eclipse

You need add Toollog.jar into your project.

##Notice

If you don't need the method -- ToolLog.x() ，you can delete the method about it ,so that you can decrease the almost size of this library.