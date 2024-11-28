package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.StorageContext;

@Component
public class IdListComponent {
	@Autowired
	private StorageContext storageContext;
	
	private List<IdListResource> resource = new ArrayList<>();
	String redisKey = "botId";
	private int botid = 10000;
	
	public void LoadResource() throws Exception
	{
		List<IdListResource> idRS = storageContext.<IdListResource>getDataList(ConstantVal.STATIC_DB, "id_list_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(idRS == null || idRS.isEmpty())
		{
			System.out.println("IdListResource No data");
			System.exit(0);
		}
		
		for(IdListResource id : idRS)
		{
			if(!checkNickname(id.getId()))
			{
				String errorMsg = "Duplicate id found:" + id.getId();
				System.out.println(errorMsg);
				System.exit(0);
			}
			
			resource.add(id);
		}
		
		//storageContext.putJsonData(ConstantVal.GAME_DB, redisKey, String.valueOf(botid + 1));
	}
	
	private boolean checkNickname(String id)
	{
		for(IdListResource nickname : resource)
		{
			if(nickname.getId().equals(id))
				return false;
		}
		
		return true;
	}
	
	public List<IdListResource> get()
	{
		return resource;
	}
	
	public int getBotId()
	{
		String lockKey = redisKey + "_lock";
		long expireMinutes = (60 * 4); // 4시간
		
		while(true)
		{
			// 분산 락을 시도한다.
			Boolean success = storageContext.getRedis(ConstantVal.GAME_DB).opsForValue().setIfAbsent(lockKey, "LOCK", expireMinutes, TimeUnit.MINUTES);
			if(success != null && success)
			{
				// 락 설정에 성공했으면, 값을 가져오고 증가시켜준다.
				try {
					String value = storageContext.getRedis(ConstantVal.GAME_DB).opsForValue().get(redisKey);
					int bot_id = value != null ? Integer.valueOf(value) : botid;
					storageContext.getRedis(ConstantVal.GAME_DB).opsForValue().set(redisKey, String.valueOf( bot_id < 10999 ? bot_id + 1 : botid));
					return bot_id;
				} finally {
					// 작업이 끝나면 락을 해제한다.
					storageContext.getRedis(ConstantVal.GAME_DB).delete(lockKey);
				}
			}
			// 값을 획득하지 못하면, 다시 재 시도
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
