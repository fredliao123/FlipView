#FlipView View
-----------------------------------------------------------------------

## Introduction

A custom view that flip bitmap in a animation：

![Gif example](https://github.com/fredliao123/FlipView/blob/master/app-release/gif/i0dklPJlQm.gif)

[app-release.apk](https://github.com/fredliao123/FlipView/blob/master/app-release/app-release.apk)


---------------------------------------------------------------------------------------------------------------

##To Use This View
Just type in you gradle

`compile 'bupt.liao.fred:FlipView:1.0.0'`

And to use in xml:
```
<bupt.liao.fred.flipviewlibrary.FlipView
        android:id="@+id/flip"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerInParent="true"
        />
```

And add to java code:
```
flipView = (FlipView)findViewById(R.id.flip);
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            flipView.startFlip();
        }
    });
```
