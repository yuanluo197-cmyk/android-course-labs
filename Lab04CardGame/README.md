# 实验四 翻牌游戏应用

罗元 · 2024110827

使用 Kotlin、XML 布局和 ViewBinding 实现三个 Activity，模型根据课程附件 model.zip 改写。界面文字集中在 strings.xml。

## 打开和运行

1. 在 Android Studio 选择 Open，打开整个 **Lab04CardGame** 文件夹（这一层包含 settings.gradle.kts），不要只打开 app。
2. 使用 Android Studio 自带 JDK，按提示安装 Android SDK 37，等待 Gradle 同步完成。联网首次同步需要下载依赖。
3. 启动 Android 模拟器或连接手机，顶部运行配置选 app，点击绿色运行按钮。
4. 首页可以进入资料修改和翻牌游戏。安装包另随完整提交包提供。

项目使用 Gradle 9.6.0、Android Gradle Plugin 9.4.0；最低 Android 6.0。SDK 路径由本机 Android Studio 配置，不上传 local.properties。

## 功能和代码位置

| 功能 | 文件 |
| --- | --- |
| 展示资料和最高分 | app/src/main/java/com/example/lab04/MainActivity.kt |
| 修改资料、校验和取消 | app/src/main/java/com/example/lab04/EditProfileActivity.kt |
| 翻牌、重开、刷新界面、保存当前牌局 | app/src/main/java/com/example/lab04/GameActivity.kt |
| MVC 模型与计分 | app/src/main/java/com/example/lab04/model/ |
| 当前玩家内存状态 | app/src/main/java/com/example/lab04/PlayerApp.kt |
| 三个页面布局 | app/src/main/res/layout/ |
| 界面字符串 | app/src/main/res/values/strings.xml |
| 单元测试 | app/src/test/java/com/example/lab04/CardGameTest.kt |
| 模拟器 UI 测试 | app/src/androidTest/java/com/example/lab04/GameUiTest.kt |

应用标题为“罗元2024110827”。姓名、学号已填；电话、邮箱和其他未知资料留空，用户可以自行修改。个人信息与最高分保留在当前应用进程中，不写入文件。

## 模型规则

从52张牌中随机抽取16张，每次翻开扣1分；同点数匹配奖励16分，同花色匹配奖励4分，不匹配额外扣2分。两张A最终得分为 -1 + 16 - 1 = 14。已配对牌不能重复选取；点击当前选中牌可取消选择，不额外扣分。

保持附件模型的随机发牌规则，因此每局不保证能全部配完8对。可以随时重新开始。牌局通过 onSaveInstanceState 保存，Activity 重建后恢复；个人资料没有跨进程持久化。

## 已完成的验证

2026-11-08 在 Windows 和 Android 15 API 35 模拟器完成：

- debug APK 构建成功。
- 10 项 JUnit 单元测试通过，失败0、错误0：匹配规则、计分、取消选择、已匹配状态、52张牌不重复、新局初值、序列化恢复。
- 4 项 Espresso UI 测试通过，失败0、错误0：保存资料、取消修改、翻牌及重开、Activity 重建恢复牌局。

复现命令（在项目目录执行，UI 测试前启动模拟器）：

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest
.\gradlew.bat :app:connectedDebugAndroidTest
```

原始测试 XML 在 docs/tests。它们是本次执行记录，后续修改后应重新测试。

## 报告与图片

- 原模板实验报告在 docs 目录，原始模板没有被覆盖。
- docs/screenshots 的四张图片来自实际模拟器测试：主页、资料编辑、初始牌局、首次翻牌。
- docs/generated-preview.png 是 AI 生成的预期界面例图，已在报告中标注；它不作为测试通过的证据，细节以实际运行截图和源代码为准。
- 报告中的核心代码图片摘自本项目实际 Kotlin 源码。

课程仓库：https://github.com/yuanluo197-cmyk/android-course-labs/tree/main/Lab04CardGame
