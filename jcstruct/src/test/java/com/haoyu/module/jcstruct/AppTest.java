package com.haoyu.module.jcstruct;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.common.BaseDataType;
import com.haoyu.module.jcstruct.dispatch.DispatchCenterService;
import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.ResolveResult;
import com.haoyu.module.jcstruct.opt.JFieldOpt;
import com.haoyu.module.jcstruct.opt.OptManager;
import com.haoyu.module.jcstruct.opt.DefaultOptManager;
import com.haoyu.module.jcstruct.resolve.DefaultResolve;
import com.haoyu.module.jcstruct.template.TemplateContainer;
import com.haoyu.module.jcstruct.utils.HexUtils;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-cjava.xml")
public class AppTest
{

	@Autowired
	private TemplateContainer templateContainer;

	@Autowired
	private OptManager optManager;

	@Autowired
	private DispatchCenterService dispatchCenterService;

	@Autowired
	private DefaultResolve defaultResolve;

	private void testCharacterResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	private void testWaveResolve() throws DtuMessageException, IOException
	{
		//申请上传波形
		byte[] data = new byte[]{-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,0,14,102,72,4,2,1,10,-26,0,0,0,100,-16,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());
		
		//中间波形
	     data = new byte[] {-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,4,6,102,73,5,-17,-61,-18,-5,-10,-71,4,-94,16,30,22,14,22,110,19,119,15,67,6,111,-1,85,-4,72,-4,89,5,64,13,-15,17,-89,13,-29,6,66,-2,-55,-10,59,-17,-103,-30,108,-38,-22,-33,29,-19,-62,-3,42,8,94,6,-13,5,-79,4,-78,2,-50,-6,-127,-14,-21,-16,114,-6,46,7,56,16,62,18,93,13,-3,8,-15,1,66,-6,-76,0,46,-3,-57,-5,-17,2,40,13,-116,21,-63,23,-82,18,19,12,49,6,57,-4,30,-6,13,-10,35,-8,-37,3,97,11,118,14,-83,9,54,2,122,-6,-83,-13,64,-20,-8,-27,-7,-28,14,-22,-64,-7,83,5,114,13,75,13,-11,12,-39,10,1,3,121,-7,74,-11,-37,-11,-20,-1,-35,10,-96,16,123,14,-8,8,-117,2,3,-7,-24,-13,-54,-25,-37,-34,73,-32,72,-19,69,-3,88,8,-16,8,-75,5,110,4,56,2,-83,-5,1,-15,89,-19,56,-11,117,3,-96,13,-25,18,114,14,-39,10,-91,3,-121,-4,-78,-6,-22,-8,-118,-10,101,-3,39,9,-96,19,-75,24,35,21,-46,17,15,11,-84,2,47,0,-125,-3,91,-3,-90,6,58,14,76,17,68,12,-102,4,-78,-4,-53,-12,-87,-19,-30,-29,-50,-33,50,-28,91,-14,-34,0,88,9,-51,8,63,7,49,5,103,2,20,-8,-4,-13,40,-14,21,-5,-57,7,-9,15,-5,17,6,12,15,6,-125,-2,-88,-8,91,-3,-97,-7,12,-9,-79,-2,-40,10,-84,18,-29,19,-19,13,-79,7,-12,2,-94,-8,-28,-11,70,-15,11,-11,31,0,-20,9,-49,13,-32,8,-61,2,-15,-5,98,-12,78,-18,80,-22,-105,-23,-123,-16,-128,-2,-113,10,21,17,11,17,-112,15,-65,12,78,4,-104,-7,-12,-10,53,-10,110,0,43,10,109,15,-39,13,-26,7,55,0,-108,-8,125,-14,70,-27,-47,-36,-95,-33,52,-20,-29,-3,3,8,-106,8,19,4,-2,3,-82,2,35,-6,103,-15,22,-19,34,-11,-71,3,-63,13,-12,18,102,14,-54,10,-107,3,-94,-3,0,-1,-93,-3,-121,-5,1,0,-59,12,78,21,-91,25,24,21,-77,16,55,10,121,0,-97,-2,-69,-5,-127,-4,49,4,-21,12,-2,15,-10,11,75,3,114,-5,-18,-13,-13,-19,80,-29,31,-34,-58,-28,32,-14,-37,0,123,9,-22,8,51,6,-56,4,-65,1,104,-8,-113,-14,-44,-15,-57,-5,-86,8,2,16,47,17,38,11,-15,6,44,-2,125,-8,31,-4,55,-9,10,-11,-62,-3,100,9,-85,18,85,19,-100,13,94,7,-8,2,-63,-7,84,-14,-65,-19,127,-15,-62,-2,102,8,98,13,66,8,-13,3,-122,-4,87,-11,-92,-17,-46,-20,105,-21,69,-14,67,0,21,11,-112,18,97,18,-89,16,-90,13,9,5,-124,-6,-50,-10,-23,-10,-27,0,121,10,-35,16,116,14,-70,8,8,1,102,-7,50,-14,-41,-25,116,-34,59,-32,50,-19,59,-3,62,8,-81,8,-82,5,1,3,53,1,-111,-7,-16,-16,-12,-20,106,-12,106,2,-115,12,-27,17,-81,14,41,9,-9,3,28,-4,-77,-5,118,-8,-9,-10,-38,-3,54,9,-84,19,-22,24,-128,22,-39,18,109,13,37,3,-93,-3,2,-6,12,-6,-65,3,-64,12,97,15,-40,11,-118,4,12,-4,-8,-11,55,-18,-42,-30,13,-37,-115,-32,86,-17,98,-2,59,8,-46,7,10,5,90,4,57,2,8,-7,-20,-14,49,-16,7,-7,-13,7,20,15,-7,18,33,13,-107,8,79,0,-26,-6,-110,0,69,-4,50,-6,124,1,39,12,-46,21,111,23,81,17,106,11,-116,5,-93,-5,-104,-6,25,-10,-109,-7,56,3,-66,12,41,15,108,9,-6,3,1,-5,53,-13,-76,-19,89,-25,-81,-26,20,-20,120,-6,-71,6,-104,14,55,13,-10,12,-121,9,109,3,30,-8,-14,-12,111,-12,83,-2,74,9,97,15,-7,15,24,8,-43,2,118,-6,69,-12,19,-23,24,-33,-99,-31,53,-19,-20,-3,-26,9,103,10,3,6,29,3,-5,1,-40,-6,57,-15,1,-20,8,-13,-122,1,-64,12,-121,17,-67,13,-24,9,-110,2,-74,-4,27,-6,-27,-8,-128,-10,43,-4,86,8,-104,19,45,24,65,23,45,19,26,14,12,4,-102,-3,98,-6,4,-6,108,3,102,12,75,15,-17,12,46,4,-85,-3,-120,-11,-81,-17,99,-31,-16,-38,-85,-33,60,-18,43,-3,117,8,-121,6,-28,5,49,4,58,2,95,-6,124,-14,36,-17,-115,-7,55,6,-112,15,-57,18,76,14,54,9,83,1,-53,-5,89,1,71,-3,120,-5,109,1,-57,13,97,21,-17,23,-46,18,56,12,95,6,126,-4,29,-5,5,-8,9,-6,-111,4,-59,12,-112,15,125,9,-50,2,-56,-6,-21,-13,58,-20,-33,-27,-117,-29,103,-23,-42,-8,82,4,-77,12,-50,12,-52,11,-88,9,4,2,-23,-8,-41,-12,1,-12,4,-2,49,9,124,16,8,15,21,8,-26,2,-87,-6,76,-37,-86,85};
		
		 json = defaultResolve.resolve(data, null);

			System.out.println(json.getId() + "->" + json.getResult());
		
		//最后一组上传波形
		data = new byte[]{-86,-86,-3,79,-6,-119,4,18,-85,-51,-17,1,0,6,102,83,6,106,-86,85};
		
		json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	private void testACKResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	//心跳
	private void testHeartBeatResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	private void testSensorResolve() throws DtuMessageException, IOException
	{
		byte[] data = new byte[] {-86,-86,-3,79,-6,-119,2,18,-85,-51,-17,1,0,31,101,-61,3,83,0,10,-26,2,0,10,1,87,6,69,3,-80,0,-18,3,98,1,-59,1,-114,7,66,4,104,31,-86,85};
		
		ResolveResult<JSONObject> json = defaultResolve.resolve(data, null);

		System.out.println(json.getId() + "->" + json.getResult());

	}
	
	private void testUnResolve() throws DtuMessageException, IOException{
		//反解析
		JSONObject ack = new JSONObject();
		ack.put("Package_Type", 1);
		ack.put("Gateway_Id", 4249877129L);
		ack.put("Package_Number", 0);
		ack.put("command_properties", 2);
		ack.put("BCC", 1);
		
		byte[] data = defaultResolve.unresolve("1",ack);
		
		//[85, 85, -3, 79, -6, -119, 1, 0, 6, 0, 0, 2, 1, 85, -86]
		//打印16进制
		System.out.println(HexUtils.bytesToHexString(data));
		
	}
	
	private void testUnResolveSensorConfig() throws DtuMessageException, IOException{
		
		String s = "{\"Gateway_Id\":\"4249877129\",\"Upload_Sensor_Type\":\"9\",\"Sensor_Id\":\"313249263\","
				+ "\"HP_Filter\":\"2\",\"X_Angle\":\"0\",\"Sample_Character_Time\":\"1\",\"Upload_Wave_Type\":\"113\","
				+ "\"Upload_Character_Time\":\"1\",\"Upload_Wave_Time\":\"2\",\"Wave_long\":\"1\",\"Sample_Frequency\":\"800\","
				+ "\"Temperature_alarm_diff\":\"35\",\"Temperature_alarm_HH\":\"80\",\"Temperature_alarm_Change\":\"5\","
				+ "\"Acc_X_alarm_HH\":\"1\",\"Acc_X_alarm_H\":\"0.8\",\"Acc_Y_alarm_HH\":\"1\",\"Acc_Y_alarm_H\":\"0.8\","
				+ "\"Acc_Z_alarm_HH\":\"1\",\"Acc_Z_alarm_H\":\"0.8\",\"Speed_X_alarm_HH\":\"1\",\"Speed_X_alarm_H\":\"0.8\","
				+ "\"Speed_Y_alarm_HH\":\"1\",\"Speed_Y_alarm_H\":\"0.8\",\"Speed_Z_alarm_HH\":\"1\",\"Speed_Z_alarm_H\":\"0.8\"}";
		
		JSONObject sensorConfig = JSONObject.parseObject(s);
		
		sensorConfig.put("Package_Type", 6);
		
		sensorConfig.put("Package_Number", 0);
		
		sensorConfig.put("BCC", 1);
		
		byte[] data = defaultResolve.unresolve("6",sensorConfig);
		
		System.out.println(HexUtils.bytesToHexString(data));
		
	}
	

	//@Test
	public void test()
	{
		try {
			testCharacterResolve();
			
			testWaveResolve();
			
			testUnResolve();
			
			testUnResolveSensorConfig();
			
			
			JSONObject content = new JSONObject();
			content.put("id", "5");

			dispatchCenterService.handle("5",content);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testother()
	{
		JFieldOpt jReadArray = optManager.getJFieldOpt(BaseDataType.BYTEARRAY);
		try {
			// jRead.read(null, null);
			jReadArray.read(null, null, 512);

		} catch (IOException e) {
			e.printStackTrace();
		}

		JFieldOpt jReadShortArray = optManager.getJFieldOpt(BaseDataType.SHORTARRAY);
		try {
			// jRead.read(null, null);
			jReadShortArray.read(null, null, 520);

		} catch (IOException e) {
			e.printStackTrace();
		}

		Template tm = templateContainer.getFCTemplate("1");

		System.out.println(tm.toString());

		JSONObject content = new JSONObject();
		content.put("id", "5");

		dispatchCenterService.handle("5",content);
	}

}
