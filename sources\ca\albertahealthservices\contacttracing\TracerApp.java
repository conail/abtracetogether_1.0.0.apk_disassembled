package ca.albertahealthservices.contacttracing;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.idmanager.TemporaryID;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import ca.albertahealthservices.contacttracing.streetpass.CentralDevice;
import ca.albertahealthservices.contacttracing.streetpass.PeripheralDevice;
import com.worklight.common.Logger;
import com.worklight.common.Logger.LEVEL;
import com.worklight.common.WLAnalytics;
import com.worklight.common.WLAnalytics.DeviceEvent;
import com.worklight.wlclient.api.WLClient;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lca/albertahealthservices/contacttracing/TracerApp;", "Landroid/app/Application;", "()V", "onCreate", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
/* compiled from: TracerApp.kt */
public final class TracerApp extends Application {
    public static Context AppContext = null;
    public static final Companion Companion = new Companion(null);
    public static final String ORG = "CA_AB";
    /* access modifiers changed from: private */
    public static final String TAG = TAG;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nXD¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lca/albertahealthservices/contacttracing/TracerApp$Companion;", "", "()V", "AppContext", "Landroid/content/Context;", "getAppContext", "()Landroid/content/Context;", "setAppContext", "(Landroid/content/Context;)V", "ORG", "", "TAG", "asCentralDevice", "Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;", "asPeripheralDevice", "Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "thisDeviceMsg", "app_release"}, k = 1, mv = {1, 1, 16})
    /* compiled from: TracerApp.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Context getAppContext() {
            Context access$getAppContext$cp = TracerApp.AppContext;
            if (access$getAppContext$cp == null) {
                Intrinsics.throwUninitializedPropertyAccessException("AppContext");
            }
            return access$getAppContext$cp;
        }

        public final void setAppContext(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "<set-?>");
            TracerApp.AppContext = context;
        }

        public final String thisDeviceMsg() {
            TemporaryID broadcastMessage = BluetoothMonitoringService.Companion.getBroadcastMessage();
            if (broadcastMessage != null) {
                ca.albertahealthservices.contacttracing.logging.CentralLog.Companion companion = CentralLog.Companion;
                String access$getTAG$cp = TracerApp.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Retrieved BM for storage: ");
                sb.append(broadcastMessage);
                companion.i(access$getTAG$cp, sb.toString());
                if (!broadcastMessage.isValidForCurrentTime()) {
                    TemporaryID retrieveTemporaryID = TempIDManager.INSTANCE.retrieveTemporaryID(TracerApp.Companion.getAppContext());
                    if (retrieveTemporaryID != null) {
                        CentralLog.Companion.i(TracerApp.TAG, "Grab New Temp ID");
                        BluetoothMonitoringService.Companion.setBroadcastMessage(retrieveTemporaryID);
                    }
                    if (retrieveTemporaryID == null) {
                        CentralLog.Companion.e(TracerApp.TAG, "Failed to grab new Temp ID");
                    }
                }
            }
            TemporaryID broadcastMessage2 = BluetoothMonitoringService.Companion.getBroadcastMessage();
            if (broadcastMessage2 != null) {
                String tempID = broadcastMessage2.getTempID();
                if (tempID != null) {
                    return tempID;
                }
            }
            return "Missing TempID";
        }

        public final PeripheralDevice asPeripheralDevice() {
            String str = Build.MODEL;
            Intrinsics.checkExpressionValueIsNotNull(str, "Build.MODEL");
            return new PeripheralDevice(str, "SELF");
        }

        public final CentralDevice asCentralDevice() {
            String str = Build.MODEL;
            Intrinsics.checkExpressionValueIsNotNull(str, "Build.MODEL");
            return new CentralDevice(str, "SELF");
        }
    }

    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(applicationContext, "applicationContext");
        AppContext = applicationContext;
        WLClient.createInstance(this);
        WLClient.getInstance().pinTrustedCertificatePublicKey("mfpcertificate.cer");
        WLAnalytics.init(this);
        WLAnalytics.addDeviceEventListener(DeviceEvent.LIFECYCLE);
        Logger.setCapture(true);
        Logger.setLevel(LEVEL.ERROR);
        WLAnalytics.send();
        Logger.send();
    }
}
