package org.dwallach.calwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * We need a separate activity for the sole purpose of requesting permissions.
 */
public class PermissionActivity extends Activity {
    private static final String TAG = "PermissionActivity";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.v(TAG, "onRequestPermissionsResult");
        CalendarPermission.handleResult(requestCode, permissions, grantResults);
        CalWatchFaceService.Engine engine = CalWatchFaceService.getEngine();
        if(engine != null) {
            engine.calendarPermissionUpdate();
        }
        Log.v(TAG, "finishing PermissionActivity");
        this.finish(); // we're done, so this shuts everything down
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.v(TAG, "starting PermissionActivity");

        CalendarPermission.request(this);
    }

    /**
     * Call this to launch the wear permission dialog.
     */
    public static void kickStart(Context context, boolean firstTimeOnly) {
        Log.v(TAG, "kickStart");

        if(firstTimeOnly && CalendarPermission.getNumRequests() > 0) return; // don't bug the user!

        Intent activityIntent = new Intent(context, PermissionActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}