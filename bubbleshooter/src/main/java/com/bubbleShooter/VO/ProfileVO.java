package com.bubbleShooter.VO;

public class ProfileVO
{
    public int id;              //프로필 아이디
    public int idCharacter;     //캐릭터 아이디
    public String iconSpineProfile; 
    public int idTextName;
    public int idTextIntro;
    public int sortNumber;
    public byte isHave;         // 미보유 : 0 , 보유 1
    public byte isNew;          // 신규 : 1, 기존 : 0
    public byte isUse;          // 사용중 : 1, 미사용 : 0
}
