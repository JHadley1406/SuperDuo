package barqsoft.footballscores.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresProvider;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.model.Match;
import barqsoft.footballscores.scoresAdapter;

/**
 * Created by Josiah Hadley on 11/23/2015.
 */
public class ScoresWidgetFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String LOG_TAG = ScoresWidgetFactory.class.getSimpleName();


    private List<Match> mScores = new ArrayList();
    private Context mContext;
    private String[] currentDate = new String[1];
    private Cursor mCursor;
    private ScoresProvider mScoresProvider;

    public ScoresWidgetFactory(Context context, Intent intent){
        mContext = context;
        mScoresProvider = new ScoresProvider();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        currentDate[0] = Utilies.getDate(1);
        if(mCursor != null){
            mCursor = null;
        }
        long identityToken = Binder.clearCallingIdentity();
        try {
            mCursor = mContext.getContentResolver()
                    .query(DatabaseContract.scores_table.buildScoreWithDate()
                            , null
                            , null
                            , currentDate
                            , null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    Match match = new Match(mCursor.getString(scoresAdapter.COL_HOME)
                            , mCursor.getString(scoresAdapter.COL_AWAY)
                            , mCursor.getString(scoresAdapter.COL_HOME_GOALS)
                            + "-"
                            + mCursor.getString(scoresAdapter.COL_AWAY_GOALS));
                    mScores.add(match);
                    mCursor.moveToNext();
                }
            }
        }finally {
            mCursor.close();
            Binder.restoreCallingIdentity(identityToken);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mScores.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_list_item);
        mView.setTextViewText(R.id.widget_home_name, mScores.get(position).getHomeTeam());
        mView.setTextViewText(R.id.widget_away_name, mScores.get(position).getAwayTeam());
        mView.setTextViewText(R.id.widget_score, mScores.get(position).getScore());

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(ScoresWidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(ScoresWidgetProvider.EXTRA_STRING, "SPORTSBALL!!");
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return mView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
