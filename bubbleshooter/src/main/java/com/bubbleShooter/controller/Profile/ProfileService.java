package com.bubbleShooter.controller.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Profile;
import com.bubbleShooter.domain.UserProfileLog;
import com.bubbleShooter.resource.ProfileResource;
import com.bubbleShooter.response.ResProfileConfirm;
import com.bubbleShooter.response.ResProfileInfo;
import com.bubbleShooter.response.ResProfileUse;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class ProfileService
{
    @Autowired
    GameResource gameResource;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    MapperVO mapperVO;
    
    public ResProfileInfo getProfile(int userId, List<Profile> profiles, boolean dbForceSearch) throws Exception
    {
        // 유저의 프로필 정보를 얻어온다.
        if(profiles == null)
        {
            profiles = repositoryService.getProfiles(userId, dbForceSearch);
        }
        
        if(profiles.isEmpty())
        {
            // 프로필 정보가 없다면, 프로피일 정보를 초기화한다.
            List<ProfileResource> profileResources = gameResource.getProfile().getProfileList();
            if(profileResources == null || profileResources.isEmpty())
                throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

            profiles = initUserProfile(userId, profileResources);
        }

        // 신규로 추가된 프로필이 있는지 체크한다.
        checkNewProfile(userId, profiles);

        boolean is_eventProfile = false;
        for(Profile profile : profiles)
        {
            ProfileResource pfData = gameResource.getProfile().get(profile.getProfileId());
            if(pfData == null)
                throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);
            
            if(pfData.getStartDate() > 0)
            {
                long startDate = TimeCalculation.StringDateToUnixTime(String.valueOf(pfData.getStartDate())) / 1000;
                long endDate = Math.max(TimeCalculation.StringDateToUnixTime(String.valueOf(pfData.getEndDate())) / 1000, 0);
                if(!TimeCalculation.checkTimeNow(startDate, endDate))
                {
                    if(profile.getIsUse() >= ConstantVal.IS_TRUE)
                        is_eventProfile = true;
                }
            }
        }

        // 사용중인 프로필이 없다면, 기본 프로필을 사용중으로 설정한다.
        if(is_eventProfile)
            releaseAllProfile(userId, profiles, true);

        ResProfileInfo res = new ResProfileInfo();
        res.result = ConstantVal.DEFAULT_SUCCESS;
        res.profiles = mapperVO.makeProfileVO(profiles);

        return res;
    }

    public boolean AddProfile(int userId, int characterId) throws Exception
    {
        List<Profile> profiles = repositoryService.getProfiles(userId, false);
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);

        for(Profile profile : profiles)
        {
            if(profile.getCharacterId() == characterId)
            {
                ProfileResource pfData = gameResource.getProfile().getCharacterId(characterId);
                if(pfData == null)
                    throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

                if(profile.getIsHave() == ConstantVal.IS_TRUE)
                    return false;

                profile.setIsHave(ConstantVal.IS_TRUE);
                profile.setIsNew(ConstantVal.IS_TRUE);
                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);
                
                //로그 추가
                repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                return true;
            }
        }

        return false;
    }

    // 현재 사용하지 않음. 추후 상점 구현시 사용예정
    public boolean buyShopProfile(int userId, int profileId) throws Exception
    {
        List<Profile> profiles = repositoryService.getProfiles(userId, false);
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);

        ProfileResource pfData = gameResource.getProfile().get(profileId);
        if(pfData == null)
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

        for(Profile profile : profiles)
        {
            if(profile.getProfileId() == profileId)
            {
                if(profile.getIsHave() == ConstantVal.IS_TRUE)
                    return false;

                profile.setIsHave(ConstantVal.IS_TRUE);
                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);

                //로그 추가
                repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                return true;
            }
        }

        return false;
    }

    public boolean delProfile(int userId, int profileId, List<Profile> profiles) throws Exception
    {
        if(profiles == null)
        {
            profiles = repositoryService.getProfiles(userId, false);
            if(profiles == null || profiles.isEmpty())
                throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);
        }

        for(Profile profile : profiles)
        {
            if(profile.getProfileId() == profileId)
            {
                if(profile.getIsHave() == ConstantVal.IS_FALSE)
                    return false;

                profile.setIsHave(ConstantVal.IS_FALSE);
                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);

                //로그 추가
                repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                return true;
            }
        }

        return false;
    }

    public boolean delProfileToCardId(int userId, int cardId, List<Profile> profiles) throws Exception
    {
        if(profiles == null)
        {
            profiles = repositoryService.getProfiles(userId, false);
            if(profiles == null || profiles.isEmpty())
                throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);
        }

        for(Profile profile : profiles)
        {
            if(profile.getCharacterId() == cardId)
            {
                if(profile.getIsHave() == ConstantVal.IS_FALSE)
                    return false;

                profile.setIsHave(ConstantVal.IS_FALSE);
                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);

                //로그 추가
                repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                return true;
            }
        }

        return false;
    }

    public ResProfileConfirm ConfirmProfile(int userId, int profileId) throws Exception
    {
        List<Profile> profiles = repositoryService.getProfiles(userId, false);
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);

        for(Profile profile : profiles)
        {
            if(profile.getProfileId() == profileId)
            {
                profile.setIsNew(ConstantVal.IS_FALSE);
                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);
                
                //로그 추가
                //repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                break;
            }
        }
        
        ResProfileConfirm res = new ResProfileConfirm();
        res.result = ConstantVal.DEFAULT_SUCCESS;
        res.profiles = getProfile(userId, profiles, false).profiles;
        return res;
    }

    public ResProfileUse UseProfile(int userId, int profileId) throws Exception
    {
        ProfileResource pfRS = gameResource.getProfile().get(profileId);
        if(pfRS == null)
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

        List<Profile> profiles = repositoryService.getProfiles(userId, false);
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6201);
        
        // 장착할 프로필이 유저의 소유인지 체크한다.
        int index = findProfileIndex(profileId, profiles);
        if(index <= ConstantVal.DEFAULT_VALUE)
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6202);

        // 프로필이 보유중인지 체크한다.
        if(profiles.get(index).getIsHave() == ConstantVal.IS_FALSE)
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6202);
        
        // 모든 프로필을 장착 해제 하고, 기본캐릭터의 INDEX값을 얻어온다.
        releaseAllProfile(userId, profiles, false);
        
        // 프로필을 장착한다.
        profiles.get(index).setIsUse(ConstantVal.IS_TRUE);
        repositoryService.setProfile(userId, index, profiles);

        //로그 추가
        //repositoryService.setProfileLog(userId, new UserProfileLog().Set(profiles.get(index)));

        ResProfileUse res = new ResProfileUse();
        res.result = ConstantVal.DEFAULT_SUCCESS;
        res.profiles = getProfile(userId, profiles, false).profiles;
        return res;
    }

    public List<Profile> initUserProfile(int userId, List<ProfileResource> profileResources) throws Exception
    {
        if(profileResources == null)
        {
            profileResources = gameResource.getProfile().getProfileList();
        }
        
        int defaultCharacterId = ConstantVal.DEFAULT_CREATE_CHARACTER[0];
        
        List<Profile> profiles = new ArrayList<>();
        for(ProfileResource pf : profileResources)
        {
            Profile profile = new Profile();
            profile.setUserId(userId);
            profile.setProfileId(pf.getId());
            profile.setItemType(pf.getItemType());
            profile.setCharacterId(pf.getIdCharacter());
            profile.setIsNew(ConstantVal.IS_FALSE);
            profile.setIsHave(pf.getIdCharacter() == defaultCharacterId ? ConstantVal.IS_TRUE : ConstantVal.IS_FALSE);
            profile.setIsUse(pf.getIdCharacter() == defaultCharacterId ? ConstantVal.IS_TRUE : ConstantVal.IS_FALSE);

            profiles.add(profile);
        }

        List<Character> characters = repositoryService.getCharacters(userId, false);
        for(Character c : characters)
        {
            for(Profile profile : profiles)
            {
                if(profile.getCharacterId() == c.getCharacterId())
                {
                    profile.setIsNew(ConstantVal.IS_TRUE);
                    profile.setIsHave(ConstantVal.IS_TRUE);

                    //로그 추가
                    repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                }
            }
        }

        List<Partner> partners = repositoryService.getPartners(userId, false);
        for(Partner p : partners)
        {
            for(Profile profile : profiles)
            {
                if(profile.getCharacterId() == p.getPartnerId())
                {
                    profile.setIsNew(ConstantVal.IS_TRUE);
                    profile.setIsHave(ConstantVal.IS_TRUE);
                    
                    //로그 추가
                    repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
                }
            }
        }

        HashMap<String, List<Profile>> param = new HashMap<>();
        param.put("profileList", profiles);
        repositoryService.initProfile(userId, param);

        return profiles;
    }

    private void checkNewProfile(int userId, List<Profile> profiles) throws Exception
    {
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

        List<ProfileResource> profileResources = gameResource.getProfile().getProfileList();
        if(profileResources == null || profileResources.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);
        
        for(ProfileResource pf : profileResources)
        {
            boolean isHave = false;
            for(Profile profile : profiles)
            {
                if(profile.getProfileId() == pf.getId())
                {
                    isHave = true;
                    break;
                }
            }

            if(!isHave)
            {
                Profile profile = new Profile();
                profile.setUserId(userId);
                profile.setProfileId(pf.getId());
                profile.setItemType(pf.getItemType());
                profile.setCharacterId(pf.getIdCharacter());
                profile.setIsNew(ConstantVal.IS_FALSE);
                profile.setIsHave(ConstantVal.IS_FALSE);
                profile.setIsUse(ConstantVal.IS_FALSE);

                profiles.add(profile);

                repositoryService.setProfile(userId, profiles.indexOf(profile), profiles);

                //로그 추가
                //repositoryService.setProfileLog(userId, new UserProfileLog().Set(profile));
            }
        }
    }

    // 모든 프로필을 장착 해제 하ㄱ고, 기본 프로필을 장착시킨다 (주의 : 이 함수를 타면 DB까지 모두 SET)
    private void releaseAllProfile(int userId, List<Profile> profiles, boolean defaultProfile) throws Exception
    {
        if(profiles == null || profiles.isEmpty())
            throw new BubbleException(ErrorCodeInfo.ERROR_CODE_PROFILE_6200);

        boolean dbUpdate = false;
        int defaultProfileIndex = ConstantVal.DEFAULT_VALUE;
        for(Profile profile : profiles)
        {
            if(profile.getCharacterId() == ConstantVal.DEFAULT_CREATE_CHARACTER[0])
                defaultProfileIndex = profiles.indexOf(profile);

            if(profile.getIsUse() >= ConstantVal.IS_TRUE)
            {
                profile.setIsUse(ConstantVal.IS_FALSE);
                repositoryService.setProfile(userId, profile);
                dbUpdate = true;
            }
        }

        if(dbUpdate)
        {
            Gson gson = new Gson();
            repositoryService.onlySetRedis(ConstantVal.GAME_DB, "profile:" + userId, gson.toJson(profiles), (60 * 4));
        }
            

        if(defaultProfile)
        {
            // 기본 프로필을 장착 시켜준다.
            profiles.get(defaultProfileIndex).setIsUse(ConstantVal.IS_TRUE);
            repositoryService.setProfile(userId, defaultProfileIndex, profiles);
            
            //로그 추가
            repositoryService.setProfileLog(userId, new UserProfileLog().Set(profiles.get(defaultProfileIndex)));
        }
    }

    private int findProfileIndex(int profileId, List<Profile> profiles)
    {
        if(profiles == null || profiles.isEmpty())
            return ConstantVal.DEFAULT_VALUE;

        for(Profile profile : profiles)
        {
            if(profile.getProfileId() == profileId)
                return profiles.indexOf(profile);
        }

        return ConstantVal.DEFAULT_VALUE;
    }
}
