# 实验五 RecyclerView 翻牌游戏

作者：罗元 2024110827。基于实验四项目扩展。

## 功能

- 首页展示玩家资料，可以修改信息；菜单中可打开“关于”对话框。
- 竖屏使用 RecyclerView 4 列展示 24 张牌，横屏 6 列展示 18 张牌。
- 连续选择三张牌进行匹配：三张同点数或同花色可得分；未匹配时前两张翻回，第三张继续保留。
- 重新开始会重新发牌并清零得分。牌组按点数生成，每局都存在可完成的三张组合。
- `Card` 和 `CardMatchingGame` 为模型，`CardAdapter` 负责牌面，`GameActivity` 负责交互和刷新。

## 运行与验证

使用 Android Studio 打开本目录，选择模拟器后运行 `app`。命令行可运行：

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:connectedDebugAndroidTest
```

已在 Android 15（API 35）模拟器上验证：8 项单元测试、4 项界面测试通过。实际运行截图见仓库中的 `Lab05RecyclerCardGame/docs/screenshots/`。
