package com.bubbleShooter.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class StorageContext {
	//////////////////////// Database //////////////////
	@Autowired
	@Qualifier("sqlSession0")
	private SqlSession sqlGame00;

	@Autowired
	@Qualifier("sqlSessionLog0")
	private SqlSession sqlLog00;

	@Autowired
	@Qualifier("sqlSessionResource")
	private SqlSession sqlResource;

	//////////////////////// Redis //////////////////
	@Autowired
	@Qualifier("rankRedisTemplate")
	private RedisTemplate<String, String> rankRedisSource;

	@Autowired
	@Qualifier("gameRedisTemplate")
	private RedisTemplate<String, String> redisSourceMap;

	private long expireMinutes = (60 * 4); // 4시간

	// sql session 값 얻어오는 함수
	public SqlSession getDB(byte findKey) {
		switch (findKey) {
		case ConstantVal.GAME_DB:
			return sqlGame00;
		case ConstantVal.STATIC_DB:
			return sqlResource;
		case ConstantVal.LOG_DB:
			return sqlLog00;
		default:
			return null;
		}
	}

	// redis template 값 얻어오는 함수
	public RedisTemplate<String, String> getRedis(byte findKey) {
		switch (findKey) {
		case ConstantVal.GAME_DB:
			return redisSourceMap;
		case ConstantVal.RANK_DB:
			return rankRedisSource;
		default:
			return null;
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// Game DB basic DB, Redis 처리 함수
	//////////////////////////////////////////////////////////////////////////

	// [SELECT] DB one row
	public <T> T checkGameDBConnection(int dbDivision) throws Exception {
		T dbObject = null;

		dbObject = getDB((byte)dbDivision).selectOne("initQry");
		return dbObject;
	}
	
	public <T> T getDataOne(byte Division, String procId) {
		T dbObject = null;
		try {
			dbObject = getDB(Division).selectOne(procId);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return dbObject;
	}

	public <T> T getDataOne(byte Division, String procId, long firstKey) {
		T dbObject = null;
		try {
			dbObject = getDB(Division).selectOne(procId, firstKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dbObject;
	}
	
	public <T> T getDataOne(byte Division, String procId, String uuid) {
		T dbObject = null;
		try {
			dbObject = getDB(Division).selectOne(procId, uuid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dbObject;
	}

	// [INSERT/UPDATE] Game DB one row
	public <T, O> T getDataOne(byte Division, String procId, O prameter) {
		T dbObject = null;
		try {
			dbObject = getDB(Division).selectOne(procId, prameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dbObject;
	}

	// [SELECT] Game DB 하나의 키값으로 multi row 얻어오는 함수
	public <T> List<T> getDataList(byte Division, String procId, long firstKey) {
		List<T> dbObject = null;
		try {
			dbObject = getDB(Division).selectList(procId, firstKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return dbObject;
	}

	// [SELECT] Game DB 여러개의 키값으로 multi row 얻어오는 함수
	public <T, O> List<T> getDataList(byte Division, String procId, O prameter) {
		List<T> dbObject = null;
		try {
			dbObject = getDB(Division).selectList(procId, prameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return dbObject;
	}

	// redis data 얻오오는 함수
	public String getJsonData(byte redisDivision, String redisKey) {
		String json = null;
		json = (String) getRedis(redisDivision).opsForValue().get(redisKey);
		return json;
	}

	public List<String> mgetJsonData(byte redisDivision, Collection<String> redisKeys) {
		List<String> jsons = null;
		jsons = getRedis(redisDivision).opsForValue().multiGet(redisKeys);
		return jsons;
	}

	// Game redis data 설정 함수
	public void putJsonData(byte redisDivision, String redisKey, String jsonData) {
		getRedis(redisDivision).opsForValue().set(redisKey, jsonData, expireMinutes, TimeUnit.MINUTES);
	}

	// Game redis data 설정 함수 (만료 시간 지정 가능)
	public void putJsonData(byte redisDivision, String redisKey, String jsonData, long expireTime) {
		getRedis(redisDivision).opsForValue().set(redisKey, jsonData, expireTime, TimeUnit.MINUTES);
	}

	// List 형태의 테이터를 redis 에 put 할때 사용
	public <T> void putJsonData(byte redisDivision, String redisKey, List<T> dbObject, Class<T[]> classListType) {
		Gson gson = new Gson();
		String jsonList = null;
		String json = gson.toJson(dbObject, dbObject.getClass());

		if (json.charAt(0) != '[')
			jsonList = "[" + json + "]";
		else
			jsonList = json;
		
		putJsonData(redisDivision, redisKey, jsonList);
	}

	public List<String> mgetOtherJsonData(byte redisDivision, Collection<String> redisKeys, long firstKey) {
		List<String> jsons = null;
		jsons = getRedis(redisDivision).opsForValue().multiGet(redisKeys);
		return jsons;
	}

	// Other redis key 삭제
	public void delOtherJsonData(byte redisDivision, String redisKey) {
		getRedis(redisDivision).delete(redisKey);
	}

	public void delSortedData(byte redisDivision, String zKey, String zVal) {
		getRedis(redisDivision).opsForZSet().remove(zKey, zVal);
	}

	// redis sortedset 키에 데이터 insert
	public boolean putSortedData(byte redisDivision, String zKey, String zVal, long zScore, byte keepTm) {
		if (keepTm == ConstantVal.DEFAULT_ZERO) {
			// expired Time : 3달
			getRedis(redisDivision).expire(zKey, (ConstantVal.MONTH_OF_MINUTE * 3), TimeUnit.MINUTES);
		} else {
			// expired Time : 2일
			getRedis(redisDivision).expire(zKey, (ConstantVal.MINUTE_OF_HOUR * ConstantVal.HOUR_OF_DAY * 2),
					TimeUnit.MINUTES);
		}

		return getRedis(redisDivision).opsForZSet().add(zKey, zVal, zScore);
	}
	
	// redis sortedset 키에 데이터 insert
	public boolean putSortedData(byte redisDivision, String zKey, String zVal, double zScore, byte keepTm) {
		if (keepTm == ConstantVal.DEFAULT_ZERO) {
			// expired Time : 3달
			getRedis(redisDivision).expire(zKey, (ConstantVal.MONTH_OF_MINUTE * 3), TimeUnit.MINUTES);
		} else {
			// expired Time : 2일
			getRedis(redisDivision).expire(zKey, (ConstantVal.MINUTE_OF_HOUR * ConstantVal.HOUR_OF_DAY * 2),
					TimeUnit.MINUTES);
		}

		return getRedis(redisDivision).opsForZSet().add(zKey, zVal, zScore);
	}

	public int getSortedScore(byte redisDivision, String zKey, String zVal) {
		Double score = getRedis(redisDivision).opsForZSet().score(zKey, zVal);
		return score.intValue();
	}

	// redis sorted set 랭킹 확인
	public long getSortedRank(byte redisDivision, String zKey, String zVal) {
		Long rank = getRedis(redisDivision).opsForZSet().reverseRank(zKey, zVal);

		if (rank == null)
			return -1;
		else
			return rank;
	}

	// redis sortedset 범위 랭크 확인 랭킹은 0부터 시작한다. ( 0 : 1등, 1 : 2등, ... )
	public Set<String> getSortedRankRange(byte redisDivision, String zKey, long start, long end) {
		Set<String> obj = getRedis(redisDivision).opsForZSet().reverseRange(zKey, start, end);
		return obj;
	}

	public Set<String> getSortedReverseRankRange(byte redisDivision, String zKey, long start, long end) {
		Set<String> obj = getRedis(redisDivision).opsForZSet().range(zKey, start, end);
		return obj;
	}

	public long getSortedTotCount(byte redisDivision, String zKey) {
		return getRedis(redisDivision).opsForZSet().size(zKey);
	}

	// ////////////////////////////////////////////////////////////////////////

	/*
	 * 스토리지 (DB, Redis) 로부터 데이터를 가져오는 함수 (one row)
	 * 
	 * procId : MySQL Stored Procedure ID redisKey : Redis key firstKey : Sharding
	 * key And Mysql Select first key classType : T 클래스 타입 (domainpackage 에 등록되어 있는
	 * 클래스) dbForceSearch : redis 에 데이터가 있어도 DB 에서 다시 조회할 때 사용
	 */
	public <T> T getFromStorage(byte dbDivision, String procId, String redisKey, long firstKey, Class<T> classType, boolean dbForceSearch) {
		if (firstKey < 1)
			return null;

		T dbObject = null;
		String json = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);

		if (json != null && !dbForceSearch)
			return gson.fromJson(json, classType);

		dbObject = getDataOne(dbDivision, procId, firstKey);
		if (dbObject != null) {
			json = gson.toJson(dbObject, dbObject.getClass());
			putJsonData(dbDivision, redisKey, json);
		}

		return dbObject;
	}
	
	public <T> T getFromStorage(byte dbDivision, String procId, String redisKey, String firstKey, Class<T> classType, boolean dbForceSearch) {
		T dbObject = null;
		String json = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);

		if (json != null && !dbForceSearch)
			return gson.fromJson(json, classType);

		dbObject = getDataOne(dbDivision, procId, firstKey);
		if (dbObject != null) {
			json = gson.toJson(dbObject, dbObject.getClass());
			putJsonData(dbDivision, redisKey, json);
		}

		return dbObject;
	}

	public <T, O> T getFromStorage(byte dbDivision, String procId, String redisKey, O parameter, Class<T> classType, boolean dbForceSearch) {
		T dbObject = null;
		String json = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);

		if (json != null && !dbForceSearch)
			return gson.fromJson(json, classType);

		dbObject = getDataOne(dbDivision, procId, parameter);
		if (dbObject != null) {
			json = gson.toJson(dbObject, dbObject.getClass());
			putJsonData(dbDivision, redisKey, json);
		}

		return dbObject;
	}

	// 스토리지 (DB, Redis) 로부터 데이터를 가져오는 함수 (multi row)
	public <T> List<T> getFromListStorage(byte dbDivision, String procId, String redisKey, long firstKey, Class<T[]> classListType,
			boolean dbForceSearch) {
		if (firstKey < 1)
			return null;

		List<T> dbObject = null;
		String json = null;
		String jsonList = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);

		if (json != null && !dbForceSearch) {
			T[] tempObj = gson.fromJson(json, classListType);
			return new ArrayList<T>(Arrays.asList(tempObj));
		}

		dbObject = getDataList(dbDivision, procId, firstKey);
		if (dbObject.isEmpty())
			return dbObject;

		json = gson.toJson(dbObject, dbObject.getClass());
		// data 형태가 List 가 아닐경우 List 형태로 만든다.
		if (json.charAt(0) != '[')
			jsonList = "[" + json + "]";
		else
			jsonList = json;

		putJsonData(dbDivision, redisKey, jsonList);

		return dbObject;
	}

	// 스토리지 (DB, Redis) 로부터 데이터를 가져오는 함수 (multi row)
	public <T, O> List<T> getFromListStorage(byte dbDivision, String procId, String redisKey, O parameter, Class<T[]> classListType, boolean dbForceSearch) {
		List<T> dbObject = null;
		String json = null;
		String jsonList = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);

		if (json != null && !dbForceSearch) {
			T[] tempObj = gson.fromJson(json, classListType);
			return new ArrayList<T>(Arrays.asList(tempObj));
		}

		dbObject = getDataList(dbDivision, procId, parameter);
		if (dbObject.isEmpty())
			return dbObject;

		json = gson.toJson(dbObject, dbObject.getClass());
		// data 형태가 List 가 아닐경우 List 형태로 만든다.
		if (json.charAt(0) != '[')
			jsonList = "[" + json + "]";
		else
			jsonList = json;

		putJsonData(dbDivision, redisKey, jsonList);

		return dbObject;
	}

	public <T> boolean setFromListRedis(byte dbDivision, String redisKey, List<T> dataObjects, Class<T[]> classListType) {
		if (dataObjects == null || dataObjects.isEmpty())
			return false;

		putJsonData(dbDivision, redisKey, dataObjects, classListType);

		return true;
	}

	// redis 만을 이용하는 데이터 처리시 사용하는 함수
	public <T> List<T> getFromListRedis(byte dbDivision, String redisKey, Class<T[]> classListType) {
		String json = null;
		Gson gson = new Gson();

		json = getJsonData(dbDivision, redisKey);
		if (json == null)
			return null;

		T[] tempObj = gson.fromJson(json, classListType);
		return new ArrayList<T>(Arrays.asList(tempObj));
	}

	/*
	 * 스토리지 (DB, Redis) 로 데이터를 저장하는 함수 userId 하나가 primary key 로 사용되는 컬럼만 사용해야 함
	 * procId : MySQL Stored Procedure ID redisKey : Redis key firstKey : Sharding
	 * key And Mysql Select first key dataObject : 저장되는 데이터 객체
	 */
	public <D> D setToStorage(byte dbDivision, String procId, String redisKey, D dataObject) {
		D resultObject = null;
		Gson gson = new Gson();

		resultObject = getDataOne(dbDivision, procId, dataObject);
		if (resultObject != null) {
			String json = gson.toJson(dataObject, dataObject.getClass());
			putJsonData(dbDivision, redisKey, json);
		}

		return resultObject;
	}

	public <T> boolean setToListStorage(byte dbDivision, String procId, String redisKey, int updateNum, List<T> dataObjects, Class<T[]> classListType) {
		if (updateNum < 0)
			return false;

		if (dataObjects.isEmpty() || dataObjects == null)
			return false;

		if (dataObjects.size() < updateNum)
			return false;

		getDataOne(dbDivision, procId, dataObjects.get(updateNum));
		putJsonData(dbDivision, redisKey, dataObjects, classListType);

		return true;
	}

	// Class 를 json 데이터로 만들어 주는 함수
	public <T> String makeOneJson(T Object, Class<T> classType) {
		Gson gson = new Gson();
		return gson.toJson(Object, classType);
	}
	
	public int CheckDBConnection()
	{
		try {
			getDataOne(ConstantVal.GAME_DB, "initQry");
		} catch (Exception e) {
			e.printStackTrace();
			return ConstantVal.GAME_DB;
		}
		
		try {
			getDataOne(ConstantVal.LOG_DB, "initQry");
		} catch (Exception e) {
			e.printStackTrace();
			return ConstantVal.LOG_DB;
		}
		
		// no error
		return ConstantVal.DEFAULT_VALUE;
	}
}
