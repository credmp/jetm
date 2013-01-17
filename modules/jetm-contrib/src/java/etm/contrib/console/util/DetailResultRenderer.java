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

package etm.contrib.console.util;

import etm.contrib.console.ConsoleRequest;
import etm.contrib.console.ConsoleResponse;
import etm.contrib.util.ExecutionAggregateComparator;
import etm.core.aggregation.Aggregate;
import etm.core.monitor.EtmException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Renderes a result for a measurement point.
 *
 * @author void.fm
 * @version $Revision: 189 $
 */
public class DetailResultRenderer extends ConsoleRenderer {

  private String etmPointName;

  public DetailResultRenderer(ConsoleRequest aRequest, ConsoleResponse aResponse,
                              ExecutionAggregateComparator aComparator, String aEtmPointName) {
    super(aRequest, aResponse, aComparator);
    etmPointName = aEtmPointName;
  }

  public void render(Map points) {
    Aggregate point = (Aggregate) points.get(etmPointName);

    try {
      writeDetailHtmlHead(etmPointName);

      response.write("<!-- Begin results -->");      
      response.write("<table>\n");
      writeTableHeader();

      if (point == null) {
        response.write(NO_RESULTS.toCharArray());
      } else {
        SortedExecutionGraph graphSorted = new SortedExecutionGraph(point, comparator);

        response.write(" <tr>\n");
        response.write("  <td>");
        writeName(graphSorted, 0);
        response.write("</td>\n");
        response.write("  <td>");
        writeMeasurements(graphSorted, 0);
        response.write("</td>\n");
        response.write("  <td>");
        writeAverage(graphSorted, 0);
        response.write("</td>\n");
        response.write("  <td>");
        writeMin(graphSorted, 0);
        response.write("</td>\n");
        response.write("  <td>");
        writeMax(graphSorted, 0);
        response.write("</td>\n");
        response.write("  <td>");
        writeTotals(graphSorted, 0);
        response.write("</td>\n");
        response.write(" </tr>\n");
      }

      response.write(FOOTER);
      response.write("</table>\n");
      response.write(" </body>\n</html>");

    } catch (IOException e) {
      throw new EtmException("Unable to write to writer: " + e);
    }
  }


  protected void writeTableHeader() throws IOException {

    response.write(" <tr>\n");

    response.write("  <th width=\"200\" ");
    if (ExecutionAggregateComparator.TYPE_NAME == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=name&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Measurement Point</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=name&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Measurement Point</a>");
      }
    } else {
      response.write("><a href=\"?sort=name&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Measurement Point</a>");

    }
    response.write("</th>\n");

    response.write("  <th width=\"30\" ");
    if (ExecutionAggregateComparator.TYPE_EXCECUTIONS == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=executions&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">#</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=executions&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">#</a>");
      }
    } else {
      response.write("><a href=\"?sort=executions&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">#</a> ");
    }
    response.write("</th>\n");


    response.write("  <th width=\"80\" ");
    if (ExecutionAggregateComparator.TYPE_AVERAGE == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=average&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Average</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=average&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Average</a>");
      }
    } else {
      response.write("><a href=\"?sort=average&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Average</a> ");
    }
    response.write("</th>\n");

    response.write("  <th width=\"80\" ");
    if (ExecutionAggregateComparator.TYPE_MIN == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=min&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Min</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=min&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Min</a>");
      }
    } else {
      response.write("><a href=\"?sort=min&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Min</a> ");
    }
    response.write("</th>\n");

    response.write("  <th width=\"80\" ");
    if (ExecutionAggregateComparator.TYPE_MAX == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=max&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Max</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=max&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Max</a>");
      }
    } else {
      response.write("><a href=\"?sort=max&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Max</a>");
    }
    response.write("</th>\n");

    response.write("  <th width=\"80\" ");
    if (ExecutionAggregateComparator.TYPE_TOTAL == comparator.getType()) {
      if (comparator.isDescending()) {
        response.write("class=\"descending\"><a href=\"?sort=total&amp;order=asc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Total</a>");
      } else {
        response.write("class=\"ascending\"><a href=\"?sort=total&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Total</a>");
      }
    } else {
      response.write("><a href=\"?sort=total&amp;order=desc&amp;point=" + URLEncoder.encode(etmPointName, "UTF-8") + "\">Total</a> ");
    }
    response.write("</th>\n");

    response.write(" </tr>\n");
  }


}