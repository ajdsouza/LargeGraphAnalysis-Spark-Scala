Large Graph Analysis Using Spark and Scala - Using Map/ReduceByKey
------------------------------------------------------------------

Get the total edge weight by Node for all the incomming edges to a Node

Read the file and transform RDD by split each line and form tuple of target_node,edge_weight from last two tokens. 
Similar to mapper in mapreduce.  
Sum all the weights for a given target_node across tuples using reduceByKey.
This is similar to reduce step in mapreduce.
Scala MR has brevity and can cache the RDD for speed.


Input is of the following form
src tgt weight
51 117 1
51 194 1
51 299 3
151 230 51
151 194 79
130 51 10

 - Code accepts two arguments upon running. The first argument (args[0]) will be a
path for the input graph file on HDFS, and the second argument (args[1]) will be a path for
output directory. The default output mechanism of Hadoop will create multiple files on the output
directory such as part00000, part00001, which will be merged and downloaded to a local 
directory by the run script. 

The format of the output is as follows. 
  - Each line represents a node ID and the sum of its incoming edges’ weights. 
  - The ID and the sum are separated by a tab(\t), and lines are not sorted. 
The following example result computed based on the toy graph above.
51 10
117 1
194 80
230 51
299 3

Nodes with no incoming edges (i.e. the sum equals zero) are excluded.

Directory Structure
-------------------
1.
Compiling the Source to get a Jar to deploy on hdfs
pom.xml contains necessary dependencies and compile configurations for each task.
To compile, you can simply call Maven in the corresponding directory (Task1 or
Task2 where pom.xml exists) by this command:
mvn package


2.
Distributing up the data files on HDFS for each node
sudo su hdfs
hadoop fs mkdir
/user/cse6242/
hadoop fs chown
cloudera /user/cse6242/
exit
su cloudera
hadoop fs put
graph1.tsv /user/cse6242/graph1.tsv
hadoop fs put
graph2.tsv /user/cse6242/graph2.tsv


3.
Running the jar using the run<>.sh script
1) run<>.sh will run JAR on Hadoop/Scala specifying the input file on HDFS (the first
argument) and output directory on HDFS (the second argument)
2) It will merge outputs from output directory and download to local file system.
3) remove the output directory on HDFS.


- MapReduce Implemented in Scala on Spark, Using HDFS Using Map and Reduce By Key
