package net.class101.utility;

import net.class101.model.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsData {


	private Map<Integer, Item> map = null;


	//singleton
	public void initialize() {
		if(map == null) {
			synchronized(this) {
				map = new ConcurrentHashMap<Integer, Item>();
				Item item1 = new Item(16374, "class", "스마트스토어로 월 100만원 만들기, 평범한 사람이 돈을 만드는 비법", 151950, 99999);
				Item item2 = new Item(26825, "class", "해금, 특별하고 아름다운 나만의 반려악기", 114500, 99999);
				Item item3 = new Item(65625, "class", "일상에 따뜻한 숨결을 불어넣어요, 반지수와 함께하는 아이패드 드로잉", 174500, 99999);
				Item item4 = new Item(91008, "kit", "작고 쉽게 그려요 - 부담없이 시작하는 수채화 미니 키트", 28000, 10);
				Item item5 = new Item(9236, "kit", "하루의 시작과 끝, 욕실의 포근함을 선사하는 천연 비누", 9900, 22);
				Item item6 = new Item(55527, "class", "코바늘로 인형을 만들자! 시은맘의 꼼지락 작업실", 299500, 99999);
				Item item7 = new Item(2344, "class", "일상 유튜버 슛뚜의 감성을 그대로. 영화같은 브이로그 제작법", 184500, 99999);
				Item item8 = new Item(60538, "kit", "시작에 대한 부담을 덜다. 가격 절약 아이패드 특가전", 135800, 7);
				Item item9 = new Item(78156, "kit", "일상을 따뜻하게 채우는 썬캐쳐와 무드등", 45000, 16);
				Item item10 = new Item(53144, "class", "여행 드로잉, 꿈만 꾸지 마세요. 핀든아트와 여행, 그리다", 249500, 99999);
				Item item11 = new Item(78311, "class", "사각사각 손글씨의 낭만, 펜크래프트의 한글 정자체 펜글씨", 209500, 99999);
				Item item12 = new Item(97166, "kit", "이렇게 멋진 수채화 키트,어때요? 클래스101과 고넹이화방이 기획한 3가지 수채화 키트", 96900, 5);
				Item item13 = new Item(83791, "class", "월급으로 만족하지 못하는 분들을 위한 아마존/이베이 입문", 178500, 99999);
				Item item14 = new Item(58395, "kit", "선과 여백으로 채우는 2020년 캘린더와 엽서", 18620, 31);
				Item item15 = new Item(39712, "kit", "집에서 느끼는 겨울의 묵직한 포근함, 플랜테리어 아이템", 17600, 8);
				Item item16 = new Item(96558, "class", "사진 입문자를 위한 쉽게 배우고 빨리 써먹는 사진과 라이트룸", 234500, 99999);
				Item item17 = new Item(42031, "kit", "나만의 음악을 만들기 위한 장비 패키지", 209000, 2);
				Item item18 = new Item(45947, "class", "일러스트레이터 집시의 매력적인 얼굴 그리기", 249500, 99999);
				Item item19 = new Item(74218, "class", "나만의 문방구를 차려요! 그리지영의 타블렛으로 굿즈 만들기", 191600, 99999);
				Item item20 = new Item(28448, "class", "당신도 할 수 있다! 베테랑 실무자가 알려주는 모션그래픽의 모든 것", 152200, 99999);
				map.put(16374, item1);
				map.put(26825, item2);
				map.put(65625, item3);
				map.put(91008, item4);
				map.put(9236, item5);
				map.put(55527, item6);
				map.put(2344, item7);
				map.put(60538, item8);
				map.put(78156, item9);
				map.put(53144, item10);
				map.put(78311, item11);
				map.put(97166, item12);
				map.put(83791, item13);
				map.put(58395, item14);
				map.put(39712, item15);
				map.put(96558, item16);
				map.put(42031, item17);
				map.put(45947, item18);
				map.put(74218, item19);
				map.put(28448, item20);
			}
		}
	}

	public Map<Integer, Item> getData() {
		return map;
	}

}