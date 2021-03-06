package ca.albertahealthservices.contacttracing.idmanager;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.api.Request;
import ca.albertahealthservices.contacttracing.api.Response;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.logging.WFLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.Charset;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
@DebugMetadata(c = "ca.albertahealthservices.contacttracing.idmanager.TempIDManager$getTemporaryIDs$1", f = "TempIDManager.kt", i = {0, 0}, l = {129}, m = "invokeSuspend", n = {"$this$launch", "queryParams"}, s = {"L$0", "L$1"})
/* compiled from: TempIDManager.kt */
final class TempIDManager$getTemporaryIDs$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Context $context;
    Object L$0;
    Object L$1;
    int label;
    private CoroutineScope p$;

    TempIDManager$getTemporaryIDs$1(Context context, Continuation continuation) {
        this.$context = context;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        TempIDManager$getTemporaryIDs$1 tempIDManager$getTemporaryIDs$1 = new TempIDManager$getTemporaryIDs$1(this.$context, continuation);
        tempIDManager$getTemporaryIDs$1.p$ = (CoroutineScope) obj;
        return tempIDManager$getTemporaryIDs$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((TempIDManager$getTemporaryIDs$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        String str;
        Object obj2;
        String str2 = "[TempID] Error getting Temporary IDs - no refreshTime returned";
        String str3 = "[TempID] Error getting Temporary IDs - no temp IDs returned";
        String str4 = "[TempID] Error getting Temporary IDs - status not SUCCESS";
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        String str5 = "TempIDManager";
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = this.p$;
            HashMap hashMap = new HashMap();
            hashMap.put("userId", Preference.INSTANCE.getUUID(this.$context));
            Request request = Request.INSTANCE;
            String str6 = "/adapters/tempidsAdapter/tempIds";
            String str7 = "GET";
            this.L$0 = coroutineScope;
            this.L$1 = hashMap;
            this.label = 1;
            str = str5;
            try {
                obj2 = Request.runRequest$default(request, str6, str7, 0, null, null, hashMap, this, 28, null);
                if (obj2 == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } catch (Exception unused) {
                String str8 = "[TempID] Error getting Temporary IDs";
                CentralLog.Companion.d(str, str8);
                WFLog.Companion.logError(str8);
                return Unit.INSTANCE;
            }
        } else if (i == 1) {
            HashMap hashMap2 = (HashMap) this.L$1;
            CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                obj2 = obj;
                str = str5;
            } catch (Exception unused2) {
                str = str5;
                String str82 = "[TempID] Error getting Temporary IDs";
                CentralLog.Companion.d(str, str82);
                WFLog.Companion.logError(str82);
                return Unit.INSTANCE;
            }
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Response response = (Response) obj2;
        CentralLog.Companion.d(str, response.toString());
        if (response.isSuccess()) {
            Integer status = response.getStatus();
            if (status != null && status.intValue() == 200) {
                CentralLog.Companion.i(str, "Retrieved Temporary IDs from Server");
                String text = response.getText();
                String str9 = null;
                String replace$default = text != null ? StringsKt.replace$default(text, "{\"pin\":", "", false, 4, (Object) null) : null;
                String str10 = "null cannot be cast to non-null type java.lang.String";
                if (replace$default != null) {
                    int length = replace$default.length() - 1;
                    if (replace$default != null) {
                        str9 = replace$default.substring(0, length);
                        Intrinsics.checkExpressionValueIsNotNull(str9, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    } else {
                        throw new TypeCastException(str10);
                    }
                }
                Object fromJson = new Gson().fromJson(str9, new TempIDManager$getTemporaryIDs$1$result$1().getType());
                Intrinsics.checkExpressionValueIsNotNull(fromJson, "Gson().fromJson(\n       …tType()\n                )");
                HashMap hashMap3 = (HashMap) fromJson;
                Object obj3 = hashMap3.get(NotificationCompat.CATEGORY_STATUS);
                Object obj4 = hashMap3.get("tempIDs");
                Object value = MapsKt.getValue(hashMap3, "refreshTime");
                if (value != null) {
                    double doubleValue = ((Double) value).doubleValue();
                    if (!Intrinsics.areEqual(obj3, (Object) "SUCCESS")) {
                        CentralLog.Companion.d(str, str4);
                        WFLog.Companion.logError(str4);
                    } else if (obj4 == null) {
                        CentralLog.Companion.d(str, str3);
                        WFLog.Companion.logError(str3);
                    } else if (Boxing.boxDouble(doubleValue) == null) {
                        CentralLog.Companion.d(str, str2);
                        WFLog.Companion.logError(str2);
                    } else {
                        String json = new GsonBuilder().create().toJson(obj4);
                        Intrinsics.checkExpressionValueIsNotNull(json, "GsonBuilder().create().toJson(tempIDs)");
                        Charset charset = Charsets.UTF_8;
                        if (json != null) {
                            byte[] bytes = json.getBytes(charset);
                            Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
                            TempIDManager.INSTANCE.storeTemporaryIDs(this.$context, new String(bytes, Charsets.UTF_8));
                            long j = (long) doubleValue;
                            long j2 = (long) 1000;
                            Preference.INSTANCE.putNextFetchTimeInMillis(this.$context, j * j2);
                            Preference.INSTANCE.putLastFetchTimeInMillis(this.$context, System.currentTimeMillis() * j2);
                        } else {
                            throw new TypeCastException(str10);
                        }
                    }
                    return Unit.INSTANCE;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            }
        }
        throw new Exception("Failed to get temp ids");
    }
}
