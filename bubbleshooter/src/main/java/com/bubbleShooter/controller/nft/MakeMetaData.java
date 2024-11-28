package com.bubbleShooter.controller.nft;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.ItemResource;
import com.bubbleShooter.resource.PartnerResource;
import com.bubbleShooter.resource.SkillCharacterActiveResource;
import com.bubbleShooter.resource.SkillPartnerResource;
import com.bubbleShooter.util.FindData;

@Component
public class MakeMetaData
{
	@Autowired
	private FindData findData;
	
	@Autowired
	private GameResource gameResource;
	
	@SuppressWarnings("unchecked")
	public String makeCharacterJson(Character character, String version) throws Exception
	{
		CharacterResource characterRS = gameResource.getCharacter().get(character.getCharacterId());
		if(characterRS == null)
			return null;
		
		//Creating a JSONObject object
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("name", characterRS.getDesc());

	    JSONArray array = new JSONArray();
	    array.add(getJsonObj("type", String.valueOf(characterRS.getItemType())));
	    array.add(getJsonObj("character_id", String.valueOf(character.getCharacterId())));
	    array.add(getJsonObj("character_name", characterRS.getDesc()));
	    array.add(getJsonObj("grade", findData.getGradeToString(character.getGrade())));
	    array.add(getJsonObj("level", String.valueOf(character.getCharacterLevel())));
	    array.add(getJsonObj("enhance", String.valueOf(character.getCharacterUpgrade())));
	    array.add(getJsonObj("add_bp", String.valueOf(character.getDailyearnlimit())));
	    array.add(getJsonObj("version", version));
	    
	    array.add(getCharacterDisplayJsonObj("active_skill", character.getActiveSkill(), character.getCharacterLevel()));
	    
	    jsonObject.put("attributes", array);
	    
	    return jsonObject.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public String makeFriendsJson(Partner partner, String version) throws Exception
	{
		PartnerResource partnerRS = gameResource.getPartner().get(partner.getPartnerId());
		if(partnerRS == null)
			return null;
		
		//Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", partnerRS.getDesc());
		
		JSONArray array = new JSONArray();
		array.add(getJsonObj("type", String.valueOf(partnerRS.getItemType())));
		array.add(getJsonObj("friends_id", String.valueOf(partner.getPartnerId())));
		array.add(getJsonObj("friends_name", partnerRS.getDesc()));
		array.add(getJsonObj("grade", findData.getGradeToString(partner.getGrade())));
		array.add(getJsonObj("level", String.valueOf(partner.getPartnerLevel())));
		array.add(getJsonObj("enhance", String.valueOf(partner.getUpgrade())));
		array.add(getPartnerDisplayJsonObj("skill_1", partner.getPartnerLevel(), partner.getSkill1()));
		array.add(getPartnerDisplayJsonObj("skill_2", partner.getPartnerLevel(), partner.getSkill2()));
		array.add(getPartnerDisplayJsonObj("skill_3", partner.getPartnerLevel(), partner.getSkill3()));
		array.add(getJsonObj("version", version));
	      
	    jsonObject.put("attributes", array);
	      
	    return jsonObject.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public String makeItemJson(int itemType, int itemId, int itemCount, String version) throws Exception
	{
		ItemResource itemRS = gameResource.getItem().get(itemId);
		if(itemRS == null)
			return null;

		//Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", itemRS.getDesc());
      
		JSONArray array = new JSONArray();
		array.add(getJsonObj("type", String.valueOf(itemType)));
		array.add(getJsonObj("item_id", String.valueOf(itemId)));
		array.add(getJsonObj("item_name", itemRS.getDesc()));
		array.add(getJsonObj("item_info", itemRS.getDesc()));
		array.add(getJsonObj("item_count", String.valueOf(itemCount)));
		array.add(getJsonObj("version", version));
      
		jsonObject.put("attributes", array);
      
		return jsonObject.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObj(String key, String value)
	{
		JSONObject obj = new JSONObject();
		obj.put("trait_type", key);
		obj.put("value", value);
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getCharacterDisplayJsonObj(String type, int skill_id, int level) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("display_type", "skills");
		obj.put("trait_type", type);
		
		JSONArray array = new JSONArray();
		SkillCharacterActiveResource characterSkill = gameResource.getCharacterActiveSkill().get(skill_id, level);
		if(characterSkill == null)
		{
			obj.put("value", "null");
			return obj;
		}
		
		String skillInfo = characterSkill.getDesc1();
		
		if(characterSkill.getValue1() > 0)
		{
			skillInfo = skillInfo.replace("{value1}", String.valueOf(characterSkill.getValue1()));
		}
		if(characterSkill.getValue2() > 0)
		{
			skillInfo = skillInfo.replace("{value2}", String.valueOf(characterSkill.getValue2()));
		}
		if(characterSkill.getValue3() > 0)
		{
			skillInfo = skillInfo.replace("{value3}", String.valueOf(characterSkill.getValue3()));
		}
		if(characterSkill.getValue4() > 0)
		{
			skillInfo = skillInfo.replace("{value4}", String.valueOf(characterSkill.getValue4()));
		}
		
		array.add(getJsonObj("skill_id", String.valueOf(characterSkill.getId())));
		array.add(getJsonObj("skill_name", characterSkill.getSkillName()));
		array.add(getJsonObj("skill_info", skillInfo));
		
		obj.put("value", array);
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getPartnerDisplayJsonObj(String type, int parnterLevel, int id) throws Exception
	{
		JSONObject obj = new JSONObject();
		obj.put("display_type", "skills");
		obj.put("trait_type", type);
		
		SkillPartnerResource skill = gameResource.getPartnerSkill().get(id);
		if(skill == null)
		{
			JSONArray array = new JSONArray();
			array.add(getJsonObj("skill_id", String.valueOf(-1)));
			array.add(getJsonObj("skill_info", ""));
			
			obj.put("value", array);
			
			return obj;
		}
		
		JSONArray array = new JSONArray();
		String skillInfo = skill.getDesc1();
		
		if(skill.getInitialValue1() > 0)
		{
			float value = skill.getInitialValue1() + (parnterLevel - 1) * skill.getGrowthValuePerLevel1();
			skillInfo = skillInfo.replace("{value1}", String.valueOf(value));
		}
		if(skill.getInitialValue2() > 0)
		{
			float value = skill.getInitialValue2() + (parnterLevel - 1) * skill.getGrowthValuePerLevel2();
			skillInfo = skillInfo.replace("{value2}", String.valueOf(value));
		}
		if(skill.getInitialValue3() > 0)
		{
			float value = skill.getInitialValue3() + (parnterLevel - 1) * skill.getGrowthValuePerLevel3();
			skillInfo = skillInfo.replace("{value3}", String.valueOf(value));
		}
		
		array.add(getJsonObj("skill_id", String.valueOf(id)));
		array.add(getJsonObj("skill_info", skillInfo));
		
		obj.put("value", array);
		
		return obj;
	}
	
}
