package com.rick;

import com.rick.util.modbusUtil;
import com.rick.model.modbusParamModel;

public class Main{
	private modbusUtil mb;
	Main(){
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
		modbusParamModel m = new modbusParamModel();
		m.setRegisterAddress(0);
		m.setSlaveId(255);
		m.setCount(20);
		m.setIp("127.0.0.1");//192.168.30.40
		mb.readHoldingRegister(m);
	}
	
	public static void main(String[] args){
		new Main();
	}
}