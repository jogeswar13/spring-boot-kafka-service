# kafka-service
Run the kafka-service in windows. This is for kafka 2.0+ version.

### ZooKeeper Installation
1. Go to your ZooKeeper config directory. For me its C:\zookeeper-3.4.7\conf
2. Rename file "zoo_sample.cfg" to "zoo.cfg"
3. Open zoo.cfg in any text editor, like Notepad; I prefer Notepad++.
4. Find and edit dataDir=/tmp/zookeeper to :\zookeeper-3.4.7\data 
5. Add an entry in the System Environment Variables as we did for Java.
    a. Add ZOOKEEPER_HOME = C:\zookeeper-3.4.7 to the System Variables.
    b. Edit the System Variable named "Path" and add ;%ZOOKEEPER_HOME%\bin;
6. You can change the default Zookeeper port in zoo.cfg file (Default port 2181).
7. Run ZooKeeper by opening a new cmd and type zkserver.

### Setting Up Kafka
1. Go to your Kafka config directory. For me its C:\kafka_2.11-0.9.0.0\config
2. Edit the file "server.properties"
3. Find and edit the line log.dirs=/tmp/kafka-logs" to "log.dir= C:\kafka_2.11-0.9.0.0\kafka-logs.
4. If your ZooKeeper is running on some other machine or cluster you can edit "zookeeper.connect:2181" to your custom IP and port. For this demo, we are using the same machine so there's no need to change. Also the Kafka port and broker.id are configurable in this file. Leave other settings as is.
5. Your Kafka will run on default port 9092 and connect to ZooKeeper’s default port, 2181.

### Running a Kafka Server
Important: Please ensure that your ZooKeeper instance is up and running before starting a Kafka server.

1. Go to your Kafka installation directory: C:\kafka_2.11-0.9.0.0\
2. Open a command prompt here by pressing Shift + right click and choose the "Open command window here" option).
3. Now type .\bin\windows\kafka-server-start.bat .\config\server.properties and press Enter.
4. Now your Kafka Server is up and running, you can create topics to store messages. Also, we can produce or consume data from Java or Scala code or directly from the command prompt.

### Creating Topics
1. Now create a topic with the name "test" and a replication factor of 1, as we have only one Kafka server running. If you have a cluster with more than one Kafka server running, you can increase the replication-factor accordingly, which will increase the data availability and act like a fault-tolerant system.
2. Open a new command prompt in the location C:\kafka_2.11-0.9.0.0\bin\windows.
3. Type the following command and hit Enter:
    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

### Creating a Producer and Consumer to Test Server
1. Open a new command prompt in the location C:\kafka_2.11-0.9.0.0\bin\windows
2. To start a producer type the following command:
    kafka-console-producer.bat --broker-list localhost:9092 --topic test
3. Again open a new command prompt in the same location as C:\kafka_2.11-0.9.0.0\bin\windows
4. Now start a consumer by typing the following command:
    kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test
5. Now you will have two command prompts.
6. Now type anything in the producer command prompt and press Enter, and you should be able to see the message in the other consumer command prompt.
7. If you are able to push and see your messages on the consumer side, you are done with Kafka setup.

### Some Other Useful Commands
1. List Topics: kafka-topics.bat --list --zookeeper localhost:2181
2. Describe Topic:
    kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]
3. Read messages from the beginning
    kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic [Topic Name] --from-beginn
4. Delete Topic:
    kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181

## Once the Kafka server setup is done. You can run the application and do the check.

### Reference
https://dzone.com/articles/running-apache-kafka-on-windows-os




