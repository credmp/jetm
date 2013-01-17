<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en_US" xml:lang="en_US">
<head>
  <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
  <title>Java(TM) Execution Time Measurement Library</title>
  <link rel="stylesheet" type="text/css" href="default.css"/>
  <script type="text/javascript">
    function submit() {
      document.forms['loginForm'].submit();
    }

    function openConsole() {
      window.open('performance/index', 'console', 'width=900,left=0,top=0,scrollbars=true');
    }
  </script>
</head>

<body>
<div id="content">
  <div id="header">JETM Web Demo</div>
  <div id="subheader">Runtime performance monitoring made easy</div>
  <div id="main">
    <h3>JETM Online Demo</h3>

    <a onclick="openConsole(); return false;">
      <img height="80" style="float: left; margin-right: 15px; margin-bottom: 15px;" border="0"
           src="img/jetm_console_thumb.png"
           alt="JETM Console"/>
    </a>

    <p>
      Welcome to the JETM online demo.
    </p>


    <p>
      Before you proceed please click image to the left. It will open the JETM performance monitoring console in a separate window.
      Nevertheless you can always access it using the link 'Monitoring Console' in the bottom menu.
    </p>

    <p><br/> <br/> <br/> <br/></p>

    <div align="center" style="clear: left;"><a href="welcome.action"><b>Proceed to the demo</b></a></div>
    <p><br/> <br/></p>

    <div id="menu">
      <a href="welcome.action">Demo Home</a>
      |
      <a href="#" onclick="openConsole(); return false;">Monitoring Console</a>
      |
      <a href="http://jetm.void.fm">JETM Home</a>
    </div>
  </div>
</div>

</body>
<!-- Last modified  $Date: 2006/10/18 07:54:42 $ -->
</html>