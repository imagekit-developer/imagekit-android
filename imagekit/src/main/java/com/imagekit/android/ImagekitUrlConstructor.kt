package com.imagekit.android

import android.content.Context
import com.imagekit.android.entity.*
import com.imagekit.android.injection.component.DaggerUtilComponent
import com.imagekit.android.injection.module.ContextModule
import com.imagekit.android.util.LogUtil.logError
import com.imagekit.android.util.TranformationMapping
import com.imagekit.android.util.TranformationMapping.overlayTransparency
import java.lang.Math.abs
import java.util.*
import java.util.regex.Pattern

@Suppress("unused")
class ImagekitUrlConstructor constructor(
    private val context: Context,
    private var source: String,
    private var transformationPosition: TransformationPosition
) {
    private val transformationList: MutableList<String> = ArrayList()
    private val transformationMap = HashMap<String, Any>()
    private var path: String? = null
    private var isSource: Boolean = true

    constructor(
        context: Context,
        urlEndpoint: String,
        path: String,
        transformationPosition: TransformationPosition
    ) : this(context, urlEndpoint, transformationPosition) {
        this.path = path
        isSource = false
    }

    init {
        ImageKit.getInstance()
        val appComponent = DaggerUtilComponent.builder()
            .contextModule(ContextModule(context))
            .build()

        appComponent
            .inject(this)
    }

    /**
     * Method to specify the width of the output image.
     * @param width Accepts integer value greater than 1 and if a value between 0 and 1 is specified, then it acts as a percentage width.
     * For eg, 0.1 means 10% of the original width, 0.2 means 20% of the original width.
     * @return the current ImagekitUrlConstructor object.
     */
    fun width(width: Float): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.width] = width
        transformationList.add(String.format("%s-%.2f", TranformationMapping.width, width))
        return this
    }

    /**
     * Method to specify the height of the output image.
     * @param height Accepts integer value greater than 1 and if a value between 0 and 1 is specified, then it acts as a percentage height.
     * For eg, 0.1 means 10% of the original height, 0.2 means 20% of the original height.
     * @return the current ImagekitUrlConstructor object.
     */
    fun height(height: Float): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.height] = height
        transformationList.add(String.format("%s-%.2f", TranformationMapping.height, height))
        return this
    }

    /**
     * Method to specify the aspect ratio of the output image or the ratio of width to height of the output image.
     * This transform must be used along with either the height or the width transform.
     * @param width Accepts integer value greater than equal to 1
     * @param height Accepts integer value greater than equal to 1
     * @return the current ImagekitUrlConstructor object.
     */
    fun aspectRatio(width: Int, height: Int): ImagekitUrlConstructor {
        val s = String.format("%s-%d-%d", TranformationMapping.aspectRatio, width, height)
        transformationMap[TranformationMapping.aspectRatio] = s
        transformationList.add(s)
        return this
    }

    /**
     * Method to decide the final value of height and width of the output image based on the aspect ratio of the input
     * image and the requested transform.
     * @param cropType Accepts value of type CropType. Possible values include maintain_ratio, force, at_least and at_max.
     * Default value - maintain_ratio
     * @see CropType
     * @return the current ImagekitUrlConstructor object.
     */
    fun crop(cropType: CropType): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.crop] = cropType
        transformationList.add(String.format("%s-%s", TranformationMapping.crop, cropType.value))
        return this
    }

    /**
     * Method used to specify the strategy of how the input image is used for cropping to get the output image.
     * @param cropMode Accepts value of type CropMode. Possible values include resize, extract, pad_extract and pad_resize.
     * Default value - resize
     * @see CropMode
     * @return the current ImagekitUrlConstructor object.
     */
    fun cropMode(cropMode: CropMode): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.cropMode] = cropMode
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.cropMode,
                cropMode.value
            )
        )
        return this
    }

    /**
     * Method used to specify the focus which is coupled with the extract type of crop mode (crop mode is not needed
     * if you are using auto focus) to get the area of the input image that should be focussed on to get the output image.
     * @param focusType Accepts value of type FocusType. Possible values include center, top, left, bottom, right,
     * top_left, top_right, bottom_left, bottom_right and auto.
     * Default value - center
     * @see FocusType
     * @return the current ImagekitUrlConstructor object.
     */
    fun focus(focusType: FocusType): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.focus] = focusType
        transformationList.add(String.format("%s-%s", TranformationMapping.focus, focusType.value))
        return this
    }

    /**
     * Method to specify the output quality of the lossy formats like JPG and WebP. A higher quality number means a
     * larger size of the output image with high quality. A smaller number means low quality image at a smaller size.
     * @param quality Accepts integer value between 1 and 100.
     * Default value is picked from the dashboard settings. It is set to 80.
     * @return the current ImagekitUrlConstructor object.
     */
    fun quality(quality: Int): ImagekitUrlConstructor {
        if (quality < 1 || quality > 100)
            logError(context.getString(R.string.error_transform_value_out_of_range))

        transformationMap[TranformationMapping.quality] = quality
        transformationList.add(String.format("%s-%d", TranformationMapping.quality, quality))
        return this
    }

    /**
     * Method used to specify the format of the output image. If no output format is specified and
     * the “Dynamic image format selection” option is selected in your dashboard settings, then the output format is
     * decided on the basis of the user’s device capabilities and input image format. If dynamic image format selction
     * is switched off, and no output format is specified then the format of the output image is same as that of the input image.
     * @param format Accepts value of type FocusType. Possible values include auto, webp, jpg, jpeg and pnt.
     * Default value - auto
     * @see Format
     * @return the current ImagekitUrlConstructor object.
     */
    fun format(format: Format): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.format] = format
        transformationList.add(String.format("%s-%s", TranformationMapping.format, format.value))
        return this
    }

    /**
     * Method to specify the Gaussian blur that has to be applied to the image. The value of blur decides the radius of
     * the Gaussian blur that is applied. Higher the value, higher is the radius of Gaussian blur.
     * @param blur Accepts integer value between 1 and 100.
     * @return the current ImagekitUrlConstructor object.
     */
    fun blur(blur: Int): ImagekitUrlConstructor {
        if (blur < 1 || blur > 100)
            logError(context.getString(R.string.error_transform_value_out_of_range))

        transformationMap[TranformationMapping.blur] = blur
        transformationList.add(String.format("%s-%d", TranformationMapping.blur, blur))
        return this
    }

    /**
     * Method to turn an image into its grayscale version.
     * @param flag Accepts boolean value of either true or false. Default value is false.
     * @return the current ImagekitUrlConstructor object.
     */
    fun effectGray(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.eGrayscale] = flag

        if (flag)
            transformationList.add(String.format("%s", TranformationMapping.eGrayscale))

        return this
    }

    /**
     * Method to specify the device pixel ratio to be used to calculate the dimension of the output image. It is useful
     * when creating image transformations for devices with high density screens (DPR greater than 1) like the iPhone.
     * The DPR option works only when either the height or the width or both are specified for resizing the image
     * If the resulting height or width after considering the specified DPR value is less than 1px or more than 5000px
     * then the value of DPR is not considered and the normal height or width specified in the transformation string is used.
     * @param dpr Possible values: 0.1 to 5.0
     * @return the current ImagekitUrlConstructor object.
     */
    fun dpr(dpr: Float): ImagekitUrlConstructor {
        if (dpr < 0.1 || dpr > 5)
            logError(context.getString(R.string.error_transform_value_out_of_range))

        transformationMap[TranformationMapping.dpr] = dpr
        transformationList.add(String.format("%s-%f", TranformationMapping.dpr, dpr))
        return this
    }

    /**
     * Method to specify the Named transformations which is an alias for the entire transformation string.
     * E.g we can create a named transform media_library_thumbnail for transformation string tr:w-150,h-150,f-center,c-at_max
     * @param namedTransformation
     * @return the current ImagekitUrlConstructor object.
     */
    fun named(namedTransformation: String): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.namedTransformation] = namedTransformation
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.namedTransformation,
                namedTransformation
            )
        )
        return this
    }

    /**
     * Method to specify the default image which is delivered in case the image that is requested using ImageKit does not exist.
     * @param defaultImage
     * @return the current ImagekitUrlConstructor object.
     */
    fun defaultImage(defaultImage: String): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.defaultImage] = defaultImage
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.defaultImage,
                defaultImage
            )
        )
        return this
    }

    /**
     * Method to specify if the output JPEG image should be rendered progressively. In progressive rendering,
     * the client instead of downloading the image row-wise (baseline loading), renders a low-quality pixelated
     * full image and then gradually keeps on adding more pixels and information to the image. It gives faster-perceived load times.
     * @param flag Possible values include true and false. Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    fun progressive(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.progressiveJPEG] = flag
        transformationList.add(String.format("%s-%b", TranformationMapping.progressiveJPEG, flag))
        return this
    }

    /**
     * Method to specify if the output image (if in PNG or WebP format) should be compressed losslessly.
     * In lossless compression, the output file size would be larger than the regular lossy compression but at the same time,
     * the perceived quality can be better in certain cases, especially for computer generated graphics.
     * Using lossless compression is not recommended for photographs.
     * @param flag Possible values include true and false. Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    fun lossless(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.lossless] = flag
        transformationList.add(String.format("%s-%b", TranformationMapping.lossless, flag))
        return this
    }

    /**
     * Method to specify if the redundant pixels of the original image need to be removed. It uses a default logic
     * to identify the redundant surrounding region and removes it. This transformation is useful for images that have
     * a solid / nearly-solid background and the object in the center. This transformation will trim
     * the background from the edges, leaving only the central object in the picture.
     * @param flag Possible values include true and false.
     * @return the current ImagekitUrlConstructor object.
     * @see trim
     */
    fun trim(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.trimEdges] = flag
        transformationList.add(String.format("%s-%b", TranformationMapping.trimEdges, flag))
        return this
    }

    /**
     * Method to specify the number of redundant pixels of the original image that need to be removed.
     * This transformation is useful for images that have a solid / nearly-solid background and the object in the center.
     * This transformation will trim the background from the edges, leaving only the central object in the picture.
     * @param value Number of pixels from the edge that need to be removed across all four sides.
     * @return the current ImagekitUrlConstructor object.
     * @see trim
     */
    fun trim(value: Int): ImagekitUrlConstructor {
        if (value < 1 || value > 99)
            logError(context.getString(R.string.error_transform_value_out_of_range))

        transformationMap[TranformationMapping.trimEdges] = value
        transformationList.add(String.format("%s-%d", TranformationMapping.trimEdges, value))
        return this
    }

    /**
     * Method to specify an image to overlay over another image. This will help you generate watermarked images if needed.
     * @param overlayImage
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayImage(overlayImage: String): ImagekitUrlConstructor {
        val formattedOverlayImage = overlayImage.replace("/", "@@")
        transformationMap[TranformationMapping.overlayImage] = formattedOverlayImage
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayImage,
                formattedOverlayImage
            )
        )
        return this
    }

    /**
     * Method used to specify the position of the overlaid image relative to the input image.
     * @param overlayFocus Possible values include center, top, left, bottom, right, top_left, top_right, bottom_left and bottom_right.
     * Default value - center
     * @see OverlayFocusType
     * @return the current ImagekitUrlConstructor object.
     * using either overlayPosX() or overlayPosY()
     */
    fun overlayFocus(overlayFocus: OverlayFocusType): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayFocus] = overlayFocus
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayFocus,
                overlayFocus.value
            )
        )
        return this
    }

    /**
     * Method used to provide more granular control over the positioning of the overlay image on the input image.
     * The top left corner of the input image is considered as (0,0) and the bottom right corner is considered as (w, h)
     * where w is the width and h is the height of the input image.
     * Negative values are supported with a leading capital N in front of the value provided. The value provided
     * is subtracted from the original dimension of the image & positioned accordingly.
     * @param overlayX Possible values include all integers.
     * @return the current ImagekitUrlConstructor object.
     * or the overlay focus has already been applied
     */
    fun overlayX(overlayX: Int): ImagekitUrlConstructor {
        val s = if(overlayX < 0)
            String.format("%s-N%s", TranformationMapping.overlayX, abs(overlayX))
        else
            String.format("%s-%d", TranformationMapping.overlayY, overlayX)

        transformationMap[TranformationMapping.overlayX] = s
        transformationList.add(s)
        return this
    }

    /**
     * Method used to provide more granular control over the positioning of the overlay image on the input image.
     * The top left corner of the input image is considered as (0,0) and the bottom right corner is considered as (w, h)
     * where w is the width and h is the height of the input image.
     * Negative values are supported with a leading capital N in front of the value provided. The value provided
     * is subtracted from the original dimension of the image & positioned accordingly.
     * @param overlayY Possible values include all integers.
     * @return the current ImagekitUrlConstructor object.
     * or the overlay focus has already been applied
     */
    fun overlayY(overlayY: Int): ImagekitUrlConstructor {
        val s = if(overlayY < 0)
            String.format("%s-N%s", TranformationMapping.overlayY, abs(overlayY))
        else
            String.format("%s-%d", TranformationMapping.overlayY, overlayY)

        transformationMap[TranformationMapping.overlayY] = s
        transformationList.add(s)
        return this
    }

    /**
     * Method used to specify the width of the overlaid image.
     * @param overlayWidth
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayWidth(overlayWidth: Float): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayWidth] = overlayWidth
        transformationList.add(
            String.format(
                "%s-%f",
                TranformationMapping.overlayWidth,
                overlayWidth
            )
        )
        return this
    }

    /**
     * Method used to specify the height of the overlaid image.
     * @param overlayHeight
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayHeight(overlayHeight: Float): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayHeight] = overlayHeight
        transformationList.add(
            String.format(
                "%s-%f",
                TranformationMapping.overlayHeight,
                overlayHeight
            )
        )
        return this
    }

    /**
     * Method used to overlay text over an image. Our current support is limited to alphanumberic & special characters _ & - only.
     * @param overlayText
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayText(overlayText: String): ImagekitUrlConstructor {
        val regex = Regex("[\\w\\s-]+")
        if (!regex.matches(overlayText))
            logError(context.getString(R.string.error_transform_value_invalid))

        transformationMap[TranformationMapping.overlayText] = overlayText
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayText,
                overlayText
            )
        )
        return this
    }

    /**
     * Method used to specify the color of the overlaid text on the image.
     * @param overlayTextColor Possible value is a valid valid RGB Hex Code
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayTextColor(overlayTextColor: String): ImagekitUrlConstructor {
        if (!Pattern.matches("[A-Fa-f0-9]+", overlayTextColor))
            logError(context.getString(R.string.error_transform_value_invalid))

        transformationMap[TranformationMapping.overlayTextColor] =
            overlayTextColor.toUpperCase(Locale.getDefault())
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayTextColor,
                overlayTextColor.toUpperCase(Locale.getDefault())
            )
        )
        return this
    }

    /**
     * Method used to specify the font family for the overlaid text.
     * @param overlayTextFontFamily
     * @return the current ImagekitUrlConstructor object.
     * @see overlayTextFontFamily
     */
    fun overlayTextFontFamily(overlayTextFontFamily: OverlayTextFont): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayTextFont] = overlayTextFontFamily
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayFocus,
                overlayTextFontFamily.value
            )
        )
        return this
    }

    /**
     * Method used to specify the size of the overlaid text.
     * @param overlayTextFontSize Possible values include any integer. Default value - 14px
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayTextFontSize(overlayTextFontSize: Int): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayTextSize] = overlayTextFontSize
        transformationList.add(
            String.format(
                "%s-%d",
                TranformationMapping.overlayTextSize,
                overlayTextFontSize
            )
        )
        return this
    }

    /**
     * Method used to specify the typography of the font family used for the overlaid text. Possible values include bold b and italics i.
     * Note Bold & Italics are not supported for all provided fonts.
     * @param overlayTextTypography
     * @return the current ImagekitUrlConstructor object
     * @see OverlayTextTypography
     */
    fun overlayTextTypography(overlayTextTypography: OverlayTextTypography): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.overlayTextTypography] = overlayTextTypography
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayTextTypography,
                overlayTextTypography.value
            )
        )
        return this
    }

    /**
     * Method used to specify the colour of background canvas to be overlaid. Possible values include a valid RGB Hex code.
     * @param overlayBackground
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayBackground(overlayBackground: String): ImagekitUrlConstructor {
        if (!Pattern.matches("[A-Fa-f0-9]+", overlayBackground))
            logError(context.getString(R.string.error_transform_value_invalid))

        transformationMap[TranformationMapping.overlayBackground] =
            overlayBackground.toUpperCase(Locale.getDefault())
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.overlayBackground,
                overlayBackground.toUpperCase(Locale.getDefault())
            )
        )
        return this
    }

    /**
     * Method used to specify the transparency level for the overlaid image.
     * Note Overlay transparency is currently supported for overlay texts & backgrounds only.
     * @param overlayAlpha Possible values include integer from 1 to 9.
     * @return the current ImagekitUrlConstructor object.
     */
    fun overlayAlpha(overlayAlpha: Int): ImagekitUrlConstructor {
        if (overlayAlpha !in 1..9)
            logError(context.getString(R.string.error_transform_value_out_of_range))

        transformationMap[overlayTransparency] = overlayAlpha
        transformationList.add(
            String.format(
                "%s-%d",
                overlayTransparency,
                overlayAlpha
            )
        )
        return this
    }

    /**
     * Method used to specify if the output image should contain the color profile that is initially available
     * from the original image. It is recommended to remove the color profile before serving the image on web and apps.
     * However, in cases where you feel that the image looks faded or washed-out after using ImageKit and want to preserve
     * the colors in your image, then you should set this option to true. Possible values include true and false.
     * @param flag Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    fun colorProfile(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.colorProfile] = flag
        transformationList.add(String.format("%s-%b", TranformationMapping.colorProfile, flag))
        return this
    }

    /**
     * Method used to specify if the output image should contain all the metadata that is initially available from
     * the original image. Enabling this is not recommended because this metadata is not relevant for rendering on the
     * web and mobile apps. The only reason where you should enable the metadata option is when you have knowingly wanted
     * the additional data like camera information, lens information and other image profiles attached to your image.
     * Possible values include true and false.
     * @param flag Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    fun metadata(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.imageMetadata] = flag
        transformationList.add(String.format("%s-%b", TranformationMapping.imageMetadata, flag))
        return this
    }

    /**
     * Method used to specify the degrees by which the output image has to be rotated or specifies the use of
     * EXIF Orientation tag for the rotation of the image using auto.
     * @param rotation Possible values include 0, 90, 180, 270, 360 and auto.
     * Default value - center
     * @see Rotation
     * @return the current ImagekitUrlConstructor object.
     */
    fun rotation(rotation: Rotation): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.rotate] = rotation
        transformationList.add(String.format("%s-%s", TranformationMapping.rotate, rotation.value))
        return this
    }

    /**
     * Method used to specify the radius to be used to get a rounded corner image.
     * This option is applied after resizing of the image, if any.
     * @param radius Possible values include positive integer.
     * @return the current ImagekitUrlConstructor object.
     */
    fun radius(radius: Int): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.radius] = radius
        transformationList.add(String.format("%s-%d", TranformationMapping.radius, radius))
        return this
    }

    /**
     * Method used to get a perfectly rounded image.
     * This option is applied after resizing of the image, if any.
     * @return the current ImagekitUrlConstructor object.
     */
    fun round(): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.radius] = "max"
        transformationList.add(String.format("%s-%s", TranformationMapping.radius, "max"))
        return this
    }

    /**
     * Method used to specify the background color as RGB hex code (e.g. FF0000) or an RGBA code (e.g. FFAABB50)
     * to be used for the image.
     * @param backgroundColor Default value - Black 000000
     * @return the current ImagekitUrlConstructor object.
     */
    fun background(backgroundColor: String): ImagekitUrlConstructor {
        if (!Pattern.matches("[A-Fa-f0-9]+", backgroundColor) && !Pattern.matches("[A-Fa-f0-9]{6}[0-9]{2}+", backgroundColor))
            logError(context.getString(R.string.error_transform_value_invalid))

        transformationMap[TranformationMapping.backgroundColor] =
            backgroundColor.toUpperCase(Locale.getDefault())
        transformationList.add(
            String.format(
                "%s-%s",
                TranformationMapping.backgroundColor,
                backgroundColor.toUpperCase(Locale.getDefault())
            )
        )
        return this
    }

    /**
     * Method used to specify the width and color of the border that is added around the image.
     * The width is a positive integer that specifies the border width in pixels.
     * The border color is specified as a standard RGB hex code e.g b-{width}_{color}
     * @param borderWidth width of the border
     * @param borderColor color of the border as RGB hex code
     * @return the current ImagekitUrlConstructor object.
     */
    fun border(borderWidth: Int, borderColor: String): ImagekitUrlConstructor {
        if (!Pattern.matches("[A-Fa-f0-9]+", borderColor))
            logError(context.getString(R.string.error_transform_value_invalid))

        val s = String.format(
            "%s-%d_%s",
            TranformationMapping.border,
            borderWidth,
            borderColor.toUpperCase(Locale.getDefault())
        )
        transformationMap[TranformationMapping.border] = s
        transformationList.add(s)
        return this
    }

    /**
     * Method used to automatically enhance the contrast of the image by using the full range of intensity values
     * that a particular image format allows. This basically means that the lighter portions of the image would become
     * even lighter and the darker portions of the image would become even brighter, thereby enhancing the contrast.
     * @param flag
     * @return the current ImagekitUrlConstructor object.
     */
    fun effectContrast(flag: Boolean): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.contrastStretch] = flag

        if (flag)
            transformationList.add(String.format("%s", TranformationMapping.contrastStretch))

        return this
    }

    /**
     * Method sharpens the input image. It is useful to highlight the edges and finer details in the image.
     * If just e-sharpen is used, then a default sharpening is performed on the image. This behavior can be controlled
     * by specifying a number that controls the extent of sharpening performed - higher the number,
     * more the sharpening
     * @param value
     * @return the current ImagekitUrlConstructor object.
     */
    fun effectSharpen(value: Int = 0): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.sharpen] = value
        transformationList.add(String.format("%s-%d", TranformationMapping.sharpen, value))
        return this
    }

    /**
     * Unsharp masking (USM) is an image sharpening technique.
     * Method allows you to apply and control unsharp mask on your images. The amount of sharpening can be controlled
     * by varying the 4 parameters - radius, sigma, amount and threshold. This results in perceptually better images
     * compared to just e-sharpen.
     * @param radius Possible values include positive floating point values.
     * @param sigma Possible values include positive floating point values.
     * @param amount Possible values include positive floating point values.
     * @param threshold Possible values include positive floating point values.
     * @return the current ImagekitUrlConstructor object.
     */
    fun effectUSM(
        radius: Float,
        sigma: Float,
        amount: Float,
        threshold: Float
    ): ImagekitUrlConstructor {
        val s = String.format(
            "%s-%f-%f-%f-%f",
            TranformationMapping.unsharpMask,
            radius,
            sigma,
            amount,
            threshold
        )
        transformationMap[TranformationMapping.unsharpMask] = s
        transformationList.add(s)
        return this
    }

    /**
     * Some transformations are dependent on the sequence in which they are carried out.
     * Method used to add sequence dependent steps in a transform chain to obtain predictable results.
     * @return the current ImagekitUrlConstructor object.
     */
    fun chainTransformation(): ImagekitUrlConstructor {
        transformationMap[TranformationMapping.transformationStep] = ":"
        transformationList.add(":")

        return this
    }

    /**
     * Method allows adding custom transformations to the image.
     * @return the current ImagekitUrlConstructor object.
     */
    fun addCustomTransformation(key: String, value: String): ImagekitUrlConstructor {
        transformationMap[key] = value
        transformationList.add(String.format("%s-%s", key, value))
        return this
    }

    /**
     * Used to create the url using the transformations specified before invoking this method.
     * @return the Url used to fetch an image after applying the specified transformations.
     */
    fun create(): String {
        try {
            var url = source

            if (isSource) {
                if (transformationList.isNotEmpty()) {
                    transformationPosition = TransformationPosition.QUERY
                    if (url.contains("?tr=")) {
                        url = url.substring(0, url.indexOf("?tr="))
                    } else if (url.contains("/tr:")) {
                        url = url.replace(
                            url.substring(url.indexOf("/tr:"), url.lastIndexOf("/")),
                            ""
                        )
                    }

                    url = addQueryParams(url)
                }
            } else if (transformationList.isNotEmpty()) {
                url = when (transformationPosition) {
                    TransformationPosition.PATH -> String.format("%s/%s?sdk-version=android-${BuildConfig.API_VERSION}", addPathParams(url), path)
                    TransformationPosition.QUERY -> addQueryParams(
                        String.format(
                            "%s/%s",
                            url,
                            path
                        )
                    )
                }
            }

            return url
        } catch (e: Exception) {
            e.printStackTrace()
            return context.getString(R.string.error_url_construction_error)
        }
    }

    private fun addPathParams(endpoint: String): String {

        var url = String.format("%s/tr:", endpoint)
        for (t in 0 until transformationList.size) {
            url = when {
                transformationList[t].contentEquals(":") -> String.format(
                    "%s%s",
                    url,
                    transformationList[t]
                )
                url.endsWith(":") -> String.format("%s%s", url, transformationList[t])
                else -> String.format("%s,%s", url, transformationList[t])
            }
        }

        return url
    }

    private fun addQueryParams(endpoint: String): String {
        var url = String.format("%s?sdk-version=android-${BuildConfig.API_VERSION}&tr=", endpoint)
        for (t in 0 until transformationList.size) {
            url = when {
                transformationList[t].contentEquals(":") -> String.format(
                    "%s%s",
                    url,
                    transformationList[t]
                )
                url.endsWith(":") -> String.format("%s%s", url, transformationList[t])
                url.endsWith("=") -> String.format("%s%s", url, transformationList[t])
                else -> String.format("%s,%s", url, transformationList[t])
            }
        }

        return url
    }
}