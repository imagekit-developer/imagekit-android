package com.imagekit.android;

import java.lang.System;

@kotlin.Suppress(names = {"unused"})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\n\u001a\u00020\u0000J&\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\rJ\u0016\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0003J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0013J\u0016\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0003J\u0006\u0010\u001b\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0003J\u000e\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\rJ\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&J\u000e\u0010\'\u001a\u00020\u00002\u0006\u0010\'\u001a\u00020(J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\rJ\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010-\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010/\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\u0003J\u000e\u00100\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0003J\u000e\u00101\u001a\u00020\u00002\u0006\u00101\u001a\u000202J\u000e\u00103\u001a\u00020\u00002\u0006\u00103\u001a\u00020\rJ\u000e\u00104\u001a\u00020\u00002\u0006\u00104\u001a\u00020\u0003J\u000e\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u0013J\u000e\u00106\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u0013J\u000e\u00107\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u0013J\u000e\u00108\u001a\u00020\u00002\u0006\u00108\u001a\u00020\u0013J\u000e\u00109\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u0003J\u000e\u0010:\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u0003J\u000e\u0010;\u001a\u00020\u00002\u0006\u0010;\u001a\u00020&J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010<\u001a\u00020\u0013J\u000e\u0010=\u001a\u00020\u00002\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020\u00002\u0006\u0010?\u001a\u00020\u0013J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010@\u001a\u00020\rJ\u000e\u0010A\u001a\u00020\u00002\u0006\u0010A\u001a\u00020\u0013J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0013J\u000e\u0010B\u001a\u00020\u00002\u0006\u0010C\u001a\u00020DJ\u0006\u0010E\u001a\u00020\u0000J\u0010\u0010F\u001a\u00020\u00002\b\b\u0002\u0010G\u001a\u00020\u0013J\u000e\u0010H\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010I\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+J\u000e\u0010J\u001a\u00020\u00002\u0006\u0010G\u001a\u00020\u0013J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006K"}, d2 = {"Lcom/imagekit/android/ImagekitUrlConstructor;", "", "endpoint", "", "imagePath", "(Ljava/lang/String;Ljava/lang/String;)V", "transformationList", "", "transformationMap", "Ljava/util/HashMap;", "addTransformationStep", "applyUnsharpMask", "radius", "", "sigma", "amount", "threshold", "aspectRatio", "width", "", "height", "backgroundHexColor", "backgroundRGBAColor", "blur", "border", "borderWidth", "borderColor", "create", "crop", "cropType", "Lcom/imagekit/android/entity/CropType;", "cropMode", "Lcom/imagekit/android/entity/CropMode;", "defaultImage", "devicePixelRatio", "dpr", "focus", "focusType", "Lcom/imagekit/android/entity/FocusType;", "format", "Lcom/imagekit/android/entity/Format;", "includeColorProfile", "flag", "", "includeImageMetadata", "isLossless", "isProgressiveJPEG", "namedTransformation", "overlayBackgroundColor", "overlayFocus", "Lcom/imagekit/android/entity/OverlayFocusType;", "overlayHeight", "overlayImage", "overlayNegX", "overlayNegY", "overlayPosX", "overlayPosY", "overlayText", "overlayTextColor", "overlayTextFont", "overlayTextSize", "overlayTextTypography", "Lcom/imagekit/android/entity/OverlayTextTypography;", "overlayTransparency", "overlayWidth", "quality", "rotate", "rotation", "Lcom/imagekit/android/entity/Rotation;", "round", "sharpen", "value", "showInGrayscale", "stretchContrast", "trimEdges", "imagekit_release"})
public final class ImagekitUrlConstructor {
    private final java.util.List<java.lang.String> transformationList = null;
    private final java.util.HashMap<java.lang.String, java.lang.Object> transformationMap = null;
    private final java.lang.String endpoint = null;
    private final java.lang.String imagePath = null;
    
    /**
     * Method to specify the width of the output image.
     * @param width Accepts integer value greater than 1 and if a value between 0 and 1 is specified, then it acts as a percentage width.
     * For eg, 0.1 means 10% of the original width, 0.2 means 20% of the original width.
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor width(float width) {
        return null;
    }
    
    /**
     * Method to specify the height of the output image.
     * @param height Accepts integer value greater than 1 and if a value between 0 and 1 is specified, then it acts as a percentage height.
     * For eg, 0.1 means 10% of the original height, 0.2 means 20% of the original height.
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor height(float height) {
        return null;
    }
    
    /**
     * Method to specify the aspect ratio of the output image or the ratio of width to height of the output image.
     * This transform must be used along with either the height or the width transform.
     * @param width Accepts integer value greater than equal to 1
     * @param height Accepts integer value greater than equal to 1
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if both width and height are not provided.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor aspectRatio(int width, int height) {
        return null;
    }
    
    /**
     * Method to decide the final value of height and width of the output image based on the aspect ratio of the input
     * image and the requested transform.
     * @param cropType Accepts value of type CropType. Possible values include maintain_ratio, force, at_least and at_max.
     * Default value - maintain_ratio
     * @see CropType
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor crop(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.CropType cropType) {
        return null;
    }
    
    /**
     * Method used to specify the strategy of how the input image is used for cropping to get the output image.
     * @param cropMode Accepts value of type CropMode. Possible values include resize, extract, pad_extract and pad_resize.
     * Default value - resize
     * @see CropMode
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor cropMode(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.CropMode cropMode) {
        return null;
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
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor focus(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.FocusType focusType) {
        return null;
    }
    
    /**
     * Method to specify the output quality of the lossy formats like JPG and WebP. A higher quality number means a
     * larger size of the output image with high quality. A smaller number means low quality image at a smaller size.
     * @param quality Accepts integer value between 1 and 100.
     * Default value is picked from the dashboard settings. It is set to 80.
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if quality is outside the 1 to 100 range.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor quality(int quality) {
        return null;
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
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor format(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.Format format) {
        return null;
    }
    
    /**
     * Method to specify the Gaussian blur that has to be applied to the image. The value of blur decides the radius of
     * the Gaussian blur that is applied. Higher the value, higher is the radius of Gaussian blur.
     * @param blur Accepts integer value between 1 and 100.
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if quality is outside the 1 to 100 range.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor blur(int blur) {
        return null;
    }
    
    /**
     * Method to turn an image into its grayscale version.
     * @param flag Accepts boolean value of either true or false. Default value is false.
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if quality is outside the 1 to 100 range.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor showInGrayscale(boolean flag) {
        return null;
    }
    
    /**
     * Method to specify the device pixel ratio to be used to calculate the dimension of the output image. It is useful
     * when creating image transformations for devices with high density screens (DPR greater than 1) like the iPhone.
     * The DPR option works only when either the height or the width or both are specified for resizing the image
     * If the resulting height or width after considering the specified DPR value is less than 1px or more than 5000px
     * then the value of DPR is not considered and the normal height or width specified in the transformation string is used.
     * @param dpr Possible values: 0.1 to 5.0
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if quality is outside the 0.1 to 5.0 range.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor devicePixelRatio(float dpr) {
        return null;
    }
    
    /**
     * Method to specify the Named transformations which is an alias for the entire transformation string.
     * E.g we can create a named transform media_library_thumbnail for transformation string tr:w-150,h-150,f-center,c-at_max
     * @param namedTransformation
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor namedTransformation(@org.jetbrains.annotations.NotNull()
    java.lang.String namedTransformation) {
        return null;
    }
    
    /**
     * Method to specify the default image which is delivered in case the image that is requested using ImageKit does not exist.
     * @param defaultImage
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor defaultImage(@org.jetbrains.annotations.NotNull()
    java.lang.String defaultImage) {
        return null;
    }
    
    /**
     * Method to specify if the output JPEG image should be rendered progressively. In progressive rendering,
     * the client instead of downloading the image row-wise (baseline loading), renders a low-quality pixelated
     * full image and then gradually keeps on adding more pixels and information to the image. It gives faster-perceived load times.
     * @param flag Possible values include true and false. Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor isProgressiveJPEG(boolean flag) {
        return null;
    }
    
    /**
     * Method to specify if the output image (if in PNG or WebP format) should be compressed losslessly.
     * In lossless compression, the output file size would be larger than the regular lossy compression but at the same time,
     * the perceived quality can be better in certain cases, especially for computer generated graphics.
     * Using lossless compression is not recommended for photographs.
     * @param flag Possible values include true and false. Default value - false
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor isLossless(boolean flag) {
        return null;
    }
    
    /**
     * Method to specify if the redundant pixels of the original image need to be removed. It uses a default logic
     * to identify the redundant surrounding region and removes it. This transformation is useful for images that have
     * a solid / nearly-solid background and the object in the center. This transformation will trim
     * the background from the edges, leaving only the central object in the picture.
     * @param flag Possible values include true and false.
     * @return the current ImagekitUrlConstructor object.
     * @see trimEdges
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor trimEdges(boolean flag) {
        return null;
    }
    
    /**
     * Method to specify the number of redundant pixels of the original image that need to be removed.
     * This transformation is useful for images that have a solid / nearly-solid background and the object in the center.
     * This transformation will trim the background from the edges, leaving only the central object in the picture.
     * @param value Number of pixels from the edge that need to be removed across all four sides.
     * @return the current ImagekitUrlConstructor object.
     * @see trimEdges
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor trimEdges(int value) {
        return null;
    }
    
    /**
     * Method to specify an image to overlay over another image. This will help you generate watermarked images if needed.
     * @param overlayImage
     * @return the current ImagekitUrlConstructor object.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayImage(@org.jetbrains.annotations.NotNull()
    java.lang.String overlayImage) {
        return null;
    }
    
    /**
     * Method used to specify the position of the overlaid image relative to the input image.
     * @param overlayFocus Possible values include center, top, left, bottom, right, top_left, top_right, bottom_left and bottom_right.
     * Default value - center
     * @see OverlayFocusType
     * @return the current ImagekitUrlConstructor object.
     * @throws ImagekitException if not a single overlay is specified or the overlay coordinates have already been applied
     * using either overlayPosX() or overlayPosY()
     */
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayFocus(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.OverlayFocusType overlayFocus) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayPosX(int overlayPosX) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayPosY(int overlayPosY) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayNegX(int overlayNegX) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayNegY(int overlayNegY) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayWidth(float overlayWidth) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayHeight(float overlayHeight) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayText(@org.jetbrains.annotations.NotNull()
    java.lang.String overlayText) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayTextColor(@org.jetbrains.annotations.NotNull()
    java.lang.String overlayTextColor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayTextFont(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.FocusType overlayTextFont) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayTextSize(int overlayTextSize) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayTextTypography(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.OverlayTextTypography overlayTextTypography) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayBackgroundColor(@org.jetbrains.annotations.NotNull()
    java.lang.String overlayBackgroundColor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor overlayTransparency(int overlayTransparency) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor includeColorProfile(boolean flag) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor includeImageMetadata(boolean flag) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor rotate(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.entity.Rotation rotation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor radius(int radius) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor round() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor backgroundHexColor(@org.jetbrains.annotations.NotNull()
    java.lang.String backgroundHexColor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor backgroundRGBAColor(@org.jetbrains.annotations.NotNull()
    java.lang.String backgroundRGBAColor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor border(int borderWidth, @org.jetbrains.annotations.NotNull()
    java.lang.String borderColor) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor stretchContrast(boolean flag) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor sharpen(int value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor applyUnsharpMask(float radius, float sigma, float amount, float threshold) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.ImagekitUrlConstructor addTransformationStep() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String create() {
        return null;
    }
    
    public ImagekitUrlConstructor(@org.jetbrains.annotations.NotNull()
    java.lang.String endpoint, @org.jetbrains.annotations.NotNull()
    java.lang.String imagePath) {
        super();
    }
}