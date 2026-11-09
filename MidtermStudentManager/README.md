# 上机测试 学生信息管理 APP

罗元 · 2024110827 · 2024级2班

包名：`com.luoyuan.studentmanager`，包含本人姓名拼音。应用标题：罗元2024110827。学号末位7，应用文字统一使用棕色 `#795548`。

## 打开运行

Android Studio 选择 Open，打开整个 `MidtermStudentManager` 文件夹，等待同步完成，选择 `app` 和已启动的模拟器，点击绿色运行按钮。

构建配置：Android SDK 37、Gradle 9.6.0、AGP 9.4.0；使用 Android Studio 自带 JDK。最低 Android 6.0。实际验证设备为 Android 15 / API 35 模拟器。项目不依赖额外的运行时 UI 库。

```powershell
.gradlew.bat :app:assembleDebug :app:testDebugUnitTest
.gradlew.bat :app:connectedDebugAndroidTest
```

## 演示顺序

1. 首页显示罗元的已有学生记录，以及“共1位学生”。
2. 点击“添加学生”，填写一个新学号、姓名、性别、班级，点击“确定”。
3. 返回首页后显示新增记录，学生数量变成2。
4. 再点“添加学生”，填写内容后点“取消”，列表和数量不变。
5. 留空学号或重复填写已有学号，点击确定，页面显示错误提示而不返回。

新增使用 Intent 传入已有学号列表，确认后用 `setResult(RESULT_OK, data)` 返回 Student 对象。首页在 `onActivityResult` 校验请求码和结果码，添加数据并调用适配器刷新列表。取消或系统返回键不添加数据。

## 主要文件

| 作用 | 位置 |
| --- | --- |
| 学生数据类和校验 | app/src/main/java/com/luoyuan/studentmanager/Student.kt |
| 列表和返回结果处理 | app/src/main/java/com/luoyuan/studentmanager/MainActivity.kt |
| 新增表单、确定与取消 | app/src/main/java/com/luoyuan/studentmanager/AddStudentActivity.kt |
| 列表适配器 | app/src/main/java/com/luoyuan/studentmanager/StudentAdapter.kt |
| 页面、列表项布局 | app/src/main/res/layout/ |
| 棕色与文字资源 | app/src/main/res/values/ |
| 单元测试 | app/src/test/java/com/luoyuan/studentmanager/StudentTest.kt |
| 界面测试 | app/src/androidTest/java/com/luoyuan/studentmanager/StudentUiTest.kt |

## 本次验证记录

2026年11月9日：debug APK 构建成功；8项单元测试通过，5项模拟器界面测试通过。界面测试覆盖新增返回并刷新、取消不添加、空学号和重复学号校验、页面重建恢复，以及标题和文字颜色。

实际运行截图在 `docs/screenshots`，测试原始 XML 在 `docs/tests`。截图中的“测试学生”为测试输入。报告在 `docs` 目录。没有使用生成的界面图片。

学生列表保存在内存，Activity 重建时通过 Bundle 恢复；关闭应用进程后恢复初始记录，没有实现数据库或文件持久化。

Git 提交记录保留了新建项目和完成实现的实际过程。课程仓库地址：

https://github.com/yuanluo197-cmyk/android-course-labs/tree/main/MidtermStudentManager
