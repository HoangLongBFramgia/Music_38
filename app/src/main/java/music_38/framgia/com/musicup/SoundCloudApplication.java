package music_38.framgia.com.musicup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class SoundCloudApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig
                .newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(), imagePipelineConfig);
    }
}
