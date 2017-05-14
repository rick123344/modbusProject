package com.rick.model;
import lombok.Data;
import java.io.Serializable;
@Data
public class modbusParamModel implements Serializable {
	private String ip = "127.0.0.1";
	private int port = 502;
	private int slaveId = 1;
	private int registerAddress = 0;
	//getRegisterValue(index); => when count setting 1 , only get one data 
	private int count = 1;// the count of IR's to read(how much data you want to read)
	private int repeat = 10;
	
}