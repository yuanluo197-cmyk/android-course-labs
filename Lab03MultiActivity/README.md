# 实验三：多 Activity

学生：罗元 2024110827。应用标题为“罗元2024110827”。

## 项目内容

本项目包含 1 个主 Activity 和 4 个二级 Activity，使用 Kotlin 与 Android 原生 XML 布局，无第三方运行依赖。

| 页面 | 功能 | 核心文件 |
| --- | --- | --- |
| 主页面 | 四个入口，显示第二页返回值 | `MainActivity.kt`、`activity_main.xml` |
| 接收参数 | 读取并显示主页面传入的参数 | `ForwardActivity.kt`、`activity_forward.xml` |
| 返回结果 | 输入内容，使用 `setResult` 返回主页面 | `ReturnActivity.kt`、`activity_return.xml` |
| 相机拍照 | 通过 `ACTION_IMAGE_CAPTURE` 隐式调用系统相机，并显示返回照片 | `CameraActivity.kt`、`activity_camera.xml` |
| 秒表 | 开始、暂停、清零；横竖屏切换后保持计时 | `StopwatchActivity.kt`、`activity_stopwatch.xml` |

全部可见界面文字位于 `app/src/main/res/values/strings.xml`。计时使用 `SystemClock.elapsedRealtime()`，旋转时在 `onSaveInstanceState` 保存计时状态。相机返回的照片保存在应用私有目录，旋转后重新读取。

## 打开与运行

1. 在 Android Studio 选择 **Open**，选中整个 `Lab03MultiActivity` 文件夹。
2. 等待 Gradle 同步，选择已有模拟器，运行 `app`。
3. 按下方清单检查每个功能，并为实验报告截取实际运行画面。

## 检查清单

1. 点击“正向传参”，新页面显示主页面发来的文字。
2. 点击“返回结果”，输入文字并确定，主页面显示返回内容。
3. 点击“隐式调用相机”，在系统相机拍照确认后，第三页显示照片；旋转手机，照片仍显示。
4. 点击“秒表”，开始后旋转手机，计时不归零；暂停后数字不再变化；清零后显示 `00:00.0`。

附件 PDF 中的 `android:name="BLAH"` 是占位文本，不能直接放入清单。本项目只是委托系统相机拍照并读取返回的预览图，因此没有声明应用自身的相机硬件访问权限。

## 验证记录

- 已用 Kotlin 编译器和 Android SDK API 37 的 `android.jar` 编译全部 Kotlin 源文件；资源已通过 `aapt2 compile`。
- 本次沙箱无法连接 Gradle 本机守护进程，因此尚未完成 `:app:assembleDebug` 和模拟器实测。请在 Android Studio 中运行并补充真实截图后提交实验报告。

提交平台按本次对话中确认的 GitHub；实验文档中的 Gitee 地址栏可以填老师接受的 GitHub 项目地址。
