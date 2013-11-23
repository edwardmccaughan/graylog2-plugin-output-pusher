/**
 * Copyright 2012 Lennart Koopmann <lennart@socketfeed.com>
 *
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.graylog2.pusheroutput.output;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.graylog2.plugin.GraylogServer;
import org.graylog2.plugin.logmessage.LogMessage;
import org.graylog2.plugin.outputs.MessageOutput;
import org.graylog2.plugin.outputs.MessageOutputConfigurationException;
import org.graylog2.plugin.outputs.OutputStreamConfiguration;
import org.graylog2.plugin.streams.Stream;

/**
 * @author Lennart Koopmann <lennart@socketfeed.com>
 */
public class PusherOutput implements MessageOutput {

  private static final String NAME = "Pusher output";
  
  private Map<String, String> configuration;
  private PusherClient pusher;
  
  public void initialize(Map<String, String> configuration) throws MessageOutputConfigurationException {
    this.configuration = configuration;
    pusher = new PusherClient(configuration);
  }



  public void write(List<LogMessage> messages, OutputStreamConfiguration streamConfiguration, GraylogServer server) throws Exception {
    for (LogMessage msg : messages) {
      sendMail(msg);
    }
  }

  public Map<String, String> getRequestedConfiguration() {
      Map<String, String> config = new HashMap<String, String>();

      config.put("application_key", "pusher application key");
      config.put("application_id", "pusher application id");
      config.put("application_secret", "pusher application secret");
      
      return config;
  }

   public Map<String, String> getRequestedStreamConfiguration() {
        Map<String, String> config = new HashMap<String, String>();        
        return config;
    }

  public String getName() {
      return NAME;
  }
  
  private boolean configSet(Map<String, String> target, String key) {
      return target != null && target.containsKey(key)
              && target.get(key) != null && !target.get(key).isEmpty();
  }

  private void sendMail(LogMessage msg){
      System.out.println("PUSHER: sending message");
      // msg.getFullMessage()
      pusher.triggerPush("site_events", "event", msg.getShortMessage());
  }
}
