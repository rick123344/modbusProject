package com.rick.model;
import java.util.List;
import lombok.Data;
import java.io.Serializable;
@Data
public class kafkaParamModel implements Serializable {
	private String ip = "127.0.0.1";
	private int port = 9092;
	private String groupId = "cooperate";
	private String topic = "Default";
	private String acks = "all";
	private int retries = 0;
	private int batchSize = 16384;
	private int lingerMs = 1;
	private int bufferMemory = 33554432;
	private List<String> topicsArray = null;
}