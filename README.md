[![JitPack](https://jitpack.io/v/thanhnamzzz/CommonLib.svg)](https://jitpack.io/#thanhnamzzz/CommonLib)
# Commons
This is my library for Android. It is very simple and will be gradually developed day by day.</br>
# Download
#### Gradle:

```gradle
repositories {
  mavenCentral()
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.thanhnamzzz:CommonLib:last-version'
}
```
# Feature
#### BaseApp:
| Class | Description |
| --- | --- |
| `SimpleActivity` | A simple class is integrated with viewbinding for activity |
| `SimpleFragment` | A simple class is integrated with viewbinding for fragment |
#### CustomView:
| View | Description |
| --- | --- |
| `FingerGestures` | Touch and swipe for a view |
| `GradientTextView` | Gradient text view |
| `StrokeTextView` | Stroke text view |
#### Extensions:
Many extensions such as:
- `Binding`: for class `SimpleActivity` và `SimpleFragment`
- `Bitmap`:
  + `saveBitmapToJpgCache`
  + `saveBitmapToPng`
  + `bitmapResize`
  + `cropWithRect`
- `File`: Function for File(), storage,
- `TextTime`: format text time
- `VersionSDK`: function check version SDK
- `View`: `VISIBLE`, `GONE`, `INVISIBLE`, view's point location (in screen)
- `Window`:
  + `updateStatusBarColors`
  + `updateNavigationBarColors`
#### Global Functions:
- `ClipboardFunction`: `copyToClipboard` and `getTextFromClipboard`
- `FileUtils`: check hidden directory, mineType of file, copy file, date file, size file, Video Resolution, Image Resolution
- `GlobalFunction`:
  + `hideSystemNavigationBar`
  + `hideSystemStatusBar`
  + `hideSystemUiBar` (both navigation and status bar)
  + `showSystemUiBar`
  + `hideKeyBoard` and `showKeyBoard`
  + `shareApp`: share link app
  + `shareFile`
  + `shareText`
  + `openMarket`
- `Permission Setting`: Open the app's permission settings.
- `Sort`: Sort the data list in ascending order (***isAscending = true***) or descending order (***isAscending = false***) based on the reference (***propertySelector***) of the data list.
#### Views:
- `ImageGlide`: methods for loading images with Glide.
- `MyToast`: a custom layout for Toast.
- `ThemeApp`: functions for setting the app theme (night mode, light mode)
