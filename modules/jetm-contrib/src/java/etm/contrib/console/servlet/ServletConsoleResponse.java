/*
 *
 * Copyright (c) 2004, 2005, 2006, 2007 void.fm
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name void.fm nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package etm.contrib.console.servlet;

import etm.contrib.console.ConsoleResponse;
import etm.contrib.console.util.ConsoleUtil;
import etm.core.monitor.EtmException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * Response abstraction for servlet based HTTP console.
 *
 * @version $Revision: 126 $
 * @author void.fm
 * 
 */
public class ServletConsoleResponse implements ConsoleResponse {

  private HttpServletResponse response;


  public ServletConsoleResponse(HttpServletResponse aResponse) {
    response = aResponse;
    response.setCharacterEncoding("UTF-8");
  }

  public void addHeader(String header, String value) {
    response.addHeader(header, value);
  }

  public void write(String content) throws IOException {
    response.getWriter().write(content);
  }

  public void write(char[] content) throws IOException {
    response.getWriter().write(content);
  }

  public void write(byte[] content) throws IOException {
    response.getOutputStream().write(content);
  }

  public void sendRedirect(String target, Map parameters) {
    try {
      response.sendRedirect(ConsoleUtil.appendParameters(target, parameters));
    } catch (IOException e) {
      throw new EtmException(e);
    }
  }

  public void sendStatus(int statusCode, String description) {
    try {
      response.sendError(statusCode, description);
    } catch (IOException e) {
      throw new EtmException(e);
    }
  }
}
