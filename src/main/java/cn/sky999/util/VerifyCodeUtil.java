package cn.sky999.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 标题： 图片验证码
 * 
 * @version 1.0
 */
public class VerifyCodeUtil {
	public static boolean checkedVerifyCode(String machineId, String vcode) throws Exception{
		String str = (String) RedisUtil.redisGet(machineId);
		if(StringUtils.isNotBlank(str)&&str.equals(vcode.toUpperCase())){
			RedisUtil.redisDel(machineId);
			return true;
		}
		return false;
	}
	
	public static boolean checkedVerifyCodeNoDel(String machineId, String vcode) throws Exception {
		String str = (String) RedisUtil.redisGet(machineId);
		if(StringUtils.isNotBlank(str)&&str.equals(vcode.toUpperCase())){
			return true;
		}
		return false;
	}

	public static BufferedImage getImage(HttpServletRequest request) throws Exception {
		String isNum = request.getParameter("isNum");
		String machineId = request.getParameter("machineId");
		if(StringUtils.isBlank(machineId)){
			machineId=request.getSession().getId();
		}
		String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
		if (StringUtils.isNotBlank(isNum) && isNum.equals("1")) {
			codes = "0123456789";
		}
		String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
		Random r = new Random();
		BufferedImage image = new BufferedImage(110, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, 110, 50);
		StringBuilder sb = new StringBuilder();
		// 向图片中画4个字符
		for (int i = 0; i < 4; i++) {
			String s = codes.charAt(r.nextInt(codes.length())) + "";
			sb.append(s);
			float x = i * 1.0F * 110 / 4;
			g2.setFont(new Font(fontNames[r.nextInt(fontNames.length)], r.nextInt(4), r.nextInt(6) + 40));
			g2.setColor(new Color(new Random().nextInt(150), r.nextInt(150), r.nextInt(150)));
			g2.drawString(s, x, 50 - 10);// 调整文字显示高度
		}
		RedisUtil.redisPut(machineId, sb.toString().toUpperCase(),600);
		for (int i = 0; i < 3; i++) {
			int x1 = r.nextInt(110);
			int y1 = r.nextInt(50);
			int x2 = r.nextInt(110);
			int y2 = r.nextInt(50);
			g2.setStroke(new BasicStroke(1.5F));
			g2.setColor(Color.BLUE);
			g2.drawLine(x1, y1, x2, y2);
		}
		return image;
	}
}
