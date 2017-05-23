package com.haoyu.module.jcstruct.opt;

import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haoyu.module.jcstruct.common.BaseDataType;
import com.haoyu.module.jcstruct.common.SystemConsts;
import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.Field;
import com.haoyu.module.jcstruct.model.SimpleLengthRule;
import com.haoyu.module.jcstruct.read.JDataInput;
import com.haoyu.module.jcstruct.writer.JDataOutput;

public class DefaultOptManager extends AbstractOptManager
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public DefaultOptManager()
	{
		super();
	}

	public DefaultOptManager(Map<BaseDataType, JFieldOpt> inits)
	{
		super(inits);
	}

	public void setDefaultRead(BaseDataType type)
	{
		switch (type)
		{
		case BOOLEAN:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput dataInput, Object... others) throws IOException
				{
					return dataInput.readBoolean();
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Object value = getValue(field, obj);

					dataOutput.writeBoolean(Boolean.parseBoolean(value.toString()));
				}

			});
			break;
		case BYTE:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return jDataInput.readByte();
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValueNoRule(field, obj);

					dataOutput.writeByte(value.byteValue());

				}
			});
			break;
		case CHAR:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return jDataInput.readCharC();
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValueNoRule(field, obj);

					dataOutput.writeByte(value.byteValue());
				}
			});
			break;
		case SHORT:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readShort());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeShort(value.shortValue());
				}
			});
			break;
		case USHORT:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readUnsignedShort());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeUshort(value.intValue());
				}
			});
			break;
		case INT:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readInt());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeInt(value.intValue());
				}
			});
			break;
		case UINT:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readUnsignedInt());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeUint(value.longValue());
				}
			});

			break;

		case LONG:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readLong());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeLong(value.longValue());

				}
			});

			break;

		case FLOAT:

			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readFloat());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeFloat(value.floatValue());

				}
			});

			break;
		case DOUBLE:

			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return field.getRealValue(jDataInput.readDouble());
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Number value = getNumberValue(field, obj);

					dataOutput.writeDouble(value.doubleValue());

				}
			});

			break;
		case STRING:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{
					return jDataInput.readUTF();
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Object value = getValue(field, obj);

					dataOutput.writeUTF(value.toString());
				}
			});

			break;
		case USHORTARRAY:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{

					int waveLength = SystemConsts.transfer_wave_long;

					if (null != field.getSimpleLengthRule()) {
						SimpleLengthRule rule = field.getSimpleLengthRule();

						JSONObject result = (JSONObject) others[0];

						int packageLength = result.getIntValue(rule.getFileName());

						waveLength = rule.getRule().getIntValue(packageLength, rule.getValue().intValue()).intValue();
					}

					int[] waveData = new int[waveLength];

					jDataInput.readUnShortedArray(waveData);

					return waveData;
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Object value = getValue(field, obj);
					dataOutput.writeUshortArray((int[]) value, -1);

				}
			});

			break;

		case SHORTARRAY:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{

					int waveLength = SystemConsts.transfer_wave_long;

					if (null != field.getSimpleLengthRule()) {
						SimpleLengthRule rule = field.getSimpleLengthRule();

						JSONObject result = (JSONObject) others[0];

						int packageLength = result.getIntValue(rule.getFileName());

						waveLength = rule.getRule().getIntValue(packageLength, rule.getValue().intValue()).intValue();
					}

					short[] waveData = new short[waveLength];

					jDataInput.readShortArray(waveData);
					return waveData;
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Object value = getValue(field, obj);
					dataOutput.writeShortArray((short[]) value, -1);
				}
			});

			break;

		case BYTEARRAY:
			this.put(type, new AbstractJFieldOpt()
			{

				@Override
				public Object read(Field field, JDataInput jDataInput, Object... others) throws IOException
				{

					int waveLength = SystemConsts.transfer_wave_long;

					if (null != field.getSimpleLengthRule()) {
						SimpleLengthRule rule = field.getSimpleLengthRule();

						JSONObject result = (JSONObject) others[0];

						int packageLength = result.getIntValue(rule.getFileName());

						waveLength = rule.getRule().getIntValue(packageLength, rule.getValue().intValue()).intValue();
					}

					byte[] waveData = new byte[waveLength];

					jDataInput.readByteArray(waveData);

					return waveData;
				}

				@Override
				public void writer(JSONObject obj, Field field, JDataOutput dataOutput, Object... others) throws IOException
				{
					Object value = getValue(field, obj);
					dataOutput.writeByteArray((byte[]) value, -1);
				}
			});

			break;
		default:
			throw new DtuMessageException(DtuMessageException.UNKNOWN_EXCEPTION, "not undefined jtype:" + type.JTYPE);

		}
	}

}
