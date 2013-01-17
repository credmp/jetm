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

package etm.core.configuration;

import etm.core.plugin.EtmPlugin;

/**
 * Configuration for an EtmPlugin
 *
 * @author void.fm
 * @version $Revision: 118 $
 */
public class EtmPluginConfig extends PropertyConfig {
  private Class pluginClass;

  public Class getPluginClass() {
    return pluginClass;
  }


  public void setPluginClass(Class aPluginClass) {
    pluginClass = aPluginClass;
  }

  public void setPluginClass(String pluginClassName) {
    Class clazz;
    try {
      clazz = Class.forName(pluginClassName);
    } catch (ClassNotFoundException e) {
      throw new EtmConfigurationException("Plugin class " + pluginClassName + " not found.");
    }
    if (EtmPlugin.class.isAssignableFrom(clazz)) {
      pluginClass = clazz;
    } else {
      throw new EtmConfigurationException("Class " + pluginClassName + " is not a valid plugin implementation.");
    }
  }
  
}
