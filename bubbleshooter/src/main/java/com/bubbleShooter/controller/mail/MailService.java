package com.bubbleShooter.controller.mail;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.nft.NFTService;
import com.bubbleShooter.domain.Mail;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.relation.MailObject;
import com.bubbleShooter.request.ReqMailConfirm;
import com.bubbleShooter.request.ReqMailOpen;
import com.bubbleShooter.request.ReqMailRemove;
import com.bubbleShooter.response.ResMailConfirm;
import com.bubbleShooter.response.ResMailList;
import com.bubbleShooter.response.ResMailOpen;
import com.bubbleShooter.response.ResMailRemove;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class MailService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private NFTService nftService;
	
	public ResMailList MailInfo(int userId) throws Exception
	{
		ResMailList res = new ResMailList();
		
		User user = repositoryService.getUser(userId, false);
		 if(user == null)
			 throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5035);
		
		List<Mail> mails = repositoryService.getMail(userId);
		if(mails == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5003);
		
		if(!user.getWallet().isBlank())
		{
			nftService.ItemImport(userId);
			nftService.BalanceDepositList(userId);
		}
		
		res.mailList = mapperVO.makeMailsVO(userId, mails);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResMailConfirm MailConfirm(int userId, ReqMailConfirm req) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5035);
		
		ResMailConfirm res = new ResMailConfirm();
		
		List<Mail> mails = repositoryService.getMail(userId);
		if(mails == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5004);
		
		int index = findData.findMailIndex(req.mailId, mails);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5005);
		
		mails.get(index).setState(ConstantVal.MAIL_STATE_CONFIRM);
		mails.get(index).setOpenTime(TimeCalculation.getCurrentUnixTime());

		//db set
		repositoryService.updateMail(userId, mails.get(index));
		
		if(!user.getWallet().isBlank())
			nftService.ItemImport(userId);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.mailList = mapperVO.makeMailsVO(userId, mails);
		return res;
	}
	
	public ResMailOpen MailOpen(int userId, ReqMailOpen req) throws Exception
	{
		ResMailOpen res = new ResMailOpen();
		
		Mail mail = getOneMail(userId, req.mailId);
		if(mail == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5001);
		
		//이미 받은 아이템
		if(mail.getState() == ConstantVal.MAIL_STATE_REWARD)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5021);

		//우편 만료기한이 지났음
		if(mail.getExpiredTime() < TimeCalculation.getCurrentUnixTime())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5011);

		// 메일 상태값 바꿔주기
		mail.setState(ConstantVal.MAIL_STATE_REWARD);
		
		// 받은시간 바꿔주기
		mail.setReceiveTime(TimeCalculation.getCurrentUnixTime());

		// mailObject 만들기 객채 하나 일때
		MailObject mailObject = makeMailObject(mail);
		
		repositoryService.updateMail(userId, mail);
		
		res.rewards = mapperVO.makeRewardResult(userId, null, mailObject.items, ConstantVal.LOG_TYPE_GET_MAIL_ITEM);
		res.items = mapperVO.makeItemVO(userId, null, false);
		res.characters = mapperVO.makeCharacterVO(userId, null, false);
		res.partners = mapperVO.makePartnerVO(userId, null, false);
		res.mails = mapperVO.makeMailsVO(userId, null);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResMailRemove MailRemove(int userId, ReqMailRemove req) throws Exception
	{
		ResMailRemove res = new ResMailRemove();
		
		List<Mail> mails = repositoryService.getMail(userId);
		if(mails == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5007);
		
		int index = findData.findMailIndex(req.mailId, mails);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5008);
		
		if(mails.get(index).getState() != ConstantVal.MAIL_STATE_REWARD)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5034);
		
		mails.get(index).setIs_expired(ConstantVal.MAIL_EXPIRED_REMOVE);
		
		//db set
		repositoryService.updateMail(userId, mails.get(index));
		
		res.mailList = mapperVO.makeMailsVO(userId, mails);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}

	public Mail getOneMail(int userId, int lastMailId) throws Exception
	{
		if(lastMailId < 0)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5025);
		
		Mail mail = repositoryService.getMailOne(lastMailId);
		if(mail == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5010);
		
		return mail;
	}

	// 메일보내기 (등록)
	public void SendMails(int userId, byte type, String title, String description, long expiredTime, List<RewardItemVO> itemInfos) throws Exception
	{
		if (itemInfos == null || itemInfos.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5032);
		
		// 아이템 개수
		if(itemInfos.size() > 0)
		{
			if(itemInfos.size() <= ConstantVal.MAIL_ITEM_CNT)
			{
				setMail(userId, type, title,  description, expiredTime, itemInfos);
			}
			else
			{
				// 5개씩 보내기
				List<RewardItemVO> mailItems = new ArrayList<>();
				int index = 0;
				
				for(int i = 0; i < itemInfos.size(); i++)
				{
					if(index == ConstantVal.MAIL_ITEM_CNT)
					{
						setMail(userId, type, title,  description, expiredTime, mailItems);
						mailItems.clear();
						index = 0;
					}
					
					mailItems.add(itemInfos.get(i));
					index++;
				}
				
				if(mailItems.size() > 0)
					setMail(userId, type, title,  description, expiredTime, mailItems);
			}
		}
	}
	
	//메일함 등록
	private void setMail(int userId, byte type, String title, String description, long expiredTime, List<RewardItemVO> itemInfos) throws Exception
	{
		//type - 0: 일반우편, 1:캐시우편
		
		if (itemInfos == null || itemInfos.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAIL_5033);
		
		Mail mailbox = new Mail();
		
		mailbox.setMailType(type);
		mailbox.setUserId(userId);
		mailbox.setMailIdVerify(0);  //???????? 애는 뭐냐?
		mailbox.setTitle(title);
		mailbox.setDescription(description);
		mailbox.setExpiredTime(expiredTime);
		mailbox.setOpenTime(TimeCalculation.getCurrentUnixTime());
		mailbox.setReceiveTime(TimeCalculation.getCurrentUnixTime());
		mailbox.setState(ConstantVal.MAIL_STATE_NEW);
		mailbox.setIs_new(ConstantVal.MAIL_STATE_NEW);
		
		mailbox.setMailItem0("");
		mailbox.setMailItem1("");
		mailbox.setMailItem2("");
		mailbox.setMailItem3("");
		mailbox.setMailItem4("");
		
		// type에 따른 expiredTime
		if(mailbox.getMailType() == ConstantVal.MAIL_TYPE_CASH)
		{
			// 캐시우편이라면
			mailbox.setExpiredTime(TimeCalculation.getDateToUnixTime(ConstantVal.MAIL_CASH_EXPIRE_TIME));
		}
		
		// 메일박스에는 최대 5개까지 보상이 가능하다.
		if(itemInfos != null)
		{
			for (byte itemNum = 0; itemNum < itemInfos.size(); itemNum++)
			{
				switch (itemNum)
				{
				case 0:
					mailbox.setMailItem0(makeRewardItemToString(itemInfos.get(itemNum)));
					 break;
				case 1:
					mailbox.setMailItem1(makeRewardItemToString(itemInfos.get(itemNum)));
					break;
					
				case 2:
					mailbox.setMailItem2(makeRewardItemToString(itemInfos.get(itemNum)));
					break;
					
				case 3:
					mailbox.setMailItem3(makeRewardItemToString(itemInfos.get(itemNum)));
					break;
					
				case 4:
					mailbox.setMailItem4(makeRewardItemToString(itemInfos.get(itemNum)));
					break;
					
				default:
					break;
				}
			}
		}
		
		//DB set
		repositoryService.setMail(userId, mailbox);
		
	}
	
	
	public String makeRewardItemToString(RewardItemVO vo)
	{
		String strMailItem = "";
		if(vo.itemType >= 0) strMailItem += String.valueOf(vo.itemType) + "|";
		if(vo.itemId >= 0) strMailItem += String.valueOf(vo.itemId) + "|";
		if(vo.itemCount > 0) strMailItem += String.valueOf(vo.itemCount) + "|";
		if(vo.grade != null && vo.grade > ConstantVal.DEFAULT_VALUE) strMailItem += String.valueOf(vo.grade) + "|";
		if(vo.level != null) strMailItem += String.valueOf(vo.level) + "|";
		if(vo.upGrade != null) strMailItem += String.valueOf(vo.upGrade) + "|";
		if(vo.skill1 != null) strMailItem += String.valueOf(vo.skill1) + "|";
		if(vo.skill2 != null) strMailItem += String.valueOf(vo.skill2) + "|";
		if(vo.skill3 != null) strMailItem += String.valueOf(vo.skill3) + "|";
		
		strMailItem = strMailItem.substring(0, strMailItem.length() - 1);
		
		return strMailItem;
	}
	
	
	private MailObject makeMailObject(Mail mail) throws Exception
	{
		MailObject mailObject = new MailObject();
		mailObject.items = new ArrayList<>();
		
		mailObject.mailId = mail.getMailId();
		mailObject.mailType = mail.getMailType();
		mailObject.userId = mail.getUserId();
		mailObject.mailIdVerify = mail.getMailIdVerify();
		mailObject.title = mail.getTitle();
		mailObject.description = mail.getDescription();
		mailObject.state = mail.getState();
		mailObject.expiredTime = mail.getExpiredTime();
		mailObject.openTime = mail.getOpenTime();
		mailObject.receiveTime = mail.getReceiveTime();
		mailObject.is_expired = mail.getIs_expired();
		mailObject.is_new = mail.getIs_new();
		
		if(!mail.getMailItem0().isEmpty())
			mailObject.items.add(makeMailItem(mail.getMailItem0()));
		
		if(!mail.getMailItem1().isEmpty())
			mailObject.items.add(makeMailItem(mail.getMailItem1()));
		
		if(!mail.getMailItem2().isEmpty())
			mailObject.items.add(makeMailItem(mail.getMailItem2()));
		
		if(!mail.getMailItem3().isEmpty())
			mailObject.items.add(makeMailItem(mail.getMailItem3()));
		
		if(!mail.getMailItem4().isEmpty())
			mailObject.items.add(makeMailItem(mail.getMailItem4()));
		
		return mailObject;
	}
	
	public RewardItemVO makeMailItem(String strMailItem) throws Exception
	{
		RewardItemVO item = new RewardItemVO();
		String[] str = strMailItem.split("\\|");
		if(str.length > ConstantVal.DEFAULT_ZERO)
		{
			item.itemType = Integer.parseInt(str[0]);
			item.itemId = Integer.parseInt(str[1]);
			item.itemCount = Integer.parseInt(str[2]);
			item.grade = str.length > 3 ? Integer.parseInt(str[3]) : null;
			item.level = str.length > 4 ? Integer.parseInt(str[4]) : null;
			item.upGrade = str.length > 5 ? Integer.parseInt(str[5]) : null;
			item.skill1 = str.length > 6 ? Integer.parseInt(str[6]) : null;
			item.skill2 = str.length > 7 ? Integer.parseInt(str[7]) : null;
			item.skill3 = str.length > 8 ? Integer.parseInt(str[8]) : null;
		}
		
		return item;
	}
	
	public List<RewardItemVO> makeMailItems(Mail mail) throws Exception
	{
		MailObject obj = makeMailObject(mail);
		return obj.items;
	}
}
