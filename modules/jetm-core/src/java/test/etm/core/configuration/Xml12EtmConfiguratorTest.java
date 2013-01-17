package test.etm.core.configuration;

import etm.core.aggregation.Aggregator;
import etm.core.aggregation.BufferedThresholdAggregator;
import etm.core.aggregation.BufferedTimedAggregator;
import etm.core.aggregation.persistence.PersistentRootAggregator;
import etm.core.configuration.EtmManager;
import etm.core.configuration.XmlEtmConfigurator;
import etm.core.jmx.EtmMonitorJmxPlugin;
import etm.core.metadata.AggregatorMetaData;
import etm.core.metadata.PluginMetaData;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.FlatMonitor;
import etm.core.monitor.NestedMonitor;
import etm.core.plugin.EtmPlugin;
import etm.core.timer.DefaultTimer;
import junit.framework.TestCase;
import test.etm.core.configuration.mockup.TestAggregator;
import test.etm.core.configuration.mockup.TestMonitor;
import test.etm.core.configuration.mockup.TestPlugin;
import test.etm.core.configuration.mockup.TestTimer;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Testing XML based configuration.
 *
 * @author void.fm
 * @version $Revision: 230 $
 */
public class Xml12EtmConfiguratorTest extends TestCase {

  public void testConfigFromString() {
    String config = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<!DOCTYPE jetm-config PUBLIC \"-// void.fm //DTD JETM Config 1.2//EN\" \"http://jetm.void.fm/dtd/jetm_config_1_2.dtd\">\n" +
      "<jetm-config type=\"flat\" />\n";

    EtmManager.reset();
    XmlEtmConfigurator.configure(config);

    EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
    assertEquals(FlatMonitor.class, etmMonitor.getClass());

  }

  public void testMonitorConfig() throws Exception {
    Object[][] configurations = new Object[][]{
      new Object[]{
        "test/etm/core/configuration/files/valid_1_2/flat-type-config.xml", FlatMonitor.class
      },
      new Object[]{
        "test/etm/core/configuration/files/valid_1_2/nested-type-config.xml", NestedMonitor.class
      },
      new Object[]{
        "test/etm/core/configuration/files/valid_1_2/monitor-class-config.xml", TestMonitor.class
      }
    };

    for (int i = 0; i < configurations.length; i++) {
      URL url = locateResource((String) configurations[i][0]);
      EtmManager.reset();
      XmlEtmConfigurator.configure(url);

      EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
      assertEquals(configurations[i][1], etmMonitor.getClass());
    }
  }


  public void testTimerConfig() throws Exception {
    Object[][] configurations = new Object[][]{
      new Object[]{
        "test/etm/core/configuration/files/valid_1_2/default-timer-config.xml", DefaultTimer.class
      },
      new Object[]{
        "test/etm/core/configuration/files/valid_1_2/timer-class-config.xml", TestTimer.class
      }
    };

    for (int i = 0; i < configurations.length; i++) {
      URL url = locateResource((String) configurations[i][0]);
      EtmManager.reset();
      XmlEtmConfigurator.configure(url);

      EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
      assertEquals(configurations[i][1], ((TestMonitor) etmMonitor).getExecutionTimer().getClass());
    }
  }

  public void testIntervalBuffer() throws Exception {
    URL url = locateResource("test/etm/core/configuration/files/valid_1_2/interval-buffer.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);

    EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
    AggregatorMetaData metaData = etmMonitor.getMetaData().getAggregatorMetaData();

    assertEquals(BufferedTimedAggregator.class, metaData.getImplementationClass());
  }

  public void testAggregatorConfig() throws Exception {
    URL url = locateResource("test/etm/core/configuration/files/valid_1_2/aggregator-config.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);

    EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
    Aggregator aggregator = ((TestMonitor) etmMonitor).getAggregator();

    assertEquals(TestAggregator.class, aggregator.getClass());
    TestAggregator testAggregator = (TestAggregator) aggregator;
    assertEquals(true, testAggregator.isBooleanTrue());
    assertEquals(false, testAggregator.isBooleanFalse());
    assertEquals(12, testAggregator.getIntValue());
    assertEquals(12124234324234L, testAggregator.getLongValue());
    assertEquals("testString", testAggregator.getStringValue());
    assertEquals(HashMap.class, testAggregator.getClazzValue());

    Map map = testAggregator.getMapValue();
    assertNotNull(map);
    assertEquals(3, map.size());
    assertEquals(map.get("key1"), "value1");
    assertEquals(map.get("key2"), "value2");
    assertEquals(map.get("key3"), "value3");

    List list = testAggregator.getListValue();
    assertNotNull(list);
    assertEquals(2, list.size());
    assertEquals(list.get(0), "value1");
    assertEquals(list.get(1), "value2");

  }

  public void testPluginConfig() throws Exception {
    URL url = locateResource("test/etm/core/configuration/files/valid_1_2/plugin-config.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);

    EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
    List plugins = ((TestMonitor) etmMonitor).getPlugins();

    assertTrue(plugins.size() > 0);

    EtmPlugin plugin = (EtmPlugin) plugins.get(0);

    assertEquals(TestPlugin.class, plugin.getClass());
    TestPlugin testPlugin = (TestPlugin) plugin;
    assertEquals(true, testPlugin.isBooleanTrue());
    assertEquals(false, testPlugin.isBooleanFalse());
    assertEquals(12, testPlugin.getIntValue());
    assertEquals(12124234324234L, testPlugin.getLongValue());
    assertEquals("testString", testPlugin.getStringValue());
    assertEquals(HashMap.class, testPlugin.getClazzValue());


    Map map = testPlugin.getMapValue();
    assertNotNull(map);
    assertEquals(3, map.size());
    assertEquals(map.get("key1"), "value1");
    assertEquals(map.get("key2"), "value2");
    assertEquals(map.get("key3"), "value3");

    List list = testPlugin.getListValue();
    assertNotNull(list);
    assertEquals(2, list.size());
    assertEquals(list.get(0), "value1");
    assertEquals(list.get(1), "value2");

    etmMonitor.start();
    assertTrue(testPlugin.isStarted());

    etmMonitor.stop();
    assertFalse(testPlugin.isStarted());
  }


  public void testAutostartConfig() throws Exception {
    URL url = locateResource("test/etm/core/configuration/files/valid_1_2/autostart-on-config.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);

    EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
    assertTrue(etmMonitor.isStarted());
    assertTrue(etmMonitor.isCollecting());

    etmMonitor.stop();

    url = locateResource("test/etm/core/configuration/files/valid_1_2/autostart-off-config.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);
    etmMonitor = EtmManager.getEtmMonitor();
    assertFalse(etmMonitor.isStarted());
    assertTrue(etmMonitor.isCollecting());
  }


  public void testFeatures() throws Exception {

    MBeanServer server = MBeanServerFactory.createMBeanServer();
    try {
      URL url = locateResource("test/etm/core/configuration/files/valid_1_2/features.xml");
      EtmManager.reset();
      XmlEtmConfigurator.configure(url);

      EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

      etmMonitor.start();

      AggregatorMetaData bufferMetaData = etmMonitor.getMetaData().getAggregatorMetaData();
      assertEquals(BufferedThresholdAggregator.class, bufferMetaData.getImplementationClass());

      assertNotNull(bufferMetaData.getNestedMetaData());


      AggregatorMetaData rootMetaData = bufferMetaData.getNestedMetaData();
      assertEquals(PersistentRootAggregator.class, rootMetaData.getImplementationClass());

      List list = etmMonitor.getMetaData().getPluginMetaData();
      assertEquals(1, list.size());

      PluginMetaData pluginMetaData = (PluginMetaData) list.get(0);
      assertEquals(EtmMonitorJmxPlugin.class, pluginMetaData.getImplementationClass());

      Map properties = pluginMetaData.getProperties();
      assertEquals("test:service=Foo", properties.get("monitorObjectName"));
      assertEquals("test.performance", properties.get("measurementDomain"));
      assertEquals("true", properties.get("overwrite"));

      etmMonitor.stop();
    } finally {
      MBeanServerFactory.releaseMBeanServer(server);
    }
  }

  public void testCustomBackend() throws Exception {
    URL url = locateResource("test/etm/core/configuration/files/valid_1_2/features-custom-persistence.xml");
    EtmManager.reset();
    XmlEtmConfigurator.configure(url);

    TestMonitor etmMonitor = (TestMonitor) EtmManager.getEtmMonitor();
    etmMonitor.start();
    assertEquals(PersistentRootAggregator.class, etmMonitor.getAggregator().getMetaData().getNestedMetaData().getImplementationClass());

    etmMonitor.stop();
  }

  private URL locateResource(String classPathName) throws IOException {
    URL url = getClass().getClassLoader().getResource(classPathName);
    if (url != null) {
      return url;
    } else {
      throw new FileNotFoundException(classPathName);
    }
  }


}
