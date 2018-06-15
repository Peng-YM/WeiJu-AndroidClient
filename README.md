# 微聚

[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![CodeFactor](https://www.codefactor.io/repository/github/peng-ym/weiju-androidclient/badge)](https://www.codefactor.io/repository/github/peng-ym/weiju-androidclient)
![](https://tokei.rs/b1/github/Peng-YM/WeiJu-AndroidClient)
![](https://tokei.rs/b1/github/Peng-YM/WeiJu-AndroidClient?category=files)
[![CircleCI](https://circleci.com/gh/Peng-YM/WeiJu-AndroidClient/tree/master.svg?style=svg&circle-token=5b83b358dafd7082f76eeb2de4412045b3832b46)](https://circleci.com/gh/Peng-YM/AndroidClient/tree/master)
--------
![logo](https://ws3.sinaimg.cn/large/006tNc79gy1fsbemu29jlj30a00a0tbp.jpg)

## 介绍

本项目实现了一个在安卓端进行数据标注，采集的平台。实现的功能如下：

1. **用户管理系统**

   - [x] 用户注册，登录
   - [x] 查看，修改用户信息

2. **任务管理系统**

   - [x] 查看所有，进行中，已完成的图片
   - [x] 发布采集任务，可采用富文本编辑器编辑任务详情页

3. **采集任务**

   - [x] 图片采集，从相册中选取图片或使用相机进行拍照
   - [x] 视频采集，从相册中选取视频或使用相机进行拍摄

4. **标注任务**

   - [x] 使用JSON自定义标注的标签及每个标签对应的属性，实现灵活的标注

     

   - [x] 单图片多标签标注

## Demo

| ![9010BDFF-0A1F-4AA2-B6BE-825F18043EC7](https://ws2.sinaimg.cn/large/006tNc79gy1fsbeu8m3daj30bj0kegpx.jpg) | ![C58C9C93-52AF-44A8-93C6-6F1259270FF8](https://ws4.sinaimg.cn/large/006tNc79gy1fsbetmwgilj30bj0ke7cn.jpg) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![97794291-AC1D-40B3-806D-6092375BE19A](https://ws3.sinaimg.cn/large/006tNc79gy1fsbeuoiq2fj30bj0keadg.jpg) | ![C5C49937-BC74-4147-990C-A92D88BB6EEB](https://ws4.sinaimg.cn/large/006tNc79gy1fsbeuxb26ej30bj0kegpy.jpg) |
| ![C7EEA005-6E62-46E6-A44B-3745DC25B34E](https://ws4.sinaimg.cn/large/006tNc79gy1fsbev30mntj30bj0keq4v.jpg) | ![BFF6A6D6-41C9-4151-B864-A525314122B4](https://ws4.sinaimg.cn/large/006tNc79gy1fsbev98xltj30bj0kegqz.jpg) |
| ![ACB9D587-B386-4B06-8BA2-1970E9D2DF38](https://ws1.sinaimg.cn/large/006tNc79gy1fsbevt8xofj30bj0ke0y1.jpg) | ![69656618-A829-4FBA-8896-4946E8F8D900](https://ws3.sinaimg.cn/large/006tNc79gy1fsbevzc8tdj30bj0ke778.jpg) |
| ![F765E3F8-EBBB-45CE-8A93-8E89AC4E173F](https://ws4.sinaimg.cn/large/006tNc79gy1fsbewa1chij30bj0kedhg.jpg) | ![895DCE69-F48A-4AAE-920D-6075D438F79F](https://ws4.sinaimg.cn/large/006tNc79gy1fsbewf9o9rj30bj0keab8.jpg) |
| ![3E3E8408-EBF9-4F5C-A984-C81EDC41BEAA](https://ws2.sinaimg.cn/large/006tNc79gy1fsbexrnwbpj30bj0kewiv.jpg) | ![DC946BDE-4540-47AD-96CB-14E9784088CC](https://ws4.sinaimg.cn/large/006tNc79gy1fsbez2tyjej30bj0ke76e.jpg) |

## 界面实现

- [x] 登录界面
- [x] 主界面
- [x] 用户信息页
- [x] 任务详情页
- [x] 我的任务页
- [x] 任务发布页
- [x] 数据采集页
- [x] 数据标注页
## 第三方库

### 架构

- [Restrofit2](https://github.com/square/retrofit) 处理Restful HTTP请求端

- [RxJava2](https://github.com/ReactiveX/RxJava) Java异步框架

- [Dagger2](https://github.com/google/dagger) [ButterKnife](http://jakewharton.github.io/butterknife/) 依赖注入框架

- [Room Persistent Library](https://developer.android.com/topic/libraries/architecture/room)  本地数据库

### 调试

- [Logger](https://github.com/orhanobut/logger) 调试信息

- [Android-Debug-Database](https://github.com/amitshekhariitbhu/Android-Debug-Database) 安卓数据库调试

## UI

- [Data Binding](https://developer.android.com/topic/libraries/data-binding/index.html) 数据绑定

- [Glide](https://github.com/bumptech/glide) 图片加载

- [Material Dialogs](https://github.com/afollestad/material-dialogs) 弹窗

- [Knife](https://github.com/mthli/Knife) 富文本编辑器

- [CircleImageView](https://github.com/hdodenhof/CircleImageView) 圆角图片
- [AwesomeValidation]() 表单验证
- [PhotoView](https://github.com/chrisbanes/PhotoView) 图片展示控件

## 开发者

### 移动端

[Peng-YM](https://github.com/Peng-YM)

[Alinxl](https://github.com/Alinxl)

[HansWanglin](https://github.com/Hanswanglin)

### 服务端

[Wang-GY](https://github.com/Wang-GY)

[l0uvre](https://github.com/l0uvre)

## 许可证

GPL v3.0[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)