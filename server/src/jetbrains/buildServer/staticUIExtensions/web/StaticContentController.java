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

import jetbrains.buildServer.controllers.AuthorizationInterceptor;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.staticUIExtensions.Configuration;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.util.WebUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 19:47
 */
public class StaticContentController extends BaseController {
  private ControllerPaths myPaths;
  private final Configuration myConfig;

  public StaticContentController(@NotNull final AuthorizationInterceptor auth,
                                 @NotNull final WebControllerManager web,
                                 @NotNull final ControllerPaths paths,
                                 @NotNull final Configuration config) {
    myPaths = paths;
    myConfig = config;
    final String path = paths.getResourceControllerRegistrationBase() + "/**";
    web.registerController(path, this);
    auth.addPathNotRequiringAuth(path);
  }

  @Override
  protected ModelAndView doHandle(@NotNull final HttpServletRequest request,
                                  @NotNull final HttpServletResponse response) throws Exception {
    String requestPath = WebUtil.getPathWithoutAuthenticationType(request);
    if (!requestPath.startsWith("/")) requestPath = "/" + requestPath;

    if (requestPath.equals(myPaths.getResourceControllerPathEmpty())) {
      return null;
    }

    final String prefixPath = myPaths.getResourceControllerPathBase();
    if (!requestPath.startsWith(prefixPath)) {
      response.sendError(HttpStatus.SC_NOT_FOUND, "Path not found");
      return null;
    }

    final String path = requestPath.substring(prefixPath.length()+1);
    int slash = path.indexOf('/');
    if (slash >= 0) {
      response.sendError(HttpStatus.SC_NOT_FOUND, "Path not found. Invalid path");
      return null;
    }

    final File includeFile = myConfig.mapIncludeFilePath(path);
    if (includeFile == null || !includeFile.isFile()) {
      response.sendError(HttpStatus.SC_NOT_FOUND, "Path not found");
      return null;
    }

    final char[] data;
    try {
      data = FileUtil.loadFileText(includeFile, "utf-8");
    } catch (IOException e) {
      response.sendError(HttpStatus.SC_NOT_FOUND, "Failed to open file.");
      return null;
    }

    response.getWriter().write(data);
    return null;
  }


}
