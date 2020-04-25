package com.devtides.androidarchitectures.wrapperclass;

public class ImageViewEventBus {

    public static class Refresh {
        String processid;
        int completed;

        public Refresh(String processid, int completed) {
            this.processid = processid;
            this.completed = completed;
        }

        public String getProcessid() {
            return processid;
        }

        public void setProcessid(String processid) {
            this.processid = processid;
        }

        public int getCompleted() {
            return completed;
        }

        public void setCompleted(int completed) {
            this.completed = completed;
        }
    }

}
