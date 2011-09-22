/*
 * Copyright (C) 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.android.rosserial;

import com.google.common.base.Preconditions;

import org.ros.android.acm_serial.AcmDevice;
import org.ros.node.DefaultNodeFactory;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.rosserial.ROSSerial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class SerialNode implements NodeMain {

  private static final int STREAM_BUFFER_SIZE = 8192;

  private final AcmDevice device;

  private Node node;
  private ROSSerial rosSerial;

  public SerialNode(AcmDevice device) {
    this.device = device;
  }

  @Override
  public void main(NodeConfiguration configuration) {
    Preconditions.checkState(node == null);
    Preconditions.checkState(rosSerial == null);
    DefaultNodeFactory nodeFactory = new DefaultNodeFactory();
    node = nodeFactory.newNode("rosserial_node", configuration);
    rosSerial =
        new ROSSerial(node, new BufferedInputStream(device.getInputStream(), STREAM_BUFFER_SIZE),
            new BufferedOutputStream(device.getOutputStream(), STREAM_BUFFER_SIZE));
    rosSerial.run();
  }

  @Override
  public void shutdown() {
    Preconditions.checkNotNull(node);
    Preconditions.checkNotNull(rosSerial);
    rosSerial.shutdown();
    rosSerial = null;
    node.shutdown();
    node = null;
  }

}