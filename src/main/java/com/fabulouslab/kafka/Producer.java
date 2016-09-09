package com.fabulouslab.kafka;


import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.io.IOException;
import java.util.Properties;

public class Producer {

    public static final String TOPIC = "mytopic";

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", KafkaAvroSerializer.class.getName());
        props.put("value.serializer", KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://localhost:8081");

        String schemaString = "{\"namespace\": \"customerManagement.avro\"," +
                "\"type\": \"record\", " +
                "\"name\": \"Customer\"," +
                "\"fields\": [" +
                "{\"name\": \"id\", \"type\": \"int\"}," +
                "{\"name\": \"name\", \"type\": \"string\"}," +
                "{\"name\": \"email\", \"type\": [\"null\",\"string\"], \"default\":\"null\" }" +
                "]" +
                "}";

        KafkaProducer<String, GenericRecord> producer = new KafkaProducer(props);
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(schemaString);

        try {
            int customers = 10;
            for (int userId = 0; userId < customers; userId++) {
                String name = "user" + userId;
                String email = name + "@fabulouslab.com";
                GenericRecord user = new GenericData.Record(schema);
                user.put("id", userId);
                user.put("name", name);
                user.put("email", email);
                ProducerRecord<String, GenericRecord> data = new ProducerRecord(TOPIC, name, user);
                producer.send(data);
            }
        } catch (Exception ex) {
            System.err.printf(ex.getMessage());
        } finally {
            producer.close();
        }

    }
}
