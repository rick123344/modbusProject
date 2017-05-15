package com.rick.originMethod;
import java.net.*;
import java.io.*;
import com.rick.util.modbusUtil;
import com.rick.model.modbusParamModel;
import java.util.concurrent.TimeUnit;
public class Origin{
	public Origin(){
		
	}
	
	public Thread getOriginSocketThread(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				Socket es= null;
				try {
					String ip_adrs = "127.0.0.1";
					int unit = Integer.parseInt("1");//slave id
					int reg_no = Integer.parseInt("10");//address
					int num_regs = Integer.parseInt("10");//count read
					System.out.println("ip_adrs = "+ip_adrs+" unit = "+unit+" reg_no = "+
					reg_no+" num_regs = "+num_regs);

					// set up socket
					es = new Socket(ip_adrs,502);
					OutputStream os= es.getOutputStream();
					FilterInputStream is = new BufferedInputStream(es.getInputStream());
					byte obuf[] = new byte[261];
					byte ibuf[] = new byte[261];
					int c = 0;
					int i;

					// build request of form 0 0 0 0 0 6 ui 3 rr rr nn nn
					for (i=0;i<5;i++) obuf[i] = 0;
					obuf[5] = 6;
					obuf[6] = (byte)unit;
					obuf[7] = 3;
					obuf[8] = (byte)(reg_no >> 8);
					obuf[9] = (byte)(reg_no & 0xff);
					obuf[10] = (byte)(num_regs >> 8);
					obuf[11] = (byte)(num_regs & 0xff);
					while(true){
						// send request
						os.write(obuf, 0, 12);
						// read response
						i = is.read(ibuf, 0, 261);
						if (i<9) {
							if (i==0) {
							  System.out.println("unexpected close of connection at remote end");
							} else {
							  System.out.println("response was too short - "+i+" chars");
							}
						} else if (0 != (ibuf[7] & 0x80)) {
							System.out.println("MODBUS exception response - type "+ibuf[8]);
						} else if (i != (9+2*num_regs)) {
							System.out.println("incorrect response size is "+i+" expected"+(9+2*num_regs));
						} else {
							System.out.println("ANOTHER");
							for (i=0;i<num_regs;i++) {
								int w = (ibuf[9+i+i]<<8) + ibuf[10+i+i];
								System.out.println("word "+i+" = "+w);
							}
						}
						TimeUnit.MILLISECONDS.sleep(300);
					}
					
					// close down
					// es.close();
				} catch (Exception e) {
					try{
						es.close();
					}catch(Exception ee){
						ee.printStackTrace();
					}
					
					System.out.println("exception :"+e);
				}
			}
		});
		return thread1;
	}
	
}