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
package test.etm.contrib.aop.spring;

import etm.core.monitor.EtmMonitor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import test.etm.contrib.aop.AopTestBase;
import test.etm.contrib.aop.resources.BarService;
import test.etm.contrib.aop.resources.FooService;
import test.etm.contrib.aop.resources.YaddaService;

/**
 * Test using SpringFramework BeanPostProcesor
 * BeanNameAutoProxyCreator.
 *
 * @author void.fm
 * @version $Revision: 35 $
 */
public class AutoProxyBeanTest extends AopTestBase {

  protected void setUp() throws Exception {
    super.setUp();
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test/etm/contrib/aop/spring/autoproxy-bean.xml");

    etmMonitor = (EtmMonitor) applicationContext.getBean("etmMonitor");
    yaddaService = (YaddaService) applicationContext.getBean("yaddaService");
    barService = (BarService) applicationContext.getBean("barService");
    fooService = (FooService) applicationContext.getBean("fooService");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }


}
