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

package etm.contrib.renderer;

import etm.core.aggregation.Aggregate;
import etm.core.monitor.EtmException;
import etm.core.renderer.MeasurementRenderer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p/>
 * The SimpleHtmlRenderer renders an html table fragment containing flat
 * or nested measurement results.
 * </p>
 * <p/>
 * We encourage to use CSS to change the visualization. Example CSS:
 * </p>
 * <pre>
 *   table {
 *     border: 2px solid #999;
 *     margin: 0px 0px 25px 0px;
 *     padding:  0px 0px 0px 0px;
 *     border-collapse: collapse;
 *   }
 * <p/>
 *   th {
 *     border: 1px solid #999;
 *     margin:  0px 0px 0px 0px;
 *     padding:  10px 5px 10px 5px;
 *   }
 * <p/>
 *   td {
 *     border: 1px solid #999;
 *     margin:  0px 0px 0px 0px;
 *     padding:  10px 15px 10px 10px;
 *   }
 * <p/>
 *   .childname {
 *     text-align: left;
 *     border-top: 1px dotted #999;
 *     position: relative;
 *     margin:  2px 0px 0px 15px;
 *   }
 * <p/>
 *   .parentname {
 *     text-align: left;
 *   }
 * <p/>
 *   .parentmeasurement {
 *     text-align: right;
 *   }
 * <p/>
 *   .childmeasurement {
 *     border-top: 1px dotted #999;
 *     text-align: right;
 *     margin:  2px 0px 0px 0px;
 *   }
 * <p/>
 *   .parenttime {
 *     text-align: right;
 *   }
 * <p/>
 *   .childtime {
 *     border-top: 1px dotted #999;
 *     text-align: right;
 *     margin:  2px 0px 0px 0px;
 *   }
 * <p/>
 *   .parenttotal {
 *     text-align: right;
 *   }
 * <p/>
 *   .childtotal {
 *     border-top: 1px dotted #999;
 *     text-align: right;
 *     margin:  2px 0px 0px 0px;
 *   }
 * <p/>
 * </pre>
 *
 * @author void.fm
 * @version $Revision: 126 $
 */
public class SimpleHtmlRenderer implements MeasurementRenderer {

  private static final String HEAD =
    "<table>\n" +
      " <tr>\n" +
      "  <th>Measurement Point</th>\n" +
      "  <th>#</th>\n" +
      "  <th>Average</th>\n" +
      "  <th>Min</th>\n" +
      "  <th>Max</th>\n" +
      "  <th>Total</th>\n" +
      " </tr>\n";

  private static final String FOOTER = " <tr><td class=\"footer\" colspan=\"6\">All times in miliseconds. Measurements provided by <a href=\"http://jetm.void.fm\" target=\"_default\">JETM</a></td></tr>\n</table>";
  private static final String NO_RESULTS = " <tr><td colspan=\"6\">No measurement results available.</td></tr>\n";

  private NumberFormat timeFormatter;
  private NumberFormat numberFormatter;
  private Writer writer;


  /**
   * Constructs a SimpleHtmlRenderer using the default locale
   * and the provided writer.
   *
   * @param aWriter The writer.
   */
  public SimpleHtmlRenderer(Writer aWriter) {
    this(aWriter, Locale.getDefault());
  }

  /**
   * Constructs a SimpleTextRenderer using the provided locale
   * and provided writer.
   *
   * @param aWriter The writer to write to.
   * @param aLocale The locale to use.
   */
  public SimpleHtmlRenderer(Writer aWriter, Locale aLocale) {
    writer = aWriter;
    timeFormatter = NumberFormat.getNumberInstance(aLocale);
    timeFormatter.setMaximumFractionDigits(3);
    timeFormatter.setMinimumFractionDigits(3);
    timeFormatter.setGroupingUsed(true);

    numberFormatter = NumberFormat.getNumberInstance(aLocale);
    numberFormatter.setMaximumFractionDigits(0);
    numberFormatter.setMinimumFractionDigits(0);
    numberFormatter.setGroupingUsed(true);
  }


  public void render(Map points) {

    try {
      writer.write(HEAD.toCharArray());

      if (points.size() == 0) {
        writer.write(NO_RESULTS.toCharArray());
      } else {
        StringBuffer buffer = new StringBuffer();

        TreeMap map = new TreeMap(points);
        for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
          Aggregate point = (Aggregate) iterator.next();

          buffer.append(" <tr>\n");
          buffer.append("  <td>");
          writeNames(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append("  <td>");
          writeMeasurements(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append("  <td>");
          writeAverage(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append("  <td>");
          writeMin(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append("  <td>");
          writeMax(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append("  <td>");
          writeTotals(buffer, point, 0);
          buffer.append("</td>\n");
          buffer.append(" </tr>\n");
        }
        writer.write(buffer.toString().toCharArray());
      }
      writer.write(FOOTER.toCharArray());
      writer.flush();
    } catch (IOException e) {
      throw new EtmException("Unable to write to writer: " + e);
    }
  }

  private void writeNames(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childname\" >");
    } else {
      aBuffer.append("<div class=\"parentname\" >");
    }

    aBuffer.append(aPoint.getName());

    if (aPoint.hasChilds()) {
      int currentDepth = depth + 1;

      Map childs = aPoint.getChilds();
      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeNames(aBuffer, child, currentDepth);
      }
    }

    aBuffer.append("</div>");
  }

  private void writeTotals(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childtotal\" >");
    } else {
      aBuffer.append("<div class=\"parenttotal\" >");
    }

    aBuffer.append(timeFormatter.format(aPoint.getTotal()));

    if (aPoint.hasChilds()) {
      Map childs = aPoint.getChilds();

      int currentDepth = depth + 1;

      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeTotals(aBuffer, child, currentDepth);
      }
    }

    aBuffer.append("</div>");
  }

  private void writeAverage(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childtime\" >");
    } else {
      aBuffer.append("<div class=\"parenttime\" >");
    }

    aBuffer.append(timeFormatter.format(aPoint.getAverage()));

    if (aPoint.hasChilds()) {
      Map childs = aPoint.getChilds();

      int currentDepth = depth + 1;
      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeAverage(aBuffer, child, currentDepth + 1);
      }
    }

    aBuffer.append("</div>");
  }

  private void writeMin(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childtime\" >");
    } else {
      aBuffer.append("<div class=\"parenttime\" >");
    }

    aBuffer.append(timeFormatter.format(aPoint.getMin()));

    if (aPoint.hasChilds()) {
      Map childs = aPoint.getChilds();

      int currentDepth = depth + 1;
      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeMin(aBuffer, child, currentDepth + 1);
      }
    }

    aBuffer.append("</div>");
  }

  private void writeMax(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childtime\" >");
    } else {
      aBuffer.append("<div class=\"parenttime\" >");
    }

    aBuffer.append(timeFormatter.format(aPoint.getMax()));

    if (aPoint.hasChilds()) {
      Map childs = aPoint.getChilds();

      int currentDepth = depth + 1;
      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeMax(aBuffer, child, currentDepth + 1);
      }
    }

    aBuffer.append("</div>");
  }


  private void writeMeasurements(StringBuffer aBuffer, Aggregate aPoint, int depth) {
    if (depth > 0) {
      aBuffer.append("<div class=\"childmeasurement\" >");
    } else {
      aBuffer.append("<div class=\"parentmeasurement\" >");
    }

    aBuffer.append(numberFormatter.format(aPoint.getMeasurements()));
    if (aPoint.hasChilds()) {
      Map childs = aPoint.getChilds();

      int currentDepth = depth + 1;
      for (Iterator iterator = childs.values().iterator(); iterator.hasNext();) {
        Aggregate child = (Aggregate) iterator.next();
        writeMeasurements(aBuffer, child, currentDepth);
      }
    }

    aBuffer.append("</div>");
  }

}
