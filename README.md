[<img width="250" alt="ImageKit.io" src="https://raw.githubusercontent.com/imagekit-developer/imagekit-javascript/master/assets/imagekit-light-logo.svg"/>](https://imagekit.io)

# ImageKit.io Android SDK

[![Release](https://jitpack.io/v/imagekit-developer/imagekit-android.svg)](https://jitpack.io/#imagekit-developer/imagekit-android)
![Build](https://github.com/imagekit-developer/imagekit-android/workflows/Gradle%20Tests/badge.svg)
[![codecov](https://codecov.io/gh/imagekit-developer/imagekit-android/branch/master/graph/badge.svg?token=BeabJwhQoh)](https://codecov.io/gh/imagekit-developer/imagekit-android/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Twitter Follow](https://img.shields.io/twitter/follow/imagekitio?label=Follow&style=social)](https://twitter.com/imagekitio)

Android SDK for [ImageKit.io](https://imagekit.io) which implements client-side upload and URL generation for use inside an Android application.

ImageKit is a complete image optimization and transformation solution that comes with an [image CDN](https://imagekit.io/features/imagekit-infrastructure) and media storage. It can be integrated with your existing infrastructure - storages like AWS S3, web servers, your CDN and custom domain names, allowing you to deliver optimized images in minutes with minimal code changes.

ImageKit Android SDK allows you to use real-time [image resizing](https://docs.imagekit.io/features/image-transformations), [optimization](https://docs.imagekit.io/features/image-optimization), and [file uploading](https://docs.imagekit.io/api-reference/upload-file-api/client-side-file-upload) in the client-side.

## Installation

### Requirements
The library requires Android version 5.0.0 (API level 21 - Lollipop) or above.

### Installation
In your root build.gradle file, add:
```gradle
allprojects {
    repositories {
       ...
        maven { url "https://jitpack.io" }
    }
}
```

In the module build.gradle file, add:
```gradle 
implementation 'com.github.imagekit-developer:imagekit-android:<VERSION>'
```

## Usage

### Initialization
`urlEndpoint` is the required parameter. You can get the value of URL-endpoint from your ImageKit dashboard - https://imagekit.io/dashboard#url-endpoints.

`publicKey` and `authenticationEndpoint` parameters are optional and only needed if you want to use the SDK for client-side file upload. You can get these parameters from the developer section in your ImageKit dashboard - https://imagekit.io/dashboard#developers.

`transformationPosition` is optional. The default value for this parameter is `TransformationPosition.PATH`. Acceptable values are `TransformationPosition.PATH` & `TransformationPosition.QUERY`.

`defaultUploadPolicy` is optional and only needed if you want to use the SDK for client-side file upload. This sets the default constraints for all the upload requests.

_Note: Do not include your Private Key in any client side code, including this SDK or its initialization._

```kotlin
// In kotlin
import com.imagekit.android.ImageKit;

ImageKit.init(
            context = applicationContext,
            publicKey = "your_public_api_key",
            urlEndpoint = "https://ik.imagekit.io/your_imagekit_id",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "your_authentication_endpoint",
            defaultUploadPolicy = UploadPolicy.Builder()
                .requireNetworkType(UploadPolicy.NetworkType.ANY)
                .setMaxRetries(3)
                .build()
        )
```

```java
// In Java
import com.imagekit.android.ImageKit;

ImageKit.Companion.init(
        getApplicationContext(),
        "your_public_api_key",
        "https://ik.imagekit.io/your_imagekit_id",
        TransformationPosition.PATH,
        "your_authentication_endpoint",
        UploadPolicy.Builder()
        .requireNetworkType(UploadPolicy.NetworkType.ANY)
        .setMaxRetries(3)
        .build()
    );
```

### Quick Examples

### URL construction
#### Using image path
```kotlin
// Kotlin
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,ar-3-2
ImageKit.getInstance()
        .url(
            path = "default-image.jpg",
            transformationPosition = TransformationPosition.QUERY
        )
        .height(400f)
        .aspectRatio(3, 2)
        .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,ar-3-2
ImageKit.Companion.getInstance()
        .url(
            "default-image.jpg",
            TransformationPosition.QUERY
        )
        .height(400f)
        .aspectRatio(3, 2)
        .create()
```


#### Using full image URL
```kotlin
// https://ik.imagekit.io/your_imagekit_id/medium_cafe_B1iTdD0C.jpg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20
ImageKit.getInstance()
        .url(
            src = https://ik.imagekit.io/your_imagekit_id/medium_cafe_B1iTdD0C.jpg",
            transformationPosition = TransformationPosition.PATH
        )
        .overlayImage("logo-white_SJwqB4Nfe.png")
        .overlayPosX(10)
        .overlayPosY(20)
        .create()
```

```java
// https://ik.imagekit.io/your_imagekit_id/medium_cafe_B1iTdD0C.jpg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20
ImageKit.Companion.getInstance()
        .url(
            "https://ik.imagekit.io/your_imagekit_id/medium_cafe_B1iTdD0C.jpg",
            TransformationPosition.PATH
        )
        .overlayImage("logo-white_SJwqB4Nfe.png")
        .overlayPosX(10)
        .overlayPosY(20)
        .create()
```

#### Using a custom parameter
```kotlin
// https://ik.imagekit.io/your_imagekit_id/plant.jpeg?tr=w-400,ot-Hand with a green plant,otc-264120,ots-30,ox-10,oy-10
ImageKit.getInstance()
        .url(src = "https://ik.imagekit.io/your_imagekit_id/plant.jpeg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20")
        .addCustomTransformation("w", "400")
        .overlayText("Hand with a green plant")
        .overlayTextColor("264120")
        .overlayTextSize(30)
        .overlayPosX(10)
        .overlayPosY(10)
        .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/plant.jpeg?tr=w-400,ot-Hand with a green plant,otc-264120,ots-30,ox-10,oy-10
ImageKit.Companion.getInstance()
        .url("https://ik.imagekit.io/your_imagekit_id/plant.jpeg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20")
        .addCustomTransformation("w", "400")
        .overlayText("Hand with a green plant")
        .overlayTextColor("264120")
        .overlayTextSize(30)
        .overlayPosX(10)
        .overlayPosY(10)
        .create()
```

#### Upload file from bitmap
``` kotlin
// Kotlin
ImageKit.getInstance().uploader().uploadImage(
    file = bitmap!!
    , fileName = filename
    , useUniqueFilename = false
    , tags = arrayOf("nice", "copy", "books")
    , folder = "/dummy/folder/"
    , imageKitCallback = this
)
```

``` kotlin
// Java
ImageKit.Companion.getInstance().uploader().uploadImage(
    bitmap,
    filename,
    false, // useUniqueFilename
    new String[]{"nice", "copy", "books"}, // tags, 
    "/dummy/folder/", 
    imageKitCallback
)
```

#### Upload file from a remote URL
``` kotlin
// Kotlin
ImageKit.getInstance().uploader().upload(
    file = "https://ik.imagekit.io/demo/img/default-image.jpg"
    , fileName = filename
    , useUniqueFilename = false
    , tags = arrayOf("nice", "copy", "books")
    , folder = "/dummy/folder/"
    , imageKitCallback = this
)
```

``` java
// Java
ImageKit.Companion.getInstance().uploader().upload(
    "https://ik.imagekit.io/demo/img/default-image.jpg", 
    filename, 
    false, // useUniqueFilename
    new String[]{"nice", "copy", "books"}, // tags, 
    "/dummy/folder/", 
    imageKitCallback
)
```

#### Upload file using binary
```kotlin
// Kotlin
ImageKit.getInstance().uploader().upload(
    file = file!!
    , fileName = file!!.name
    , useUniqueFilename = true
    , tags = arrayOf("nice", "copy", "books")
    , folder = "/dummy/folder/"
    , imageKitCallback = this
)
```

```java
// Java
ImageKit.Companion.getInstance().uploader().upload(
    file,
    filename, 
    false, // useUniqueFilename
    new String[]{"nice", "copy", "books"}, // tags, 
    "/dummy/folder/", 
    imageKitCallback
)
```

## Demo application
* The official step by step iOS quick start guide - https://docs.imagekit.io/getting-started/quickstart-guides/ios
* You can also run the demo application in [sample](/sample) folder.

## Components

The library includes 3 Primary Classes:

* [`ImageKit`](#ImageKit) for defining options like `urlEndpoint`, `publicKey` or `authenticationEndpoint` for the application to use.
* `ImageKitURLConstructor` for [constructing image urls](#constructing-image-urls).
* `ImageKitUploader`for client-side [file uploading](#file-upload).
* `UploadPolicy` for setting a set of policy constraints that need to be validated for an upload request to be executed.
* `ImagePreprocess` to set the parameters for preprocessing the image to be uploaded.
* `VideoPreprocess` to set the parameters for preprocessing the video to be uploaded.

## ImageKit
In order to use the SDK, you need to provide it with a few configuration parameters. 
```kotlin
// In kotlin
import com.imagekit.android.ImageKit;

ImageKit.init(
            context = applicationContext,
            publicKey = "your_public_api_key",
            urlEndpoint = "https://ik.imagekit.io/your_imagekit_id",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "http://www.yourserver.com/auth",
            defaultUploadPolicy = UploadPolicy.Builder()
                .requireNetworkType(UploadPolicy.NetworkType.ANY)
                .setMaxRetries(3)
                .build()
        )
```

```java
// In Java
import com.imagekit.android.ImageKit;

ImageKit.Companion.init(
        getApplicationContext(),
        "your_public_api_key",
        "https://ik.imagekit.io/your_imagekit_id",
        TransformationPosition.PATH,
        "http://www.yourserver.com/auth",
        UploadPolicy.Builder()
        .requireNetworkType(UploadPolicy.NetworkType.ANY)
        .setMaxRetries(3)
        .build()
    );
```
* `urlEndpoint` is required to use the SDK. You can get URL-endpoint from your ImageKit dashboard - https://imagekit.io/dashboard#url-endpoints.
* `publicKey` and `authenticationEndpoint` parameters are required if you want to use the SDK for client-side file upload. You can get these parameters from the developer section in your ImageKit dashboard - https://imagekit.io/dashboard#developers.
* `transformationPosition` is optional. The default value for this parameter is `TransformationPosition.PATH`. Acceptable values are `TransformationPosition.PATH` & `TransformationPosition.QUERY`.
* `defaultUploadPolicy` is optional and only needed if you want to use the SDK for client-side [file uploading](#file-upload). This sets the default constraints for all the upload requests.

> Note: Do not include your Private Key in any client-side code.

## Constructing Image URLs
The `ImageKitURLConstructor` is used to create a url that can be used for rendering and manipulating images in real-time. `ImageKitURLConstructor` consists of functions that can be chained together to perform transformations.

`ImageKitURLConstructor` can be initialized by calling `ImageKit.getInstance().url(...)` with a set of parameters defined below.

| Parameter             | Type | Description                    |
| :----------------| :----|:----------------------------- |
| urlEndpoint      | String | Optional. The base URL to be appended before the path of the image. If not specified, the URL-endpoint specified in the parent `IKContext` component is used. For example, https://ik.imagekit.io/your_imagekit_id/endpoint/ |
| path             | String |Conditional. This is the path at which the image exists. For example, `/path/to/image.jpg`. Either the `path` or `src` parameter needs to be specified for URL generation. |
| src              | String |Conditional. This is the complete URL of an image already mapped to ImageKit. For example, `https://ik.imagekit.io/your_imagekit_id/endpoint/path/to/image.jpg`. Either the `path` or `src` parameter needs to be specified for URL generation. |
| transformationPosition | [TransformationPosition](/imagekit/src/main/java/com/imagekit/android/ImagekitUrlConstructor.kt) |Optional. The default value is `.PATH` that places the transformation string as a URL path parameter. It can also be specified as `.QUERY`, which adds the transformation string as the URL's query parameter i.e.`tr`. If you use `src` parameter to create the URL, then the transformation string is always added as a query parameter. |

The transformations to be applied to the URL can be chained to `ImageKit.getInstance().url(...)`. See the list of [different tranformations](#list-of-supported-transformations). Different steps of a [chained transformation](https://docs.imagekit.io/features/image-transformations/chained-transformations) can be added by calling the function `chainTransformation`.

### Basic Examples
```kotlin
// Kotlin
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,ar-3-2
ImageKit.getInstance()
        .url(
            path = "default-image.jpg",
            transformationPosition = TransformationPosition.QUERY
        )
        .height(400f)
        .aspectRatio(3, 2)
        .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,ar-3-2
ImageKit.Companion.getInstance()
        .url(
            "default-image.jpg",
            TransformationPosition.QUERY
        )
        .height(400f)
        .aspectRatio(3, 2)
        .create()
```

More Examples can be found in [URLGenerationTests.kt](imagekit/src/test/java/com/imagekit/android/URLGenerationTests.kt)

### List of supported transformations
The complete list of transformations supported and their usage in ImageKit can be found [here](https://docs.imagekit.io/imagekit-docs/image-transformations). The SDK provides a function for each transformation parameter, making the code simpler and readable. If a transformation is supported in ImageKit, but if a name for it cannot be found in the table below, then use the `addCustomTransformation` function and pass the transformation code from ImageKit docs as the first parameter and value as the second parameter. For example - `.addCustomTransformation("w", "400")`


<details>
<summary>Expand</summary>

| Supported Transformation Name | Transformation Function Prototypes                                                                                                                                                                                                                                                                                                                   | Translates to parameter |
|-------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| height                        | height(height: Int)                                                                                                                                                                                                                                                                                                                                  | h                       |
| width                         | width(width: Int)                                                                                                                                                                                                                                                                                                                                    | w                       |
| aspectRatio                   | aspectRatio(width: Int, height: Int)                                                                                                                                                                                                                                                                                                                 | ar                      |
| quality                       | quality(quality: Int)                                                                                                                                                                                                                                                                                                                                | q                       |
| crop                          | crop(cropType: CropType)                                                                                                                                                                                                                                                                                                                             | c                       |
| cropMode                      | cropMode(cropMode: CropMode)                                                                                                                                                                                                                                                                                                                         | cm                      |
| x, y                          | focus(x: Int, y: Int)                                                                                                                                                                                                                                                                                                                                | x, y                    |
| focus                         | focus(focusType: FocusType)                                                                                                                                                                                                                                                                                                                          | fo                      |
| format                        | format(format: Format)                                                                                                                                                                                                                                                                                                                               | f                       |
| radius                        | radius(radius: Int)                                                                                                                                                                                                                                                                                                                                  | r                       |
| background                    | background(backgroundColor: String)                                                                                                                                                                                                                                                                                                                  | bg                      |
| border                        | border(borderWidth: Int, borderColor: String)                                                                                                                                                                                                                                                                                                        | b                       |
| rotation                      | rotation(rotation: Rotation)                                                                                                                                                                                                                                                                                                                         | rt                      |
| blur                          | blur(blur: Int)                                                                                                                                                                                                                                                                                                                                      | bl                      |
| named                         | named(namedTransformation: String)                                                                                                                                                                                                                                                                                                                   | n                       |
| overlayX                      | overlayX(overlayX: Int)                                                                                                                                                                                                                                                                                                                              | ox                      |
| overlayY                      | overlayY(overlayY: Int)                                                                                                                                                                                                                                                                                                                              | oy                      |
| overlayFocus                  | overlayFocus(overlayFocus: OverlayFocusType)                                                                                                                                                                                                                                                                                                         | ofo                     |
| overlayHeight                 | overlayHeight(overlayHeight: Int)                                                                                                                                                                                                                                                                                                                    | oh                      |
| overlayWidth                  | overlayWidth(overlayWidth: Int)                                                                                                                                                                                                                                                                                                                      | ow                      |
| overlayImage                  | overlayImage(overlayImage: String)                                                                                                                                                                                                                                                                                                                   | oi                      |
| overlayImageTrim              | overlayImageTrim(overlayImageTrim: Boolean)                                                                                                                                                                                                                                                                                                          | oit                     |
| overlayImageAspectRatio       | overlayImageAspectRatio(width: Int, height: Int)                                                                                                                                                                                                                                                                                                     | oiar                    |
| overlayImageBackground        | overlayImageBackground(overlayImageBackground: String)                                                                                                                                                                                                                                                                                               | oibg                    |
| overlayImageBorder            | overlayImageBorder(borderWidth: Int, borderColor: String)                                                                                                                                                                                                                                                                                            | oib                     |
| overlayImageDPR               | overlayImageDPR(dpr: Float)                                                                                                                                                                                                                                                                                                                          | oidpr                   |
| overlayImageQuality           | overlayImageQuality(quality: Int)                                                                                                                                                                                                                                                                                                                    | oiq                     |
| overlayImageCropping          | overlayImageCropping(cropMode: CropMode)                                                                                                                                                                                                                                                                                                             | oic                     |
| overlayText                   | overlayText(overlayText: String)                                                                                                                                                                                                                                                                                                                     | ot                      |
| overlayTextFontSize           | overlayTextFontSize(overlayTextFontSize: Int)                                                                                                                                                                                                                                                                                                        | ots                     |
| overlayTextFontFamily         | overlayTextFontFamily(overlayTextFontFamily: OverlayTextFont)                                                                                                                                                                                                                                                                                        | otf                     |
| overlayTextColor              | overlayTextColor(overlayTextColor: String)                                                                                                                                                                                                                                                                                                           | otc                     |
| overlayTextTransparency       | overlayTextTransparency(overlayTextTransparency: Int)                                                                                                                                                                                                                                                                                                | oa                      |
| overlayAlpha                  | overlayAlpha(overlayAlpha: Int)                                                                                                                                                                                                                                                                                                                      | oa                      |
| overlayTextTypography         | overlayTextTypography(overlayTextTypography: OverlayTextTypography)                                                                                                                                                                                                                                                                                  | ott                     |
| overlayBackground             | overlayBackground(overlayBackground: String)                                                                                                                                                                                                                                                                                                         | obg                     |
| overlayTextEncoded            | overlayTextEncoded(overlayTextEncoded: String)                                                                                                                                                                                                                                                                                                       | ote                     |
| overlayTextWidth              | overlayTextWidth(width: Int)                                                                                                                                                                                                                                                                                                                         | otw                     |
| overlayTextBackground         | overlayTextBackground(overlayTextColor: String)                                                                                                                                                                                                                                                                                                      | otbg                    |
| overlayTextPadding            | overlayTextPadding(overlayTextPadding: String)<br>overlayTextPadding(overlayTextPadding: Int)<br>overlayTextPadding(verticalPadding: Int, horizontalPadding: Int)<br>overlayTextPadding(topPadding: Int, horizontalPadding: Int, bottomPadding: Int)<br>overlayTextPadding(topPadding: Int, rightPadding: Int, bottomPadding: Int, leftPadding: Int) | otp                     |
| overlayTextInnerAlignment     | overlayTextInnerAlignment(overlayTextInnerAlignment: OverlayTextInnerAlignment)                                                                                                                                                                                                                                                                      | otia                    |
| overlayRadius                 | overlayRadius(radius: Int)                                                                                                                                                                                                                                                                                                                           | or                      |
| progressive                   | progressive(flag: Boolean)                                                                                                                                                                                                                                                                                                                           | pr                      |
| lossless                      | lossless(flag: Boolean)                                                                                                                                                                                                                                                                                                                              | lo                      |
| trim                          | trim(flag: Boolean)<br>trim(value: Int)                                                                                                                                                                                                                                                                                                              | t                       |
| metadata                      | metadata(flag: Boolean)                                                                                                                                                                                                                                                                                                                              | md                      |
| colorProfile                  | colorProfile(flag: Boolean)                                                                                                                                                                                                                                                                                                                          | cp                      |
| defaultImage                  | defaultImage(defaultImage: String)                                                                                                                                                                                                                                                                                                                   | di                      |
| dpr                           | dpr(dpr: Float)                                                                                                                                                                                                                                                                                                                                      | dpr                     |
| effectSharpen                 | effectSharpen(value: Int = 0)                                                                                                                                                                                                                                                                                                                        | e-sharpen               |
| effectUSM                     | effectUSM( radius: Float, sigma: Float, amount: Float, threshold: Float)                                                                                                                                                                                                                                                                             | e-usm                   |
| effectContrast                | effectContrast(flag: Boolean)                                                                                                                                                                                                                                                                                                                        | e-contrast              |
| effectGray                    | effectGray(flag: Boolean)                                                                                                                                                                                                                                                                                                                            | e-grayscale             |
| original                      | original(flag: Boolean)                                                                                                                                                                                                                                                                                                                              | orig                    |

</details>

## Constructing Video URLs
The `ImageKitURLConstructor` can also be used to create a url that can be used for streaming videos with real-time transformations. `ImageKitURLConstructor` consists of functions that can be chained together to perform transformations.

The initialization is same as that for image URLs by calling `ImageKit.getInstance().url(...)` with a set of parameters defined below.

### Basic Examples
```kotlin
// Kotlin
// https://ik.imagekit.io/your_imagekit_id/default-video.mp4?tr=h-400.00,w-400.00
ImageKit.getInstance()
    .url(
        path = "default-video.mp4",
        transformationPosition = TransformationPosition.QUERY
    )
    .height(400f)
    .width(400f)
    .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/default-video.mp4?tr=h-400.00,w-400.00
ImageKit.Companion.getInstance()
        .url(
            "default-video.mp4",
            TransformationPosition.QUERY
        )
        .height(400f)
        .width(400f)
        .create();
```

### Responsive image loading
To automatically set the dimensions and pixel ratio of the image , call `ImageKit.getInstance().url(...).setResponsive(...)` with a set of parameters defined below:

| Parameter | Type                                                              | Description                                                                                                      |
|:----------|:------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| view      | [View](https://developer.android.com/reference/android/view/View) | Specifies the reference of the view of which the dimensions are to be taken into consideration for image sizing. |

Code example:
```kotlin
// Kotlin
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,w-400.00
ImageKit.getInstance()
    .url(
        path = "default-image.jpg",
        transformationPosition = TransformationPosition.QUERY
    )
    .setResponsive(view = displayView)
    .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/default-image.jpg?tr=h-400.00,w-400.00
ImageKit.Companion.getInstance()
        .url(
            "default-image.jpg",
            TransformationPosition.QUERY
        )
        .setResponsive(displayView)
        .create();
```

### Adaptive bitrate streaming
To obtain the video URL with adaptive streaming, call `ImageKit.getInstance().url(...).setAdaptiveStreaming(...)` with a set of parameters defined below.

| Parameter   | Type            | Description                                                                                                               |
|:------------|:----------------|:--------------------------------------------------------------------------------------------------------------------------|
| format      | StreamingFormat | Specifies the format for streaming video. Supported values for type are `StreamingFormat.HLS` and `StreamingFormat.DASH`. |
| resolutions | List\<Int>      | Specifies the representations of the required video resolutions. E. g. 480, 720, 1080 etc.                                |

Code example:
```kotlin
// Kotlin
// https://ik.imagekit.io/your_imagekit_id/default-video.mp4?tr=h-400.00,w-400.00
ImageKit.getInstance()
    .url(
        path = "default-video.mp4",
        transformationPosition = TransformationPosition.QUERY
    )
    .setAdaptiveStreaming(
        format = StreamingFormat.HLS,
        resolutions = listOf(240, 360, 480, 720, 1080, 1440, 2160)
    )
    .create()
```

```java
// Java
// https://ik.imagekit.io/your_imagekit_id/default-video.mp4?tr=h-400.00,w-400.00
ImageKit.Companion.getInstance()
        .url(
            "default-video.mp4",
            TransformationPosition.QUERY
        )
        .setAdaptiveStreaming(StreamingFormat.HLS,
            Arrays.asList(240, 360, 480, 720, 1080, 1440, 2160)
        )
        .create();
```

### File Upload
The SDK provides a simple interface using the `ImageKit.getInstance().uploader().upload(...)` method to upload files to the ImageKit Media Library. It accepts all the parameters supported by the [ImageKit Upload API](https://docs.imagekit.io/api-reference/upload-file-api/client-side-file-upload#request-structure-multipart-form-data).

Make sure that you have specified `authenticationEndpoint` during SDK initialization. The SDK makes an HTTP GET request to this endpoint and expects a JSON response with three fields i.e. `signature`, `token`, and `expire`.  

[Learn how to implement authenticationEndpoint](https://docs.imagekit.io/api-reference/upload-file-api/client-side-file-upload#how-to-implement-authenticationendpoint-endpoint) on your server.

The `ImageKit.getInstance().uploader().upload(...)` accepts the following parameters

| Parameter         | Type                                                                                | Description                                                                                                                                                                                                                                                                                 |
|:------------------|:------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| file              | Binary / Bitmap / String                                                            | Required.                                                                                                                                                                                                                                                                                   |
| fileName          | String                                                                              | Required. If not specified, the file system name is picked.                                                                                                                                                                                                                                 |
| useUniqueFileName | Boolean                                                                             | Optional. Accepts `true` of `false`. The default value is `true`. Specify whether to use a unique filename for this file or not.                                                                                                                                                            |
| tags              | Array of string                                                                     | Optional. Set the tags while uploading the file e.g. ["tag1","tag2"]                                                                                                                                                                                                                        |
| folder            | String                                                                              | Optional. The folder path (e.g. `/images/folder/`) in which the file has to be uploaded. If the folder doesn't exist before, a new folder is created.                                                                                                                                       |
| isPrivateFile     | Boolean                                                                             | Optional. Accepts `true` of `false`. The default value is `false`. Specify whether to mark the file as private or not. This is only relevant for image type files                                                                                                                           |
| customCoordinates | String                                                                              | Optional. Define an important area in the image. This is only relevant for image type files. To be passed as a string with the `x` and `y` coordinates of the top-left corner, and `width` and `height` of the area of interest in format `x,y,width,height`. For example - `10,10,100,100` |
| responseFields    | Array of string                                                                     | Optional. Values of the fields that you want upload API to return in the response. For example, set the value of this field to `["tags", "customCoordinates", "isPrivateFile"]` to get value of `tags`, `customCoordinates`, and `isPrivateFile` in the response.                           |
| policy            | UploadPolicy                                                                        | Optional. Set the custom policy to override the default policy for this upload request only. This doesn't modify the default upload policy.                                                                                                                                                 |
| preprocess        | ImagePreprocess/VideoPreprocess                                                     | Optional. Set the set the parameters for preprocessing the image/video before uploads. This doesn't modify the default upload policy.                                                                                                                                                       |
| imageKitCallback  | [ImageKitCallback](imagekit/src/main/java/com/imagekit/android/ImageKitCallback.kt) | Required.                                                                                                                                                                                                                                                                                   |

Sample Usage
```kotlin
ImageKit.getInstance().uploader().upload(
    file = image,
    fileName = "sample-image.jpg",
    useUniqueFilename = true,
    tags = ["demo"],
    folder = "/",
    isPrivateFile =  false,
    customCoordinates = "",
    responseFields = "",
    policy = UploadPolicy.Builder()
        .requireNetworkType(UploadPolicy.UploadPolicy.NetworkType.UNMETERED)
        .setMaxRetries(5)
        .build(),
    preprocess = ImagePreprocess.Builder()
        .limit(512, 512)
        .rotate(90f)
        .build(),
    imageKitCallback = object: ImageKitCallback {
        override fun onSuccess(uploadResponse: UploadResponse) {
            // Handle Success Response
        }
        override fun onError(uploadError: UploadError) {
            // Handle Upload Error
        }
    }
)
```

### UploadPolicy
The `UploadPolicy` class represents a set of conditions that need to be met for an upload request to be executed.

`UploadPolicy.Builder` class is responsible for building the UploadPolicy instances. This class provides following methods to access and modify the policy parameters:

| Parameter                                          | Type                 | Description                                                                                                                                                                                                                      |
|:---------------------------------------------------|:---------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| requireNetworkType(type: NetworkType )             | UploadPolicy.Builder | Specifies the network type required for the upload request. Possible values are `UploadPolicy.NetworkPolicy.ANY` and `UploadPolicy.NetworkPolicy.UNMETERED`. Defaults to `NetworkPolicy.ANY`.                                    |
| requiresBatteryCharging(requiresCharging: Boolean) | UploadPolicy.Builder | Sets whether the device needs to be connected to a charger for the upload request. Defaults to `false`.                                                                                                                          |
| requiresDeviceIdle(requiresIdle: Boolean)          | UploadPolicy.Builder | Sets whether the device needs to be idle for the upload request. Defaults to `false`.                                                                                                                                            |
| setMaxRetries(count: Int)                          | UploadPolicy.Builder | Sets the maximum number of retries for the upload request. Negative value will throw an `IllegalArgumentException`. Defaults to 5.                                                                                               |
| setRetryBackoff(interval, BackoffPolicy policy)    | UploadPolicy.Builder | Sets the backoff interval in milliseconds and policy (`UploadPolicy.BackoffPolicy.LINEAR` or `UploadPolicy.BackoffPolicy.EXPONENTIAL`) for retry attempts. Defaults to interval of 10000ms and policy of `BackoffPolicy.LINEAR`. |

Example code
```kotlin
val policy = UploadPolicy.Builder()
    .requireNetworkType(UploadPolicy.NetworkType.UNMETERED)
    .requiresCharging(true)
    .requiresDeviceIdle(false)
    .setMaxRetries(5)
    .setRetryBackoff(60000L, UploadPolicy.BackoffPolicy.EXPONENTIAL)
    .build()
```

## Upload preprocessors
### ImagePreprocess
The `ImagePreprocess` class encapsulates a set of methods to apply certain transformations to an image before uploading.

`ImagePreprocess.Builder` class is responsible for building the ImagePreprocess instances. This class provides following methods to access and modify the policy parameters:

| Parameter                                                                                                               | Type                    | Description                                                              |
|:------------------------------------------------------------------------------------------------------------------------|:------------------------|:-------------------------------------------------------------------------|
| limit(width: Int, height: Int)                                                                                          | ImagePreprocess.Builder | Specifies the maximum width and height of the image                      |
| crop(p1: Point, p2: Point)                                                                                              | ImagePreprocess.Builder | Specifies the two points on the diagonal of the rectangle to be cropped. |
| format(format: [Bitmap.CompressFormat](https://developer.android.com/reference/android/graphics/Bitmap.CompressFormat)) | ImagePreprocess.Builder | Specify the target image format.                                         |
| rotate(degrees: Float)                                                                                                  | ImagePreprocess.Builder | Specify the rotation angle of the target image.                          |

Example code
```kotlin
val preprocess = ImagePreprocess.Builder()
    .limit(1280, 720)
    .format(Bitmap.CompressFormat.WEBP)
    .rotate(45f)
    .build()
```
### VideoPreprocess
The `VideoPreprocess` class encapsulates a set of methods to apply certain transformations to an image before uploading.

`VideoPreprocess.Builder` class is responsible for building the VideoPreprocess instances. This class provides following methods to access and modify the policy parameters:

| Parameter                            | Type                    | Description                                          |
|:-------------------------------------|:------------------------|:-----------------------------------------------------|
| limit(width: Int, height: Int)       | VideoPreprocess.Builder | Specifies the maximum width and height of the video. |
| frameRate(frameRateValue: Int)       | VideoPreprocess.Builder | Specifies the target frame rate of the video.        |
| keyFramesInterval(interval: Int)     | VideoPreprocess.Builder | Specify the target keyframes interval of video.      |
| targetAudioBitrateKBps(bitrate: Int) | VideoPreprocess.Builder | Specify the target audio bitrate of the video.       |
| targetVideoBitrateKBps(bitrate: Int) | VideoPreprocess.Builder | Specify the target video bitrate of the video.       |

Example code
```kotlin
val preprocess = VideoPreprocess.Builder()
    .frameRate(90)      
    .targetAudioBitrateKBps(320)
    .targetVideoBitrateKBps(480)
    .build()
```

## Support
For any feedback or to report any issues or general implementation support please reach out to [support@imagekit.io](mailto:support@imagekit.io)

## Links
* [Documentation](https://docs.imagekit.io)
* [Main website](https://imagekit.io)

## License

Released under the MIT license.
