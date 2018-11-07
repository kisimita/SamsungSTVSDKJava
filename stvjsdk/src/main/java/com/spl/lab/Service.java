package com.spl.lab;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Service {
	public static void WakeOnWirelessLan(String macAddr) {
		int SRC_PORT = 2014;
		int DST_PORT = 2014;
		String magicPacketId = "FF:FF:FF:FF:FF:FF";
		String broadCastAddr = "255.255.255.255";
		String wakeUpIdentifier = "SECWOW";
		String secureOn = "00:00:00:00:00:00";

		int wow_packet_max_size = 136;
		int wow_packet_min_size = 102;
		int wow_packet_sec_size = 6;
		int wow_packet_ss_size = 12;
		int packetSizeAlloc = 102;
		int reservedField = 0;
		int applicationID = 0;

		packetSizeAlloc += 6;
		packetSizeAlloc += 12;

		ByteBuffer wowPacketBuffer = ByteBuffer.allocate(packetSizeAlloc);
		wowPacketBuffer.put(convertMacAddrToBytes("FF:FF:FF:FF:FF:FF"));

		for (int i = 0; i < 16; i++) {
			wowPacketBuffer.put(convertMacAddrToBytes(macAddr));
		}

		wowPacketBuffer.put(convertMacAddrToBytes("00:00:00:00:00:00"));
		wowPacketBuffer.put("SECWOW".getBytes());
		wowPacketBuffer.putInt(reservedField);
		wowPacketBuffer.put((byte) applicationID);

		byte[] magicPacket = wowPacketBuffer.array();

		DatagramSocket wowSocket = null;

		try {
			wowSocket = new DatagramSocket(null);
			wowSocket.setReuseAddress(true);
			wowSocket.bind(new InetSocketAddress(2014));

			DatagramPacket wowPacket = new DatagramPacket(magicPacket, magicPacket.length);

			wowPacket.setAddress(InetAddress.getByName("255.255.255.255"));
			wowPacket.setPort(2014);

			wowSocket.send(wowPacket);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wowSocket != null) {
				wowSocket.close();
			}
		}
	}

	private static byte[] convertMacAddrToBytes(String macAddr) {
		String[] macAddrAtoms = macAddr.split(":");

		byte[] macAddressBytes = new byte[6];
		for (int i = 0; i < 6; i++) {
			Integer hex = Integer.valueOf(Integer.parseInt(macAddrAtoms[i], 16));
			macAddressBytes[i] = hex.byteValue();
		}

		return macAddressBytes;
	}
}

