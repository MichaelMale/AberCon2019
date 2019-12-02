package uk.ac.aber.cs31620.abercon2019.model.datasource.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

/**
 * ResourceUtil.java - A class to facilitate the retrieval of data from the "assets/images"
 * folder. In this situation, it is used to retrieve images assigned to speakers.
 * <p>
 * To allow for better thread management, this class retrieves the image asynchronously through
 * an inner class.
 *
 * @author Michael Male
 * @version 1.0 2019-12-01
 * @see AsyncTask
 */
public class ResourceUtil {
    private static final String ASSET_MISSING_TAG = "ASSET_MISSING"; // Tag that is used to log
    // any missing assets.

    /**
     * Executes the asynchronous task to retrieve a bitmap representation of the requested image.
     *
     * @param context   The context of the application
     * @param imagePath String containing the image path
     * @return Object of type Bitmap containing a representation of such image
     */
    public static Bitmap getAssetImageAsBitmap(Context context, String imagePath) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context); // To avoid
        // context leaks, a weak reference is passed to the asynchronous task.
        try {
            return new AsyncRetrieveImage(contextWeakReference, imagePath).execute().get();
        } catch (ExecutionException | InterruptedException e) { // This would occur in the event
            // of threading issues or issues that are outside the user's control. Stack trace is
            // logged to stdout.
            e.printStackTrace();
        }
        return null; // While the method shouldn't reach this far, if there is no bitmap
        // representation of the image then the method will return null.
    }

    /**
     * ResourceUtil.AsyncRetrieveImage - A class to facilitate asynchronously finding an image
     * and returning it as a Bitmap.
     *
     * @author Michael Male
     * @version 1.0 PRODUCTION
     */
    private static class AsyncRetrieveImage extends AsyncTask<Void, Void, Bitmap> {
        private WeakReference<Context> mWeakContext;
        private String mImagePath;

        /**
         * Constructor for objects of type AsyncRetrieveImage.
         *
         * @param reference A weak reference of type context, containing the context of the
         *                  application
         * @param imagePath A string containing the image path
         */
        AsyncRetrieveImage(WeakReference<Context> reference, String imagePath) {
            mWeakContext = reference;
            mImagePath = imagePath;
        }


        /**
         * Method manages the locating and returning of a Bitmap via a background task.
         *
         * @param voids Object of type Void. The method is not designed to have any parameters
         *              passed to it.
         * @return Object of type Bitmap containing a bitmap representation of the requested
         * image, or null if the image wasn't found.
         */
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap result = null;
            AssetManager assetManager = mWeakContext.get().getAssets();
            try {
                InputStream is = assetManager.open("images/" + mImagePath);
                result = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                Log.w(ASSET_MISSING_TAG, e.toString());
            }
            return result;
        }
    }
}
