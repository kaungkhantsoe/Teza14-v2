package com.kks.teza14.events;

/**
 * Created by kaungkhantsoe on 17/05/2020.
 **/
public class AppEvents {

     public static class CallAnswerEvent {
        boolean isAnswered = false;

        public CallAnswerEvent(boolean isAnswered) {
            this.isAnswered = isAnswered;
        }

        public boolean getIsAnswered() {
            return isAnswered;
        }
    }

    public static class NotificationEvent {
         String token;

         public NotificationEvent(String token) {
             this.token = token;
         }

         public String getToken() {
             return token;
         }
    }

    public static class AddEvent {

        public String postId;

        public AddEvent(String postId) {
            this.postId = postId;
        }

        public String getPostId() {
            return postId;
        }
    }
}
