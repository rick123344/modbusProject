package com.rick.util;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.WakeupException;

import com.rick.model.kafkaParamModel;

public class kafkaUtil{
	// Properties prop = null;
	public kafkaUtil(){
		
	}
	
	// public resetProperties(kafkaParamModel kafkaModel){
		// prop = new Properties();
		// prop.put("bootstrap.servers",kafkaModel.getIp()+":"+kafkaModel.getPort());
		// prop.put("group.id", kafkaModel.getGroupId());
		// prop.put("acks","all");
		// prop.put("retries", 0);
		// prop.put("batch.size", 16384);
		// prop.put("linger.ms", 1);
		// prop.put("buffer.memory", 33554432);
		// prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	// }
	
	
	public void producerSendMessage(kafkaParamModel kafkaModel){
		Properties prop = new Properties();
		prop.put("bootstrap.servers",kafkaModel.getIp()+":"+kafkaModel.getPort());
		prop.put("group.id", kafkaModel.getGroupId());
		prop.put("acks","all");
		prop.put("retries", 0);
		prop.put("batch.size", 16384);
		prop.put("linger.ms", 1);
		prop.put("buffer.memory", 33554432);
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = null;
		String msg = kafkaModel.getSingleMessage();
		
		try{
			producer = new KafkaProducer<>(prop);
			if(kafkaModel.getMultiMessage()!=null){
				for(String m : kafkaModel.getMultiMessage()){
					producer.send(new ProducerRecord<String,String>(kafkaModel.getTopic(),m));
				}
			}else{
				producer.send(new ProducerRecord<String,String>(kafkaModel.getTopic(),msg));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(producer!=null){
				producer.close();
			}
		}
	}
	
	public void consumerReceiveMessage(kafkaParamModel kafkaModel){
		Properties prop = new Properties();
		prop.put("bootstrap.servers",kafkaModel.getIp()+":"+kafkaModel.getPort());
		prop.put("group.id", kafkaModel.getGroupId());
		prop.put("acks","all");
		prop.put("retries", 0);
		prop.put("batch.size", 16384);
		prop.put("linger.ms", 1);
		prop.put("buffer.memory", 33554432);
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("key.deserializer", StringDeserializer.class.getName());
		prop.put("value.deserializer", StringDeserializer.class.getName());
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop); 
		consumer.subscribe(Arrays.asList("test","HelloKafka"));
		
		try{
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					Map<String, Object> data = new HashMap<>();
					data.put("partition", record.partition());
					data.put("offset", record.offset());
					data.put("value", record.value());
					System.out.println(data);
				}
			}
		}catch(WakeupException e){
			//ignore for shutdown
		}finally{
			consumer.close();
		}
		
	}
	
}