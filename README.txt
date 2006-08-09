README.txt - Antomology
=======================

1. What is it?

   Antomology, besides being a word play on Entomology, is an Ant analysis tool 
   suite.  The current suite offering is:
  
     a. StatisticsListener - an Ant BuildListener which can be used to gather 
          statistics while an Ant build is executed.  Statistics on the targets
          and tasks executed are written to the console after the build completes.
          Some of the statistics captured are:
          
          i.   the number of times a target / task is called
          ii.  the average processing time spent on a target / task
          iii. the total processing time spent on a target / task
          iv.  the total processing time spent on a target / task expressed as a 
                 percentage

2. How do I build the source

   Simply execute ant in the same directory as the build.xml.

3. How do I use the StatisticsListener?

   > set CLASSPATH=${dir.antomology}/build/dist/antomology-bin-${project.version}.jar
   > ant -listener antomology.StatisticsListener <target>

    
4. What license is Antomology released under?

   Apache License, Version 2.0