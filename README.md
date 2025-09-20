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

## Update 5.2: Transition Activity
How to use it? Please refer to the [`sample`](https://github.com/thanhnamzzz/CommonLib/tree/master/sample/src/main/java/io/virgo_common/common_lib/animationActivity).

## Update 5.4: BlurView
Fix bug
How to use it? Please refer to the sample - [`BlurActivity`](https://github.com/thanhnamzzz/CommonLib/blob/master/sample/src/main/java/io/virgo_common/common_lib/blurView/BlurActivity.kt).

##
#### BaseApp:
| Class | Description |
| --- | --- |
| `SimpleActivity` | A simple class is integrated with viewbinding for activity |
| `SimpleFragment` | A simple class is integrated with viewbinding for fragment |
#### CustomView:
| View | Description |
| --- | --- |
| `FingerGestures` | Touch and swipe for a view |
| `GradientBackgroundButton` | Gradient background button |
| `GradientTextView` | Gradient text view |
| `StrokeTextView` | Stroke text view |
| `ProgressWheel` | A custom progressbar |
| `Material Edittext` | A custom Edittext with label, color, font |
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

## Update 5.0: AnimationView
### Java
#### Import render animations

```java
import io.virgo_common.common_libs.animationView.AnimationView;
```

#### Start animation

```java
// Declare TextView
TextView AppleText = findViewById(R.id.TextView);

// Create AnimationView Class
AnimationView animationView = new AnimationView();

// Set Animation
animationView.setAnimation(Attention.Wobble(AppleText));
animationView.setDuration(2000L);
animationView.isLoop(true); //or false
animationView.setDelayLoop(1000L);
animationView.start();
```
### Kotlin
#### Import render animations

```kotlin
import io.virgo_common.common_libs.animationView.AnimationView
```

#### Start animation

```kotlin
// Declare TextView
val textView: TextView = findViewById(R.id.TextView)

// Create Render Class
 val animationView = AnimationView()

// Set Animation
animationView.setAnimation(Bounce().InDown(textView))
animationView.setDuration(2000L)
animationView.isLoop(true) //or false
animationView.setDelayLoop(1000L)
animationView.start()
```

## Animations

To animate the view, add the class name and specific animation method name`setAnimation` to an view. You can include the method `setDuration` to specify duration of animation. Default value for `duration` is `1000 Milliseconds`. Next, you need to add one of the following classes to the view:

| Class Name  |             |             |             |             |             |		  |
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| `Attention` | `Bounce`    | `Fade`      | `Flip   `   | `Rotate`    | `Slide`     | `Zoom`      |

Next, you can choose whether to loop the animation (by default, it’s disabled) using the `isLoop` method, and set the delay before the next animation using the `setDelayLoop` method (default is 1 second).

### Attention

| `Attention`       |                    |  		      |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `Bounce`|<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/1-attention-bounce.c6335f3d.gif">| `Flash`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/2-attention-flash.27fb56e5.gif">|
| `Pulse`           |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/3-attention-pulse.ee6d1fae.gif">| `Ruberband`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/4-attention-ruberband.a701fa5b.gif">|
| `Shake`           |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/5-attention-shake.62d9243a.gif">| `Standup`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/6-attention-standup.553e1945.gif">|
| `Swing`           |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/7-attention-swing.602dd7aa.gif">| `Tada`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/8-attention-tada.faa9f3c1.gif">|
| `Wave`            |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/9-attention-wave.9a37979d.gif">| `Wobble`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/10-attention-wobble.42ac8c56.gif">|

### Bounce

| `Bounce`	    |                    |       	      |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/15-bounce-in-down.6aad1cbd.gif">| `InUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/13-bounce-in-up.7a1d7c17.gif">|
| `InLeft`           |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/11-bounce-in-left.ebc9bc0f.gif">| `InRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/12-bounce-in-right.a66d3b31.gif">|
| `In`           |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/14-bounce-in.2ef584a6.gif">	|		|		|

### Fade

| `Fade`            |                    |                    |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/18-fade-in-down.24e645e6.gif">| `InUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/19-fade-in-up.a2b79fa7.gif">|
| `InLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/16-fade-in-left.6e93da17.gif">| `InRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/17-fade-in-right.59345f8c.gif">|
| `OutDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/22-fade-out-down.4f4ebc27.gif">| `OutUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/25-fade-out-up.75f60e2d.gif">|
| `OutLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/20-fade-out-left.7f1583e0.gif">| `OutRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/21-fade-out-right.ff6adf09.gif">|
| `In`               |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/23-fade-in.532822b1.gif">| `Out`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/24-fade-out.a3ee0d3a.gif">|

### Flip

| `Flip`            |                    |                    |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InX`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/26-flip-in-x.1de9f264.gif">| `InY`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/27-flip-in-y.8f45c5e1.gif">|
| `OutX`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/28-flip-out-x.09be8ac3.gif">| `OutY`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/29-flip-out-y.f5c532a8.gif">|

### Rotate

| `Rotate`          |                    |                    |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InDownLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/30-rotate-in-down-left.cdd124aa.gif">| `InDownRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/31-rotate-in-down-right.f0693f8b.gif">|
| `InUpLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/32-rotate-in-up-left.f818146d.gif">| `InUpRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/33-rotate-in-up-right.537606e2.gif">|
| `OutDownLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/34-rotate-out-down-left.de06d895.gif">| `OutDownRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/35-rotate-out-down-right.f4505ac5.gif">|
| `OutUpLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/39-rotate-up-left.de13c028.gif">| `OutUpRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/36-rotate-out-up-right.ddb7df75.gif">|
| `In`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/37-rotate-in.08924c36.gif">| `Out`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/38-rotate-out.b127d7a8.gif">|

### Slide

| `Slide`           |                    |                    |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/40-slide-in-down.efa18709.gif">| `InUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/43-slide-in-up.0f918432.gif">|
| `InLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/41-slide-in-left.da5a9127.gif">| `InRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/42-slide-in-right.f0e33c31.gif">|
| `OutDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/44-slide-out-down.269616ff.gif">| `OutUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/47-slide-out-up.7efa439e.gif">|
| `OutLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/45-slide-out-left.4226eca7.gif">| `OutRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/46-slide-out-right.f283b64b.gif">|

### Zoom

| `Zoom`            |                    |                    |                    |
| ----------------- | ------------------ | ------------------ | ------------------ |
| `InDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/48-zoom-in-down.6e41a799.gif">| `InUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/51-zoom-in-up.40e3a26e.gif">|
| `InLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/49-zoom-in-left.82604bb4.gif">| `InRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/50-zoom-in-right.a419ab92.gif">|
| `OutDown`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/52-zoom-out-down.cb3ee254.gif">| `OutUp`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/57-zoom-out-up.0c74d038.gif">|
| `OutLeft`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/53-zoom-out-left.79cb419d.gif">| `OutRight`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/54-zoom-out-right.7a3ac74d.gif">|
| `In`          |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/55-zoom-in.1c0c5d5a.gif">| `Out`              |<img width="200" alt="portfolio_view" src="https://gayanvoice.github.io/android-animations/static/media/56-zoom-out.f0c52b21.gif">|

This android view animations library supports number of animations. This effect is inspired by the following library:
Click here https://github.com/gayanvoice/android-animations-kotlin
