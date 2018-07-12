package com.gulei.moudle_girls.bean;

import com.gulei.common.view.photoview.Info;

/**
 * Created by gl152 on 2018/6/28.
 */

public class EventType {
    public static class PageSelected {
        public int position;

        public PageSelected(int position) {
            this.position = position;
        }
    }

    public static class BackInfo {
      public int currentPosition;

        public BackInfo(int currentPosition) {
            this.currentPosition = currentPosition;
        }
    }

    public static class GetInfo {
        public Info info;

        public GetInfo(Info info) {
            this.info = info;
        }
    }
}
