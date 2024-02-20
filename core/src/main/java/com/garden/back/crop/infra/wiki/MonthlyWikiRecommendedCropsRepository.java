package com.garden.back.crop.infra.wiki;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MonthlyWikiRecommendedCropsRepository {

    private final List<WikiRecommendedCrop> wikiRecommendedCrops = new ArrayList<>();

    @PostConstruct
    public void setUp() {
        wikiRecommendedCrops.add(new WikiRecommendedCrop("파", "파에는 칼슘·염분·비타민 등이 많고 특이한 향취가 있어서 생식·약용 및 요리에 널리 쓰인다. 마늘과 함께 한국 요리의 주요 양념 재료이다.", List.of(1, 3, 4), "https://ko.wikipedia.org/wiki/%ED%8C%8C_(%EC%A2%85)"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("고추", "고추는 비타민 A와 비타민 C가 다량 함유되어 있어. 더위 예방 효과가 높고, 또한 살균 작용이 있어 식중독을 예방해 주기도 하므로, 특히 더운 지역에서 많이 사용된다.", List.of(1, 5), "https://ko.wikipedia.org/wiki/%EA%B3%A0%EC%B6%94"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("들깨", "들깨를 자연 그대로 자라게 두면 열매를 적게 맺어 수확량이 적다. 여름에 줄기를 밑에서 5센티미터 정도만 남기고 자르면 새 줄기가 자라고 가을에 열매를 더 많이 맺는다. 이때 잘라낸 줄기에서 잎을 수확할 수 있다. 잎은 여름부터 가을에 걸쳐 수확하며, 들깨는 가을에 과실이 성숙하면 풀포기 채로 채취해 씨앗을 떨어낸 다음 햇볕에 말린다.", List.of(1, 6, 7), "https://ko.wikipedia.org/wiki/%EB%93%A4%EA%B9%A8"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("고구마", "고구마는 따뜻한 기후를 좋아하는 고온 작물로 한국의 봄 날씨와는 어울리지 않는다. 자라는 데 알맞은 온도는 30-35 °C이다. 땅은 너무 습한 곳이 아니면 그다지 가리지 않으며 산성 땅에서도 비교적 잘 된다. 고구마는 싹을 길러서 심는데, 싹기르기는 온상에서 한다. 3월 중·하순에 온상을 만들고 온상의 온도가 30-35 °C가 되면 씨고구마를 묻는다. 5월 상순-중순경 싹이 30cm로 자라면 20-30cm 간격으로 꽂는다. 이때 순과 잎이 땅 위에 나오도록 심는다.", List.of(1, 2, 5), "https://ko.wikipedia.org/wiki/%EA%B3%A0%EA%B5%AC%EB%A7%88"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("파프리카", "파프리카(헝가리어: paprika)는 피망보다도 매운 맛이 없고 단맛이 강하다. 일반적으로 매운맛이 나고 육질이 질긴 것을 피망, 단맛이 많고 아삭아삭하게 씹히는 것을 파프리카라고 부른다.", List.of(1, 5), "https://ko.wikipedia.org/wiki/%EB%8B%A8%EA%B3%A0%EC%B6%94"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("마늘", "마늘은 한파에 약하고 가뭄에도 취약하여 전세계적으로 온대와 아열대 지역에서 재배한다. 더위에도 약하여 한지형의 경우 6월 하순경에는 지상부가 마르고, 약 3개월간 휴면기에 들어간다. 파종 후 저온기를 경과하여야 비늘줄기의 비대가 촉진되므로 한지형은 대개 10월 하순·11월 상순에 파종하는 데, 이보다 빨리 파종하여 연내에 지상부가 자라날 경우 추위에 약하게 된다. 봄에 파종할 경우에는 해동되자마자 파종해야만 비늘줄기가 알차게 성숙된다. 파종시에 흙덮기(복토)를 얇게 하면 솟아 나와서 동해를 입으므로 약 2-3cm가량 흙을 덮고 그 위에 두엄을 덮어서 보온하고, 이듬해 3월 중·하순에 벗긴다. 난지형은 9월쯤 파종하여 다음해 5-6월에 수확한다.", List.of(2), "https://ko.wikipedia.org/wiki/%EB%A7%88%EB%8A%98"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("쑥갓", "재배는 보통 봄·가을에 실시하지만 연중 재배할 수도 있다. 파종은 줄뿌림하거나 산파(散播)한다. 거름은 10a당 두엄 1,000kg, 질소 16kg, 인산 7kg, 칼리 8kg 정도이고, 수확은 본잎이 5-10장, 길이 10-15cm 정도 될 때 한다. 품종에는 대엽종·중엽종·소엽종이 있는데, 대한민국에서는 대부분 중엽종이 재배되고 있다. 중엽종은 분지가 많고 생육이 왕성하며 추수가 비교적 빠른 장점이 있다.", List.of(4, 11), "https://ko.wikipedia.org/wiki/%EC%91%A5%EA%B0%93"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("감자", "감자는 수확할 때 늦서리 염려가 없도록 가능한 일찍 파종하는 것이 좋다. 예전에는 음력 2월에 파종하였으나, 지금은 모내기 전에 수확하는 논감자는 양력 2월 하순, 남쪽지방의 봄감자는 3월 중순, 경기, 충청 등 중부내륙 지역은 3월 하순, 강원도 등 고랭지 여름감자는 4월 하순, 남쪽지방의 가을감자는 7월 하순이 파종적기이다. 감자는 비교적 서늘한 기후를 좋아하는데, 자라는 데 가장 알맞은 온도는 20°C쯤이다. 이정도 온도가 90~150일 정도 유지되어야 최상의 감자를 얻을 수 있다.", List.of(5, 6), "https://ko.wikipedia.org/wiki/%EA%B0%90%EC%9E%90"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("시금치", "시금치의 재배 형태는 봄가꾸기·여름가꾸기·가을가꾸기의 세 가지가 있다. 봄가꾸기는 4~5월에 씨를 뿌려 5~6월에 수확하는 것으로 대표적인 품종으로는 노벨이 있다. 여름가꾸기는 6~8월에 씨를 뿌려 8~10월에 수확하는 형태로 재래종이 재배되나 온도가 25°C 이상 되면 자라지 않으므로 고랭지에서만 재배된다. 가을가꾸기는 9~10월에 씨를 뿌려 10~11월에 수확하는 것으로 주로 우성시금치가 재배된다. 파종은 줄뿌림을 주로 하며 시비량은 10a당 질소 20kg, 칼륨 15kg, 인산 12kg 정도이다. 수확은 재배 시기에 따라 다르나 파종 뒤 50~60일에 실시한다.", List.of(6), "https://ko.wikipedia.org/wiki/%EC%8B%9C%EA%B8%88%EC%B9%98"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("양파", "대한민국에서는 8-9월에 모판에 파종하여 10월에 어린 모종을 밭에 정식하고, 다음 해 6월 무렵에 수확하는 가을뿌림재배가 대부분을 차지하고 있다. 봄에 파종하여 가을에 수확하는 봄뿌림재배를 하면 다음 해 1월 상순까지는 싹이 나지 않고, 그 뒤에 냉장하면 4월까지 저장할 수 있다. 봄뿌리재배는 강원도 대관령·인제등지의 고랭지에서 하고 있다. 이 밖에 3-4월에 파종하여 5월 중순경에 작은 알(球)을 수확하고 건조시켰다가 8월 무렵 밭에 심어 겨울부터 이른 봄에 수확하는 세트 재배방식도 있다.", List.of(6, 12), "https://ko.wikipedia.org/wiki/%EC%96%91%ED%8C%8C"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("배추", "배추는 서늘한 기후를 좋아하는 저온성 채소로서 생육 기간은 50~90일이다. 생육 초기에는 높은 온도에 잘 견디나 생육에 가장 알맞은 온도는 20°C 전후이고 결구의 최적온도는 15~16°C이다. 과거에는 가을에만 재배되었으나 근래에는 봄철 하우스 재배 및 여름철 고랭지 재배도 시행되고 있다. 온도가 천천히 내려갈 때에는 영하 8°C까지 견디나 갑자기 추워지면 영하 3~4°C에서 동해를 입는다. 종자는 젖은 상태에서 10°C 이하로 일주일이 경과되면 꽃눈 분화를 하는 종자춘화형(種子春化形)이다. 또 어린 식물도 어느 정도 자라기 전에 13°C 이하의 저온을 받으면 꽃눈이 분화하여 결구가 되지 않는다. 특히 이런 현상은 하우스나 비닐 터널을 이용한 봄재배에서 자주 볼 수 있다. 배추는 자라는 데 충분한 수분이 필요하며 물빠짐이 좋은 사질양토(모래진흙)가 좋다.", List.of(8, 9, 10), "https://ko.wikipedia.org/wiki/%EB%B0%B0%EC%B6%94"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("상추", "생육 및 싹트기에 알맞은 온도는 15 ~ 20°C이며 고온에 의해 추대가 이루어진다. 산성 토양에 약하며 충분한 수분을 필요로 한다. 봄재배·가을재배·겨울재배·고랭지재배가 있다. 고랭지에서는 주로 결구상추가 재배되며 평지의 봄·가을 재배에서는 잎상추가 재배된다. 5, 6주간 묘상에서 육묘하여 이식하는데 파종은 6cm 간격으로 줄뿌림하며 본잎이 2 ~ 4장 전개되면 한번 옮겨심었다가 본잎이 5 ~ 7장 되었을 때 포장이나 온실 내에 정식한다. 거름은 10a당 질소 20kg, 인산 10kg, 칼리 15kg 정도로 준다. 수확은 잎상추는 정식 후 30일, 결구상추는 50일, 치마상추는 수시로 실시한다.", List.of(11), "https://ko.wikipedia.org/wiki/%EC%83%81%EC%B6%94"));
        wikiRecommendedCrops.add(new WikiRecommendedCrop("토마토", "영어 Tomato 와 스페인어 tomate는 아즈텍어 tomatl에서 유래됐다. ,토마토를 뜻하는 이탈리아어 뽀모도로(pomodoro)는 '황금 사과'라는 뜻인데 그리스 신화 중 헤스페리데스 동산에서 자란다는 바로 그 황금 사과를 말했던 것이라고 추측된다. 이는 토마토의 원산지 남아메리카를 에덴동산이 있던 곳이라고 믿었던 것과도 관련이 있다. 나무에서 자라지 않는 일년생 채소라는 의미에서 일년감이라고도 불리며, 한자이름인 남만시로도 알려져 있다.", List.of(11), "https://ko.wikipedia.org/wiki/%ED%86%A0%EB%A7%88%ED%86%A0"));
    }

    public List<WikiRecommendedCrop> findByMonth(Integer month) {
        return wikiRecommendedCrops.stream()
            .filter(crop -> crop.recommendedMonth().contains(month))
            .toList();
    }
}
