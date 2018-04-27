package com.mizhi.videoplayermanagertest;

import android.graphics.Rect;
import android.view.View;

import com.volokh.danylo.video_player_manager.manager.VideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * 类描述：
 *
 * @Author 许少东
 * Created at 2018/4/27.
 */

public abstract class VideoListItem implements VideoItem, ListItem {
    private final Rect mCurrentViewRect; // 当前视图的方框
    private final VideoPlayerManager<MetaData> mVideoPlayerManager; // 视频播放管理器
    private final String mTitle; // 标题
    private final String CoverImageUrl; // 图片资源

    // 构造器, 输入视频播放管理器
    public VideoListItem(
            VideoPlayerManager<MetaData> videoPlayerManager,
            String title,
            String imageResource) {
        mVideoPlayerManager = videoPlayerManager;
        mTitle = title;
        CoverImageUrl = imageResource;

        mCurrentViewRect = new Rect();
    }

    // 视频项的标题
    public String getTitle() {
        return mTitle;
    }

    public String getCoverImageUrl() {
        return CoverImageUrl;
    }

    // 显示可视的百分比程度
    @Override
    public int getVisibilityPercents(View view) {
        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }


        return percents;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        VideoWatchAdapter.VideoViewHolder viewHolder =
                (VideoWatchAdapter.VideoViewHolder) newActiveView.getTag();
        playNewVideo(new CurrentItemMetaData(newActiveViewPosition, newActiveView),
                viewHolder.getVpvPlayer(), mVideoPlayerManager);
    }

    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mVideoPlayerManager);
    }


    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    // 顶部出现
    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    // 底部出现
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }
}
