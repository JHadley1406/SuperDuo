package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.widget.ScoresWidgetFactory;

/**
 * Created by Josiah Hadley on 12/3/2015.
 */
public class ScoresWidgetService extends RemoteViewsService {

    @Override
    public ScoresWidgetFactory onGetViewFactory(Intent intent){

        ScoresWidgetFactory dataProvider = new ScoresWidgetFactory(
                getApplicationContext(), intent);

        return dataProvider;
    }
}
