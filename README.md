# HackerNews Android Demo

### Description
This is simple demo app which uses [HackerNews API](https://github.com/HackerNews/API) to fetch data and display.

There are two screens 
* Top Stories Screen (use [top stories API](https://hacker-news.firebaseio.com/v0/topstories.json) )
* Story Comment Screen (use [item API](https://hacker-news.firebaseio.com/v0/item/2921983.json))

<img src="https://firebasestorage.googleapis.com/v0/b/nano-projects.appspot.com/o/photos%2Fscreen_top_stories.png?alt=media" width="200"> <img src="https://firebasestorage.googleapis.com/v0/b/nano-projects.appspot.com/o/photos%2Fscreen_comment.png?alt=media" width="200">


### Third party libraries used in this demo
* [ThirtyInch](https://github.com/grandcentrix/ThirtyInch): MVP framework
* [Dagger2](https://github.com/google/dagger): dependency injection framework
* [Butterknife](https://github.com/JakeWharton/butterknife): bind views and callbacks for to fields and methods
* [Timber](https://github.com/JakeWharton/timber): Log helper
* [RxJava2](https://github.com/ReactiveX/RxJava): a library for composing asynchronous and event-based programs using observable sequences for the Java VM
* [RxAndroid](https://github.com/ReactiveX/RxAndroid): RxJava binding for Android, provides `Scheduler` which can run on UI thread
* [Retrofit](https://github.com/square/retrofit): Type-safe HTTP client for Android and Java by Square, Inc (combined with `gson converter` and `rxjava2 adapter`)
* [Stetho](https://github.com/facebook/stetho): provides `StethoInterceptor` allow to debug app using Chrome Developer Tools
* [Retrolambda](https://github.com/evant/gradle-retrolambda): plugin which allows to build Java or Android project with retrolambda
* [DataBinding](https://developer.android.com/topic/libraries/data-binding/index.html): no more `findViewBindId` when enable this config
* [LeakCanary](https://github.com/square/leakcanary): a memory leak detection library for Android and Java.
* [Mockito](https://github.com/mockito/mockito): mocking framework for writing unit tests
* [Support Library](https://developer.android.com/topic/libraries/support-library/index.html): provide additional convenience classes and features not available in the standard Framework API

### Other very useful libraries (which are not used in this demo)
* [Fresco](https://github.com/facebook/fresco): powerful library for loading images, managing memory and more
* [EventBus](https://github.com/greenrobot/EventBus): simplifies communication between Activities, Fragments, Threads, Services, etc. (but do not overuse this library because it would be hard for tracking data flow)
* [Realm.io](https://github.com/realm/realm-java): easy to use database, a replacement for SQLite
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html): a collection of libraries from Google including LifeCycleObserver, LiveData, ViewModel and Room
* Many more..

### Tracking tools
* [Fabric.io](https://fabric.io)
* [Firebase](https://firebase.google.com/docs/android/setup)


