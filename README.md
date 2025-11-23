# KotlinLearn - Android 现代开发实战项目

## 简介

**KotlinLearn** 是一个基于 Kotlin 语言的 Android 学习与实践项目。本项目旨在演示和探索 Android 现代开发中的各种主流技术栈、架构模式以及 Jetpack 组件的使用。通过多个模块和示例，展示了如何构建高质量、可维护的 Android 应用。

无论你是 Android 初学者，还是希望深入了解 MVVM、MVI、协程或 Jetpack 组件的开发者，本项目都能提供丰富的参考代码。

## 项目特点

- **全 Kotlin 编写**：完全使用 Kotlin 语言，充分利用其简洁性和强大的特性（如协程、扩展函数等）。
- **现代架构**：
  - **MVVM**: 深入实践 Model-View-ViewModel 架构。
  - **MVI**: 探索 Model-View-Intent 单向数据流架构。
- **Jetpack 全家桶**：
  - **Lifecycle**: 感知生命周期。
  - **LiveData & ViewModel**: 数据驱动 UI。
  - **DataBinding & ViewBinding**: 高效的视图绑定。
  - **Navigation**: 单 Activity 多 Fragment 导航管理。
  - **Room**: 本地数据库 ORM 框架。
  - **Paging 3**: 分页加载库。
  - **WorkManager**: 后台任务管理。
- **依赖注入**：使用 **Koin** (v3) 进行轻量级依赖注入。
- **组件化路由**：集成 **ARouter** 实现模块间解耦通信。
- **网络层**：封装 **Retrofit + OkHttp + Gson**，支持拦截器和日志。
- **异步编程**：全面使用 **Kotlin Coroutines (协程)** 替代传统回调。
- **调试与性能**：集成 **DoKit (DoraemonKit)** 和 **LeakCanary** 进行性能监控和内存泄漏检测。

## 模块说明

项目采用多模块结构，主要包含以下模块：

| 模块名称 | 说明 |
| :--- | :--- |
| `app` | 主应用模块，包含入口和综合示例 (Coroutines, MVI, Navigation 等) |
| `mvvm` | MVVM 架构模式的独立演示模块 |
| `netdemo` | 网络请求封装与演示模块 |
| `2-7-livedatadem` | LiveData 使用示例 |
| `2-8-viewModel` | ViewModel 使用示例 |
| `2-9-databinding` | DataBinding 使用示例 |
| `2-12-liveCycle` | Lifecycle 生命周期组件示例 |
| `leakcanarylearn` | LeakCanary 内存泄漏检测学习模块 |

## 技术栈一览

- **语言**: Kotlin
- **核心库**: AndroidX, Material Design
- **架构组件**: Jetpack (Lifecycle, ViewModel, LiveData, Room, Paging3, Navigation, WorkManager)
- **网络**: Retrofit, OkHttp, Gson
- **依赖注入**: Koin
- **路由**: ARouter
- **事件总线**: LiveEventBus
- **工具**: DoKit, LeakCanary

## 快速开始

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/KotlinLearn.git
   ```

2. **打开项目**
   使用 Android Studio 打开项目根目录。

3. **同步依赖**
   等待 Gradle Sync 完成，下载所需依赖。

4. **运行**
   连接 Android 设备或模拟器，选择 `app` 模块运行即可。

## 环境要求

- Android Studio Arctic Fox 或更高版本
- Kotlin 1.6+
- Gradle 7.0+
- JDK 11 或更高版本

## 贡献

欢迎提交 Issue 和 Pull Request 来完善本项目！

## 许可证

本项目仅供学习交流使用。
