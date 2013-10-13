//import Pusher;

public class Push {

  public static void main(String[] args) {
    Pusher.triggerPush("site_events", "event", "{\"event\":\"new_user\"}");
    System.out.println("Hello, World");
  }

}