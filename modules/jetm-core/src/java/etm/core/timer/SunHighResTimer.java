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

package etm.core.timer;

import etm.core.metadata.TimerMetaData;
import sun.misc.Perf;

/**
 * The SunHighResTimer makes use of undocumented Sun
 * JDK High Performance Timer <code>code.misc.Perf</code>.
 *
 * @author void.fm
 * @version $Revision: 35 $
 */

public class SunHighResTimer implements ExecutionTimer {

  private static final String DESCRIPTION = "Sun High Resolution Timer (sun.misc.Perf)";
  private Perf perf = Perf.getPerf();
  private long ticksPerSecond;

  public SunHighResTimer() {
    ticksPerSecond = perf.highResFrequency();
  }

  public long getCurrentTime() {
    return perf.highResCounter();
  }

  public long getTicksPerSecond() {
    return ticksPerSecond;
  }

  public String toString() {
    return getClass().getName();
  }

  public TimerMetaData getMetaData() {
    return new TimerMetaData(SunHighResTimer.class,
      DESCRIPTION,
      getTicksPerSecond()
    );
  }
}
