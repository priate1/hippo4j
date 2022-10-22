/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hippo4j.auth.filter;

import cn.hippo4j.auth.toolkit.AuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * When anonymous login is enabled, an error will be reported when viewing the current user information.
 * Modify the URI to query the default administrator information.
 *
 * before:hippo4j/v1/cs/auth/users/info or hippo4j/v1/cs/auth/users/info/xxx
 * after:hippo4j/v1/cs/auth/users/info/admin
 */
public class RewriteUserInfoApiFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean enableAuthentication = AuthUtil.enableAuthentication;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();
        if (!enableAuthentication && path.contains("users/info")) {
            httpRequest.getRequestDispatcher("/hippo4j/v1/cs/auth/users/info/admin").forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}