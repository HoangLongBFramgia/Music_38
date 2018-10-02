package music_38.framgia.com.musicup.screen.search.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Track> mTracks;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mLayoutInflater;

    public SearchAdapter(List<Track> tracks, OnItemClickListener onItemClickListener) {
        mTracks = tracks;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_playlist, viewGroup, false);
        return new SearchViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder detailViewHolder, int i) {
        Track track = mTracks.get(i);
        detailViewHolder.bindData(track, mTracks);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView mImageTrack;
        private TextView mTextTrack;
        private TextView mTextAuthor;
        private ImageView mImageMore;
        private OnItemClickListener mOnItemClickListener;
        private List<Track> mTracks;

        SearchViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mOnItemClickListener = onItemClickListener;
            mImageTrack = itemView.findViewById(R.id.image_song);
            mTextTrack = itemView.findViewById(R.id.text_title_song);
            mTextAuthor = itemView.findViewById(R.id.text_auth_song);
            mImageMore = itemView.findViewById(R.id.icon_more_song);
            itemView.setOnClickListener(this);
            mImageMore.setOnClickListener(this);
        }

        private void bindData(Track track, List<Track> tracks) {
            mTracks = tracks;
            mImageTrack.setImageURI(Uri.parse(track.getArtworkUrl()));
            mTextAuthor.setText(track.getArtist());
            mTextTrack.setText(track.getTitle());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.icon_more_song:
                    break;
                case R.id.constraint_item_playlist:
                    mOnItemClickListener.onItemClickSearchListener(mTracks, getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickSearchListener(List<Track> tracks, int position);
    }
}
