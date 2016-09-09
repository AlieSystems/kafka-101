# Kafka-101 

Un exemple de producteur/consommateur Kafka avec schema-registry sur la plateforme confluent.io 3.0

### Étape 0: Installer Kafka

```shell
$ wget http://packages.confluent.io/archive/3.0/confluent-3.0.1-2.11.zip

$ unzip confluent-3.0.1-2.11.zip

$ cd confluent-3.0.1
```


### Étape 1: Lancer Kafka

```shell
# Lancer Zookeeper
$ bin/zookeeper-server-start ./etc/kafka/zookeeper.properties

# Lancer Kafka  (dans une nouvelle console)
$ bin/kafka-server-start ./etc/kafka/server.properties

# Lancer Schema Registry (dans une nouvelle console)
$ bin/schema-registry-start ./etc/schema-registry/schema-registry.properties
```

### Étape 2: Créer le topic

```shell
$ bin/kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic

#Lister les topics
$ bin/kafka-topics --list --zookeeper localhost:2181
```

### Étape 4: Compilation & Run

```shell
$ git clone https://github.com/oraclewalid/kafka-101.git

$ cd kafka-101

$ mvn clean package

# Lancer Consumer
$ mvn exec:java -Dexec.mainClass="com.fabulouslab.kafka.Consumer"
```

```shell
# Lancer Producer (dans une nouvelle console)
$ mvn exec:java -Dexec.mainClass="com.fabulouslab.kafka.Producer"
```