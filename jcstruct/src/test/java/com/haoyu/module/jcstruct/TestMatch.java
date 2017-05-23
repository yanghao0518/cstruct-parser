package com.haoyu.module.jcstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMatch
{

	public static void main(String[] args)
	{
		List<String> list = getTeacherList("{Package_length}{-}{6}");
		
		System.out.println(list.get(1));
		
		float f1 = 36F;
		
		System.out.println((int)f1);
	}
	
	public  static List<String> getTeacherList(String managers){
        List<String> ls=new ArrayList<String>();
       // Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
        
        Matcher matcher = pattern.matcher(managers);
        while(matcher.find())
            ls.add(matcher.group());
        return ls;
    }
}
