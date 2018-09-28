package music_38.framgia.com.musicup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class SoundCloudApplication extends Application {

    private static SoundCloudApplication sSelf;

    public static SoundCloudApplication self() {
        return sSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSelf = this;
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig
                .newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(), imagePipelineConfig);
    }
}
