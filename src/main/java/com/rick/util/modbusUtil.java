package com.rick.util;

import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

import com.rick.model.modbusParamModel;

public class modbusUtil{
	
	private TCPMasterConnection con = null; //the connection
	private ModbusTCPTransaction trans = null; //the transaction
	
	// private String ip = "127.0.0.1";
	// private int port = 502;
	// private int slaveId = 1;
	// private int registerAddress = 0;
	// private int count = 1;// the count of IR's to read
	// private int repeat = 10;
	
	public modbusUtil(){}
	
	//single bit(1,0), read only , Digital
	public String readDiscreteInput(modbusParamModel mpm){
		
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
		System.out.println("\nInput Register.\n");
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
				for(int i = 0; i<mpm.getCount(); i++){
					System.out.print(", "+res.getRegisterValue(i));
				}
				System.out.println("");
				k++;
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
	
}