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

package org.ros.android.views.visualization;

import org.ros.message.geometry_msgs.Transform;
import org.ros.message.geometry_msgs.Vector3;
import org.ros.rosjava_geometry.Geometry;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Provides Functionality to apply a list of transforms to an OpenGL context.
 * 
 * @author moesenle@google.com (Lorenz Moesenlechner)
 * 
 */
public class GlTransformer {

  public static void applyTransforms(GL10 gl, List<Transform> transforms) {
    for (Transform transform : transforms) {
      gl.glTranslatef((float) transform.translation.x, (float) transform.translation.y,
          (float) transform.translation.z);
      double angleDegrees = Math.toDegrees(Geometry.calculateRotationAngle(transform.rotation));
      Vector3 axis = Geometry.calculateRotationAxis(transform.rotation);
      gl.glRotatef((float) angleDegrees, (float) axis.x, (float) axis.y, (float) axis.z);
    }
  }

}