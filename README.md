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


## Usage
### Initialization
You need to initialize the sdk by providing the application context, Public Key and the Imagekit Id. You can do this either in your application or launcher activity. This needs to be called before any other function in the SDK or else an exception would be thrown.
```kotlin
ImageKit.init(applicationContext, <CLIENT_PUBLIC_KEY>, <IMAGEKIT_ID>)
```

### Image Upload

The following example uploads a bitmap or an image file using the given filename, a list of tags to associate with the uploaded image and an image upload callback:
``` kotlin
        val filename = "icLauncher.png"
        val timestamp = System.currentTimeMillis()
        ImageKit.getInstance().uploadImage(
            imageBitmap/file
            ,filename
            , <SIGNATURE>
            , timestamp
            , true
            , arrayOf("tag1", "tag2", "tag3")
            , "/folder/path/to/image/"
            , callback
        )
```

For more information on the parameters and steps to create a signature for upload, see https://docs.imagekit.io/#server-side-image-upload. An example implementation to generate a valid in android can also be found here: https://github.com/imagekit-developer/imagekit-android/blob/master/app/src/main/java/com/imagekit/android/SignatureUtil.java

### File Upload
The following example uploads a file using the given filename, a list of tags to associate with the uploaded file and a file upload callback:
``` kotlin
        val filename = "icLauncher.png"
        val timestamp = System.currentTimeMillis()
        ImageKit.getInstance().uploadImage(
            file
            ,filename
            , <SIGNATURE>
            , timestamp
            , true
            , arrayOf("tag1", "tag2", "tag3")
            , "/folder/path/to/file/"
            , callback
        )
```
