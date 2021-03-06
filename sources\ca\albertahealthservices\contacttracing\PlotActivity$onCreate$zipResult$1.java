package ca.albertahealthservices.contacttracing;

import androidx.core.app.NotificationCompat;
import ca.albertahealthservices.contacttracing.fragment.ExportData;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import io.reactivex.functions.BiFunction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "Lca/albertahealthservices/contacttracing/fragment/ExportData;", "records", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "status", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "apply"}, k = 3, mv = {1, 1, 16})
/* compiled from: PlotActivity.kt */
final class PlotActivity$onCreate$zipResult$1<T1, T2, R> implements BiFunction<List<? extends StreetPassRecord>, List<? extends StatusRecord>, ExportData> {
    public static final PlotActivity$onCreate$zipResult$1 INSTANCE = new PlotActivity$onCreate$zipResult$1();

    PlotActivity$onCreate$zipResult$1() {
    }

    public final ExportData apply(List<StreetPassRecord> list, List<StatusRecord> list2) {
        Intrinsics.checkParameterIsNotNull(list, "records");
        Intrinsics.checkParameterIsNotNull(list2, NotificationCompat.CATEGORY_STATUS);
        return new ExportData(list, list2);
    }
}
