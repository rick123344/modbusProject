package com.rick;

import com.rick.util.modbusUtil;
import com.rick.util.kafkaUtil;
import com.rick.model.modbusParamModel;
import com.rick.model.kafkaParamModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;
import java.util.Date;

import java.lang.Thread;
import java.lang.Runnable;
public class Main{
	private modbusUtil mb;
	private kafkaUtil kafka;
	Main(){
		
		kafkaTest();
		//modbusTest();
	}
	
	public void kafkaTest(){
		kafkaParamModel kpm  = new kafkaParamModel();
		kpm.setTopicsArray(Arrays.asList("test","Default"));
		kpm.setTopic("test");
		kafka = new kafkaUtil(kpm);
		
		kafka.producerSendMessage(Arrays.asList("this","is","testu..."));
		kafka.consumerReceiveMessage();
		
	}
	
	public void modbusTest(){
		mb = new modbusUtil();
		// read Digital Input
		// modbusParamModel m1 = new modbusParamModel();
		// m1.setCount(20);
		// mb.readDiscreteInput(m1);
		
		// read Coil  
		// modbusParamModel m2 = new modbusParamModel();
		// m2.setRegisterAddress(0);
		// m2.setCount(20);
		// m2.setIp("172.20.10.2");//192.168.30.40
		// mb.readDiscreteOutputCoil(m2);
		
		// read Input Register
		// modbusParamModel m3 = new modbusParamModel();
		// m3.setRegisterAddress(0);
		// m3.setCount(20);
		// m3.setIp("172.20.10.2");//192.168.30.40
		// mb.readInputRegister(m3);
		
		// read Holding Register
		// modbusParamModel m = new modbusParamModel();
		// m.setRegisterAddress(0);
		// m.setSlaveId(1);
		// m.setCount(20);
		// m.setIp("10.1.21.12");//192.168.30.40
		// mb.readHoldingRegister(m);
		
		
		// modbusParamModel m6 = new modbusParamModel();
		// m6.setRegisterAddress(0);
		// m6.setSlaveId(2);
		// m6.setCount(20);
		// m6.setPort(503);
		// m6.setIp("10.1.21.12");//192.168.30.40
		// mb.readHoldingRegister(m6);
		
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				modbusParamModel m = new modbusParamModel();
				m.setRegisterAddress(0);
				m.setSlaveId(1);
				m.setCount(20);
				mb.readHoldingRegisterToKafka(m);
			}
		});
		
		// Thread thread2 = new Thread(new Runnable(){
			// public void run(){
				// modbusParamModel m6 = new modbusParamModel();
				// m6.setRegisterAddress(0);
				// m6.setSlaveId(2);
				// m6.setCount(20);
				// m6.setPort(503);
				//m6.setIp("10.1.21.12");//192.168.30.40
				// mb.readHoldingRegister(m6);
			// }
		// });
		
		thread1.start();
		//thread2.start();
	}
	
	
	public static void main(String[] args){
		new Main();
	}
}