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
	private String singleMessage = "";
	private List<String> multiMessage = null;
}