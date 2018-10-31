package cn.sky999.common.uuid;

import java.util.UUID;

public class UUIDUtil {

	public static String get32UUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static void main(String[] args) {
		for(int i=0;i<1;i++) {
			System.out.println(get32UUID());
		}
	}
}
