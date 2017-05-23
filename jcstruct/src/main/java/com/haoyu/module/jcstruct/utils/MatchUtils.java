package com.haoyu.module.jcstruct.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

import com.haoyu.module.jcstruct.model.SimpleLengthRule;
import com.haoyu.module.jcstruct.model.SimpleValueRule;
import com.haoyu.module.jcstruct.rule.SimpleDivideRule;
import com.haoyu.module.jcstruct.rule.SimpleDivideRuleForReceive;
import com.haoyu.module.jcstruct.rule.SimpleComputerRule;
import com.haoyu.module.jcstruct.rule.SimpleMultiplyRule;
import com.haoyu.module.jcstruct.rule.SimpleMultiplyRuleForReceive;
import com.haoyu.module.jcstruct.rule.SimplePlusRule;
import com.haoyu.module.jcstruct.rule.SimpleSubtractRule;

public final class MatchUtils
{

	public final static String computerRule = "(?<=\\{)(.+?)(?=\\})";

	public final static Map<String, SimpleComputerRule> optsComputer = new HashMap<>();

	public final static Map<String, SimpleComputerRule> optsComputerForReceive = new HashMap<>();

	static {

		optsComputer.put("+", new SimplePlusRule());
		optsComputer.put("-", new SimpleSubtractRule());
		optsComputer.put("*", new SimpleMultiplyRule());
		optsComputer.put("/", new SimpleDivideRule());

		optsComputerForReceive.put("*", new SimpleMultiplyRuleForReceive());
		optsComputerForReceive.put("/", new SimpleDivideRuleForReceive());

	}

	public static SimpleLengthRule matchLength(String target)
	{
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(computerRule);
		Matcher matcher = pattern.matcher(target);
		while (matcher.find()) {
			ls.add(matcher.group());
		}
		checkLengRule(ls, target);
		return changeSimpleRuleLength(ls);
	}

	public static SimpleValueRule matchValue(String target, boolean isForReceive)
	{
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(computerRule);
		Matcher matcher = pattern.matcher(target);
		while (matcher.find()) {
			ls.add(matcher.group());
		}
		checkValueRule(ls, target, isForReceive);
		return changeSimpleRuleValue(ls, isForReceive);

	}

	private static void checkLengRule(List<String> ls, String target)
	{

		Assert.isTrue(ls.size() == 3, "解析长度规则长度不正确:" + target);

		Assert.isTrue(optsComputer.containsKey(ls.get(1)), "解析长度规则操作符失败:" + target);

		Assert.isTrue(NumberValidationUtils.isWholeNumber(ls.get(2)), "解析长度规则目标值失败:" + target);
	}

	private static void checkValueRule(List<String> ls, String target, boolean isForReceive)
	{

		Assert.isTrue(ls.size() == 2, "解析属性重计算规则长度不正确:" + target);

		Assert.isTrue(optsComputer.containsKey(ls.get(0)), "解析属性重计算规则操作符失败:" + target);

		Assert.isTrue(NumberValidationUtils.isRealNumber(ls.get(1)), "解析属性重计算规则目标值失败:" + target);

		if (isForReceive && isMultiplyAndDivide(ls.get(0))) {

			Assert.isTrue(optsComputerForReceive.containsKey(ls.get(0)), "解析属性重计算规则操作符失败:" + target);

		}
	}

	private static boolean isMultiplyAndDivide(String string)
	{
		return "*".equals(string) || "/".equals(string);
	}

	private static SimpleLengthRule changeSimpleRuleLength(List<String> ls)
	{

		SimpleLengthRule rule = new SimpleLengthRule();

		rule.setFileName(ls.get(0));

		rule.setRule(optsComputer.get(ls.get(1)));

		rule.setValue(Integer.parseInt(ls.get(2)));

		return rule;
	}

	private static SimpleValueRule changeSimpleRuleValue(List<String> ls, boolean isForReceive)
	{

		SimpleValueRule rule = new SimpleValueRule();

		if (isForReceive && isMultiplyAndDivide(ls.get(0))) {

			rule.setRule(optsComputerForReceive.get(ls.get(0)));

		} else {

			rule.setRule(optsComputer.get(ls.get(0)));
		}

		rule.setValue(Float.parseFloat(ls.get(1)));

		return rule;
	}

}
