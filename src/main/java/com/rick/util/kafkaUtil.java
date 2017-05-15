package com.rick.util;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
	private Properties prop = null;
	private String topic = "";
	private List<String> topics = new ArrayList<String>();
	public kafkaUtil(kafkaParamModel kafkaModel){
		resetProperties(kafkaModel);
	}
	
	public void resetProperties(kafkaParamModel kafkaModel){
		prop = new Properties();
		prop.put("bootstrap.servers",kafkaModel.getIp()+":"+kafkaModel.getPort());
		prop.put("group.id", kafkaModel.getGroupId());
		prop.put("acks",kafkaModel.getAcks());
		prop.put("retries", kafkaModel.getRetries());
		prop.put("batch.size", kafkaModel.getBatchSize());
		prop.put("linger.ms", kafkaModel.getLingerMs());
		prop.put("buffer.memory", kafkaModel.getBufferMemory());
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.put("key.deserializer", StringDeserializer.class.getName());
		prop.put("value.deserializer", StringDeserializer.class.getName());
		topic = kafkaModel.getTopic();
		topics = kafkaModel.getTopicsArray();
	}
	
	public void producerSendMessage(List<String> msg){
		Producer<String, String> producer = null;
		try{
			producer = new KafkaProducer<>(prop);
			for(String m : msg){
				producer.send(new ProducerRecord<String,String>(topic,m));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(producer!=null){
				producer.close();
			}
		}
	}
	
	public void consumerReceiveMessage(){
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop); 
		consumer.subscribe(topics);
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