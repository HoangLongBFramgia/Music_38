package music_38.framgia.com.musicup.data.source.local;

import android.os.AsyncTask;

import java.util.List;

import music_38.framgia.com.musicup.data.model.RecentSearch;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.local.realm.RealmRecentSearch;

public class RecentSearchLocalAsyncTask extends AsyncTask<String, String, List<RecentSearch>> {

    private Callback mCallBack;

    public RecentSearchLocalAsyncTask(Callback callBack) {
        mCallBack = callBack;
    }

    @Override
    protected List<RecentSearch> doInBackground(String... strings) {
        return RealmRecentSearch.getRecentSearchList();
    }

    @Override
    protected void onPostExecute(List<RecentSearch> recentSearches) {
        super.onPostExecute(recentSearches);
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(recentSearches);

    }
}
