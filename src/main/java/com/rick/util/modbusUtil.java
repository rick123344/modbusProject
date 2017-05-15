package com.rick.util;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

import com.rick.model.kafkaParamModel;
import com.rick.model.modbusParamModel;

public class modbusUtil{
	private kafkaUtil kafka = null;
	public modbusUtil(){}
	
	//single bit(1,0), read only , Digital
	public String readDiscreteInput(modbusParamModel mpm){
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		try{
			//prepare addr 
			InetAddress addr = InetAddress.getByName(mpm.getIp());
			//prepare connect
			con = new TCPMasterConnection(addr);
			con.setPort(mpm.getPort());
			con.connect();
			//prepare request
			ReadInputDiscretesRequest req = new ReadInputDiscretesRequest(mpm.getRegisterAddress(),mpm.getCount());
			req.setUnitID(mpm.getSlaveId());
			//prepare transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			//execute and getReponse
			ReadInputDiscretesResponse res = null;
			int k = 0, repeat = mpm.getRepeat();
			do {
				trans.execute();
				res = (ReadInputDiscretesResponse) trans.getResponse();
				System.out.println("Digital Inputs Status=" + res.getDiscretes().toString());
				k++;
				TimeUnit.MILLISECONDS.sleep(300);
			} while (k < repeat);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				//Close the connection
				con.close();
			}
		}
		
		return "";
	}
	
	//single bit(1,0), read - write , Coil
	public String readDiscreteOutputCoil(modbusParamModel mpm){
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		try{
			//prepare addr 
			InetAddress addr = InetAddress.getByName(mpm.getIp());
			//prepare connect
			con = new TCPMasterConnection(addr);
			con.setPort(mpm.getPort());
			con.connect();
			//prepare request
			ReadCoilsRequest req = new ReadCoilsRequest(mpm.getRegisterAddress(),mpm.getCount());
			req.setUnitID(mpm.getSlaveId());
			//prepare transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			//execute and getReponse
			ReadCoilsResponse res = null;
			int k = 0, repeat = mpm.getRepeat();
			do {
				trans.execute();
				res = (ReadCoilsResponse) trans.getResponse();
				System.out.println("Digital Inputs Status=" + res.getCoils().toString());
				k++;
				TimeUnit.MILLISECONDS.sleep(300);
			} while (k < repeat);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				//Close the connection
				con.close();
			}
		}
		return "";
	}
	
	//16bit word, read only, analog , analogue inputs
	public String readInputRegister(modbusParamModel mpm){
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		try{
			//prepare addr 
			InetAddress addr = InetAddress.getByName(mpm.getIp());
			//prepare connect
			con = new TCPMasterConnection(addr);
			con.setPort(mpm.getPort());
			con.connect();
			//prepare request
			ReadInputRegistersRequest req = new ReadInputRegistersRequest(mpm.getRegisterAddress(),mpm.getCount());
			req.setUnitID(mpm.getSlaveId());
			//prepare transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			//execute and getReponse
			ReadInputRegistersResponse res = null;
			int k = 0, repeat = mpm.getRepeat();
			do {
				trans.execute();
				res = (ReadInputRegistersResponse) trans.getResponse();
				for(int i = 0; i<mpm.getCount(); i++){
					System.out.print(", "+res.getRegisterValue(i));
				}
				System.out.println("");
				k++;
				TimeUnit.MILLISECONDS.sleep(300);
			} while (k < repeat);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				//Close the connection
				con.close();
			}
		}
		
		return "";
	}
	
	//16bit word, read - write, analog
	public String readHoldingRegister(modbusParamModel mpm){
		String result = "";
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		try{
			//prepare addr 
			InetAddress addr = InetAddress.getByName(mpm.getIp());
			//prepare connect
			con = new TCPMasterConnection(addr);
			con.setPort(mpm.getPort());
			con.connect();
			//prepare request
			ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(mpm.getRegisterAddress(),mpm.getCount());
			req.setUnitID(mpm.getSlaveId());
			//prepare transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			//execute and getReponse
			ReadMultipleRegistersResponse res = null;
			int k = 0, repeat = mpm.getRepeat();
			do {
				trans.execute();
				res = (ReadMultipleRegistersResponse) trans.getResponse();
				System.out.println(mpm.getPort()+" : ");
				for(int i = 0; i<mpm.getCount(); i++){
					System.out.print(", "+res.getRegisterValue(i));
					result += res.getRegisterValue(i);
				}
				System.out.println("");
				k++;
				TimeUnit.MILLISECONDS.sleep(300);
			} while (k < repeat);
			// while(true){
				// trans.execute();
				// res = (ReadMultipleRegistersResponse) trans.getResponse();
				// System.out.println(mpm.getPort()+" : ");
				// for(int i = 0; i<mpm.getCount(); i++){
					// System.out.print(", "+res.getRegisterValue(i));
				// }
				// System.out.println("");
				// k++;
				// TimeUnit.MILLISECONDS.sleep(300);
			// }
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				//Close the connection
				con.close();
			}
		}
		
		return result;
	}
	
	
	public String readHoldingRegisterToKafka(modbusParamModel mpm){
		
		kafkaParamModel kpm  = new kafkaParamModel();
		kpm.setTopicsArray(Arrays.asList("test","Default"));
		kpm.setTopic("test");
		kafka = new kafkaUtil(kpm);
		
		String result = "";
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		try{
			//prepare addr 
			InetAddress addr = InetAddress.getByName(mpm.getIp());
			//prepare connect
			con = new TCPMasterConnection(addr);
			con.setPort(mpm.getPort());
			con.connect();
			//prepare request
			ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(mpm.getRegisterAddress(),mpm.getCount());
			req.setUnitID(mpm.getSlaveId());
			//prepare transaction
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			//execute and getReponse
			ReadMultipleRegistersResponse res = null;
			while(true){
				String str = "";
				trans.execute();
				res = (ReadMultipleRegistersResponse) trans.getResponse();
				for(int i = 0; i<mpm.getCount(); i++){
					str+=", "+res.getRegisterValue(i);
				}
				kafka.producerSendMessage(Arrays.asList(str));
				System.out.println("send : "+str);
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				//Close the connection
				con.close();
			}
		}
		
		return result;
	}
	
}