package com.bit2015.network1006.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPEchoWhileServer {
	private static final int PORT = 60000;
	private static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		try {
			//1. UDP 서버 소켓 생성
			datagramSocket = new DatagramSocket(PORT);

			//2. 수신 대기
			log("Packet 수신 대기");
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE );
			//UDP 의 단점. TCP IP 는 크기 상관없이 다 담아줄수 있는데 
			// 여기는 receive buffer , senderbuffer 사이즈 맞춰줘야되고. 고정되어 있음.
			byte[] sendData; 
			while(true){
				datagramSocket.receive( receivePacket);

				// 3. 수신데이터 출력
				// System.out.println(receivePacket.toString()); // 결과 :
				// java.net.DatagramPacket@70dea4e
				// System.out.println(receivePacket.getData().toString()); //결과
				// : [B@5c647e05
				// ToString 도 만능은 아니구나
				String message = new String(receivePacket.getData(), 0,
						receivePacket.getLength(), "UTF-8");
				sendData = receivePacket.getData();
				
				log("Packet 수신 : " + message);

				if("serverExit".equals(message)){
					log("serverExit Message 받아서 서버 종료합니다");
					break;
				}
				if("time".equals(message)){
					//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					//String data = format.format(new Date());
					
				//	sendData = data.getBytes();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					String data = format.format(new Date());
					DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.length(), receivePacket.getAddress(), receivePacket.getPort());
					datagramSocket.send(sendPacket);
					continue;
				}
				// 4. Data 보내기
				// DatagramPacket sendPacket = new DatagramPacket(message,
				// message.length());
				
				DatagramPacket sendPacket = new DatagramPacket(
						sendData, receivePacket.getLength(),
						receivePacket.getAddress(), receivePacket.getPort());
				datagramSocket.send(sendPacket);

			}
			//5. 자원정리
//			datagramSocket.close();
			
		} catch (SocketException e) {
			log("Exception Error @1 : "+e);
		} catch (IOException e){
			log("Exception Error @2 : "+e);
		}finally{
			datagramSocket.close();
			
		}
		
	}

	public static void log( String log){
		System.out.println("[UDP-Echo-Server Log : ]"+log);
	}
}
