# GodotFacebookAd

Facebook Audience Network plugin for Godot Engine

It supports:

- Interstitial
- Rewarded Video


**Note:**
It requires Gradle Version >= 5.6.4 so it might not work in gradle version 3.2.1 and below. I tested it in Godot 3.2.2beta1 which uses gradle 5.6.4 and it is working fine.


How to use
----------
- Configure, install  and enable the "Android Custom Template" for your project, just follow the [official documentation](https://docs.godotengine.org/en/latest/getting_started/workflow/export/android_custom_build.html);
- download this project
- drop the ```facebookAd-plugin``` directory (from the zip package) inside the ```res://android/``` directory on your Godot project.
- drop the ```facebookAd-lib``` directory (from the zip package) inside the ```res://``` directory on your Godot project.
- on the Project -> Export -> Android -> Options -> Permissions: check the permissions for _Access Network State_ and _Internet_
- on the Project Settings -> Android -> Modules, add the string:

![modules](https://user-images.githubusercontent.com/11765482/80276154-64dd4e00-8706-11ea-9775-8c6bd0222612.png)


```
org/godotengine/godot/GodotFacebookAd
```

Now you'll able to add an Admob Node to your scene (**only one node should be added per scene**)

![node](https://user-images.githubusercontent.com/11765482/80276140-48411600-8706-11ea-8633-81b80f8abaf6.png)

Edit ad ids

![ids](https://user-images.githubusercontent.com/11765482/80276155-660e7b00-8706-11ea-9851-c8342627d002.png)

And connect its signals

![signals](https://user-images.githubusercontent.com/11765482/80276152-60189a00-8706-11ea-8b7f-f6339af50636.png)

Sample code
-----
In the demo directory you'll find a working sample project where you can see how the things works on the scripting side. But you need to provide your `interstitialAdId`



API Reference
-------------

### Properties
```python

  interstitialAdId
  rewardedVideoAdId

```

### Methods
```python

 # show interstitial ad
 # next ad will be automatically loaded
 # for your next show call
 show_interstitial()

 # show rewarded video
 # next ad will be automatically loaded
 # for your next show call
 func show_rewarded_video()

```
### Signals
```python
signal interstitial_loaded
signal interstitial_displayed
signal interstitial_dismissed
signal interstitial_error(adError)
signal interstitial_clicked
signal interstitial_impression_logged

signal rewarded_video_loaded
signal rewarded_video_displayed
signal rewarded_video_closed
signal rewarded_video_error(adError)
signal rewarded_video_clicked
signal rewarded_video_impression_logged
signal rewarded_video_completed
```

Troubleshooting
--------------
* First of all, please make sure you're able to compile the custom build for Android without the plugin, this way we can isolate the cause of the issue.

* Using logcat for Android is the best way to troubleshoot most issues. You can filter Godot only messages with logcat using the command: 
```
adb logcat -s godot
adb -d logcat GodotFacebookAd:V FAN:V godot:V *:S

```
* _GodotFacebookAd Java Singleton not found_: this module is Android only, so the GodotFacebookAd Java singleton will only exists on Android platform. In other words, you will be able to run it on an Android device (or emulator) only, it will not work on editor or on another platform.


References
-------------
Based on the works of:
* https://github.com/MrZak-dev/GodotFAN
* https://github.com/Shin-NiL/Godot-Android-Admob-Plugin

License
-------------
MIT license
