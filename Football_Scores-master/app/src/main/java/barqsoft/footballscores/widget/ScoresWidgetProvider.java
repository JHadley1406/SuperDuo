package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.R;
import barqsoft.footballscores.service.ScoresWidgetService;

/**
 * Created by Josiah Hadley on 11/18/2015.
 */
public class ScoresWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "ACTION_TOAST";
    public static final String EXTRA_STRING = "EXTRA_STRING";

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(ACTION_TOAST)){
            String item = intent.getExtras().getString(EXTRA_STRING);
            Toast.makeText(context, item, Toast.LENGTH_LONG).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context
            , AppWidgetManager appWidgetManager
            , int[] appWidgetIds){

        for(int widgetId : appWidgetIds){
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

            final Intent onItemClick = new Intent(context, ScoresWidgetProvider.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0, onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.scores_collection_list, onClickPendingIntent);

            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context, AppWidgetManager widgetManager, int widgetId){

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_scores_provider);

        Intent intent = new Intent(context, ScoresWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.scores_collection_list, intent);

        return mView;
    }
}
