package music_38.framgia.com.musicup.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class Utils {

    private static final int IMAGE_RESIZE_WIDTH = 50;
    private static final int IMAGE_RESIZE_HEIGHT = 50;
    private static final int IMAGE_BLUR = 20;

    public static void blurImageWithFresco(SimpleDraweeView imageView, Uri uri) {

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(IMAGE_BLUR))
                .setResizeOptions(new ResizeOptions(IMAGE_RESIZE_WIDTH, IMAGE_RESIZE_HEIGHT))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);
    }
}
