# imagekit-android
#### Version: 0.0.3
Android client for Imagekit Integration


## Integration
In your root build.gradle file, add:
```xml
allprojects {
    repositories {
       ...
        maven { url "https://jitpack.io" }
        maven{
            url 'https://raw.github.com/zeeshanejaz/unirest-android/mvn-repo'
        }
    }
}
```

In the module build.gradle file, add:
```xml 
implementation 'com.github.imagekit-developer:imagekit-android:<VERSION>'
```
