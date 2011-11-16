/*
 * Copyright 2000-2011 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jetbrains.buildServer.staticUIExtensions.web;

import jetbrains.buildServer.staticUIExtensions.Configuration;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 19:50
 */
public class ControllerPaths {
  private final String myResourcesControllerPath;
  @NotNull
  private final Configuration myConfig;

  public ControllerPaths(@NotNull final PluginDescriptor descriptor,
                         @NotNull final Configuration config) {
    myConfig = config;
    myResourcesControllerPath = descriptor.getPluginResourcesPath("resources.html");
  }

  public String getResourceControllerRegistrationBase() {
    return myResourcesControllerPath;
  }

  public String getResourceControllerPathBase() {
    return getResourceControllerRegistrationBase() +"/" + myConfig.getAccessToken();
  }

  @NotNull
  public String getResourceControllerPath(@NotNull String resource) {
    return getResourceControllerPathBase() + "/" + resource;
  }

  @NotNull
  public String getResourceControllerPathEmpty() {
    return getResourceControllerPathBase() + "/" + myConfig.getAccessToken();
  }
}
