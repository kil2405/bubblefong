package com.bubbleShooter.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Mail;
import com.bubbleShooter.domain.Mission;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.UserItem;

@Component
public class FindData
{
	public int findCharacterIndex(List<Character> characters, String characterUid)
	{
		for(Character c : characters)
		{
			if(c.getUseOrNot() <= ConstantVal.IS_FALSE)
				continue;
			
			if(c.getUid().equals(characterUid))
				return characters.indexOf(c);
		}
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public int findCharacterEmptySlot(List<Character> characters)
	{
		for(Character c : characters)
		{
			if(c.getUseOrNot() > ConstantVal.IS_FALSE)
				continue;
			
			if(c.getUseOrNot() == ConstantVal.IS_FALSE)
				return characters.indexOf(c);
		}
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public int findPartnerIndex(List<Partner> partners, String uid)
	{
		for(Partner p : partners)
		{
			if(p.getUseOrNot() <= ConstantVal.IS_FALSE)
				continue;
			
			if(p.getUid().equals(uid))
				return partners.indexOf(p);
		}
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public int findPartnerEmptySlot(List<Partner> partners)
	{
		for(Partner p : partners)
		{
			if(p.getUseOrNot() > ConstantVal.IS_FALSE)
				continue;
			
			if(p.getUseOrNot() == ConstantVal.IS_FALSE)
				return partners.indexOf(p);
		}
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public int findMissionIndex(List<Mission> missions, int missionId)
	{
		for(Mission mission : missions)
		{
			if(missionId == mission.getMissionId())
				return missions.indexOf(mission);
		}
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public long getShopSellLimitTime(int sellType) throws Exception
	{
		long limitDate = 0;
		
		switch(sellType)
		{
		case ConstantVal.SHOP_SELL_TYPE_UNLIMITED:
			limitDate = 0;
			break;
		case ConstantVal.SHOP_SELL_TYPE_ACCOUNT:
			limitDate = TimeCalculation.getDateToUnixTime(ConstantVal.MAIL_CASH_EXPIRE_TIME);
			break;
		case ConstantVal.SHOP_SELL_TYPE_DAILY:
			limitDate = TimeCalculation.getNextDayUnixTime(24);
			break;
		case ConstantVal.SHOP_SELL_TYPE_WEEKLY:
			limitDate = TimeCalculation.getWeekDayToUnixTime(1, ConstantVal.MONDAY);
			break;
		case ConstantVal.SHOP_SELL_TYPE_MONTHLY:
			limitDate = TimeCalculation.doDateUnixTime(ConstantVal.DATE_SECTION_MONTH, 1);
			break;
		}
		
		return limitDate;
	}
	
	public int getRandInt(int seed)
	{
		if (seed == 0)
			return 0;

		return ThreadLocalRandom.current().nextInt(seed);
	}

	public int getRandInt(int startVal, int endVal)
	{
		int minVal = Math.min(startVal, endVal);
		int maxVal = Math.max(startVal, endVal);
		
		int gapVal = maxVal - minVal;
		if (gapVal < ConstantVal.IS_TRUE)
			return minVal;
		
		return ThreadLocalRandom.current().nextInt(gapVal + 1) + minVal;
	}
	
	public String getRandString(int count)
	{
		int NUMBUR_MAX = 9;
	    int TAG_MAX_RANDOM = 100000;
	    int TAG_TYPE_CAPITAL_LETTER = 3;
		
	    StringBuilder randString = new StringBuilder();
	    for(int iCnt = 0 ; iCnt < count ; )
	    {
	        int value = getRandInt(TAG_MAX_RANDOM) % TAG_TYPE_CAPITAL_LETTER;
	        
	        String str = "";
	        switch(value)
	        {
	            case 0:
	            	str = String.format("%d", getRandInt(0, NUMBUR_MAX));
	                break;

	            case 1:
	            	str = String.format("%c", (getRandInt(65, 90)));
	                break;
	                
	            case 2:
	            	str = String.format("%c", (getRandInt(65, 90)));
	                break;
	        }
	        
	        ++iCnt;
	        randString.append(str);
	    }
        
        return randString.toString();
	}

//	public List<Integer> getRandomKeyList(int maxValue, int count)
//	{
//		List<Integer> list = new ArrayList<Integer>();
//
//		Random random = new Random();
//
//		while (true)
//		{
//			int rValue = random.nextInt(maxValue);
//			if (!list.contains(rValue))
//			{
//				list.add(rValue);
//				count--;
//				if (count < 1)
//				{
//					break;
//				}
//			}
//		}
//
//		return list;
//	}
//	
//	public int getLastRankMatchIndex(List<MatchHistory> historys)
//	{
//		if(historys == null || historys.isEmpty())
//			return ConstantVal.DEFAULT_VALUE;
//		
//		int index = ConstantVal.DEFAULT_VALUE;
//		MatchHistory lastHistory = new MatchHistory();
//		for(MatchHistory history : historys)
//		{
//			if(history.getIsRank() <= ConstantVal.IS_FALSE)
//				continue;
//			
//			if(history.getHistoryTime() > lastHistory.getHistoryTime())
//			{
//				lastHistory = history;
//				index = historys.indexOf(history);
//			}
//		}
//		
//		return index;
//	}
//	
//	public byte getGradeToStartLevel(byte grade)
//	{
//		if(grade <= ConstantVal.DEFAULT_VALUE)
//			return ConstantVal.DEFAULT_VALUE;
//		
//		return ServerConfig.GRADE_START_LEVEL[grade];
//	}
	
	public String getGradeToString(int grade)
	{
		String gradeString = "";
		switch(grade)
		{
		case ConstantVal.CARD_GRADE_N:
			gradeString = "N";
			break;
		case ConstantVal.CARD_GRADE_R:
			gradeString = "R";
			break;
		case ConstantVal.CARD_GRADE_SR:
			gradeString = "SR";
			break;
		case ConstantVal.CARD_GRADE_SSR:
			gradeString = "SSR";
			break;
		}
		
		return gradeString;
	}
	
	public byte getStringToGrade(String strGrade)
	{
		byte grade = 0;
		switch(strGrade)
		{
		case "N":
		case "C":
			grade = ConstantVal.CARD_GRADE_N;
			break;
		case "R":
		case "B":
			grade = ConstantVal.CARD_GRADE_R;
			break;
		case "SR":
		case "A":
			grade = ConstantVal.CARD_GRADE_SR;
			break;
		case "SSR":
		case "S":
			grade = ConstantVal.CARD_GRADE_SSR;
			break;
		}
		
		return grade;
	}
	
	public byte findUserItemIndex(int itemType, int itemId, List<UserItem> items)
	{
		if(items == null || items.isEmpty())
			return ConstantVal.DEFAULT_VALUE;
		
		for(UserItem item : items)
		{
			if(item.getItemType() == itemType && item.getItemId() == itemId)
				return (byte)items.indexOf(item);
		}
		
		return ConstantVal.DEFAULT_VALUE;
	}
	
	public int getStringLength(String str) 
	{
		char ch[] = str.toCharArray();
		int max = ch.length;
		int count = 0;

		for (int i = 0; i < max; i++) 
		{
			// 0x80: 문자일 경우 +2
			if (ch[i] > 0x80) {
				count++;
			}
			count++;
		}
		return count;
	}
	
	public boolean checkInputOnlyNumberAndAlphabet(String textInput)
	{
		if(textInput.length() <= 0)
			return false;
		
		char chrInput;
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
			    // 영문(소문자) OK!
				continue;
			} 
			else if (chrInput >=0x41 && chrInput <= 0x5A) {
			    // 영문(대문자) OK!
				continue;
			}
			else if (chrInput >= 0x30 && chrInput <= 0x39) {
			    // 숫자 OK!
				continue;
			}
			else {
			    return false;   // 영문자도 아니고 숫자도 아님!
			}
		}
		return true;
	}
	
	public int findMailIndex(int mailId, List<Mail> mails)
	{
		for(Mail m : mails)
		{
			if(m.getMailId() == mailId)
				return mails.indexOf(m);
		}
		
		return ConstantVal.DEFAULT_VALUE;
	}
	
//	public int findCouponIndex(long couponId, List<Coupon> coupons)
//	{
//		for(Coupon c : coupons)
//		{
//			if(c.getCouponId() == couponId)
//				return coupons.indexOf(c);
//		}
//		
//		return ConstantVal.DEFAULT_VALUE;
//	}
//	
//	public int findEventIndex(long eventId, List<Event> events)
//	{
//		for(Event e : events)
//		{
//			if(e.getEventId() == eventId)
//				return events.indexOf(e);
//		}
//		
//		return ConstantVal.DEFAULT_VALUE;
//	}
//	
//	public int findNoticeIndex(long noticeId, List<Notice> notices)
//	{
//		for(Notice n : notices)
//		{
//			if(n.getNoticeId() == noticeId)
//				return notices.indexOf(n);
//		}
//		
//		return ConstantVal.DEFAULT_VALUE;
//	}
//	
//	public int findServerConfigIndex(String configKey, List<AdminServerConfig> adminServerConfigs)
//	{
//		for(AdminServerConfig a : adminServerConfigs)
//		{
//			if(a.getConfigKey().equals(configKey))
//				return adminServerConfigs.indexOf(a);
//		}
//		
//		return ConstantVal.DEFAULT_VALUE;
//	}
}
