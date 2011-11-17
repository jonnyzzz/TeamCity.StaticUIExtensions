Plugin for TeamCity for customizing TeamCity server pages with include of some custom content.

Requirements:
=============
TeamCity 6.5 or newer

License:
========
Apache 2.0

Builds:
=======
Download from  http://teamcity.jetbrains.com/viewType.html?buildTypeId=bt334&tab=buildTypeStatusDiv

To build locally, open the project with IntelliJ IDEA, make plugin-zip artifact. 
You may also need to set up TeamCityDistribution path variable in IDEA to point to 
unpacked .exe or .tar.gz TeamCity distribution. 

Usage:
=======

Configuration file of plugin is available under: 

[TeamCity Data Directory]/config/_static_ui_extensions/static-ui-extensions.xml


In the configuration file you may specify page place and static file that is included.
HTML, css or js resources are supported. 


This is a sample rule:

     <rule place-id="[put place id here]" 
           html-file="[put html file from this folder]"
           js-file="[put js file from this folder]"
           css-files="[put css file from this folder]">
         
         this is the rule to make page place work only for URLs that starts with
         <url starts="overview.html" />

         this is the rule to only include content if URL is equal to 
         <url equals="viewType.html" />

         you may add as much rules / url constraints as you like.
     </rule>


The full list of page extensions is available at:
http://javadoc.jetbrains.net/teamcity/openapi/current/jetbrains/buildServer/web/openapi/PlaceId.html

The full list of supported page places you may find in: page-places-list.txt that is generated automatically on server start


Sample usages:
==============
 - add google analytics to TeamCity installation
 - add instance-specific info/announcement


